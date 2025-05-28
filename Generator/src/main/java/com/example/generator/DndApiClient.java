package com.example.generator;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

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
    public List<String>getSpellsByClassAndLevel(String characterClass, int level)
    {
        return webClient.get()
                        .uri("classes/{characterClass}/levels/{level}/spells",characterClass.toLowerCase(),level)
                        .retrieve()
                        .bodyToMono(JsonNode.class)
                        .map(json->{
                            JsonNode results = json.get("results");
                            if(results == null||!results.isArray()) return List.<String>of();
                            return StreamSupport.stream(results.spliterator(),false)
                                    .map(node->node.get("name").asText())
                                    .collect(Collectors.toList());
                        })
                        .block();
    }
    /*public List<String>getStartingEquipment(String characterClass)
    {
        return webClient.get()
                .uri("classes/{characterClass}", characterClass)
                .retrieve()
                .bodyToMono(JsonNode.class)
                .map()
    }*/

}
