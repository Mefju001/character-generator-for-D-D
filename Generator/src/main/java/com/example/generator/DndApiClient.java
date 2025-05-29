package com.example.generator;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
public class DndApiClient {
    private final WebClient webClient = WebClient.create("https://www.dnd5eapi.co/api/2014/");
    public List<String> getRaceTraits(String race){
        return webClient.get()
                        .uri("races/{race}",race.toLowerCase())
                .retrieve()
                .bodyToMono(JsonNode.class)
                .map(json-> StreamSupport.stream(json.get("traits").spliterator(),false)
                        .map(node->node.get("name").asText())
                        .collect(Collectors.toList()))
                .block();
    }
    public List<String>getClassFeatures(String characterClass,int level)
    {
        return webClient.get()
                .uri("classes/{characterClass}/levels/{level}",characterClass.toLowerCase(), level)
                .retrieve()
                .bodyToMono(JsonNode.class)
                .map(json->StreamSupport.stream(json.get("features").spliterator(),false)
                        .map(node->node.get("name").asText())
                        .collect(Collectors.toList()))
                .block();
    }
    public List<Spell>getSpellsByClassAndLevel(String characterClass, int level)
    {
         JsonNode json = webClient.get()
                        .uri("classes/{characterClass}/levels/{level}/spells",characterClass.toLowerCase(),level)
                        .retrieve()
                        .bodyToMono(JsonNode.class)
                        .block();
        if(json == null) return List.of();
        JsonNode results = json.get("results");
        if(results == null||!results.isArray()) return List.of();
        List<Spell>spells = new ArrayList<>();
        for(JsonNode node : results){
            String spellIndex=node.get("index").asText();
            JsonNode spellDetails = webClient.get()
                    .uri("spells/{index}",spellIndex)
                    .retrieve()
                    .bodyToMono(JsonNode.class)
                    .block();
            if(spellDetails!=null) {
                String name = spellDetails.get("name").asText();
                int spellLevel = spellDetails.get("level").asInt();
                String description = "";
                JsonNode descNode = spellDetails.get("desc");
                if (descNode != null && descNode.isArray() && !descNode.isEmpty()) {
                    description = descNode.get(0).asText();
                }
                spells.add(new Spell(name, spellLevel, description));
            }
        }
        return spells;
    }
    public EquipmentData getStartingEquipment(String characterClass)
    {
        JsonNode json = webClient.get()
                .uri("classes/{class}/starting-equipment",characterClass.toLowerCase())
                .retrieve()
                .bodyToMono(JsonNode.class)
                .block();
        if(json == null) return null;
        List<EquipmentOption> proposed = new ArrayList<>();
        JsonNode options = json.get("starting_equipment_options");
        if(options != null&&options.isArray()) {
            for(JsonNode opt:options){
                String desc = opt.get("desc").asText();
                List<String> choices = new ArrayList<>();
                JsonNode innerOptions = opt.get("from").get("options");
                for(JsonNode choice:innerOptions){
                    JsonNode item = choice.get("of");
                    if(item!=null&&item.has("name")){
                        choices.add(item.get("name").asText());
                    } else {
                        JsonNode choiceNode = choice.get("choice");
                        if(choiceNode!=null) {
                            JsonNode from = choiceNode.get("from");
                            if (from != null) {
                                if (from.has("name")) {
                                    choices.add(from.get("name").asText());
                                } else if (from.has("equipment_category") && from.get("equipment_category").has("name")) {
                                    choices.add(from.get("equipment_category").get("name").asText());
                                }
                            }
                        }
                    }

                }
                proposed.add(new EquipmentOption(desc,choices));
            }
        }
        List<EquipmentItem> selected = new ArrayList<>(List.of());
        JsonNode items = json.path("starting_equipment");
        if (items.isArray()) {
            for (JsonNode entry : items) {
                String name = entry.path("equipment").path("name").asText("Nieznany przedmiot");
                int qty = entry.path("quantity").asInt(1);
                selected.add(new EquipmentItem(name, qty));
            }
        }
        return new EquipmentData(proposed,selected);
    }
}
