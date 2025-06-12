package com.example.generator.Service;

import com.example.generator.Data.CharacterJson;
import com.example.generator.Api.DndApiClient;
import com.example.generator.Data.CharactersData;
import com.example.generator.Data.EquipmentData;
import com.example.generator.Data.Spell;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CharacterService {

    private final DndApiClient apiClient;
    private final Random random = new Random();
    @Autowired
    public CharacterService(DndApiClient dndApiClient)
    {
        this.apiClient=dndApiClient;
    }
    public CharacterJson generate(String race, String characterClass, int level)
    {
        CharacterJson characterJson = new CharacterJson();
        characterJson.setgenerate(race,characterClass,level);
        List<Integer>rolls = new ArrayList<>();
        for(int i=0;i<6;i++)
            rolls.add(roll());
        sortmal(rolls);
        Map<String,List<String>>primaryAbilities = Map.ofEntries(
                Map.entry("barbarian", List.of("STR", "CON")),
                Map.entry("bard", List.of("CHA", "DEX")),
                Map.entry("cleric", List.of("WIS", "STR")),
                Map.entry("druid", List.of("WIS", "CON")),
                Map.entry("fighter", List.of("STR", "DEX", "CON")),
                Map.entry("monk", List.of("DEX", "WIS")),
                Map.entry("paladin", List.of("STR", "CHA")),
                Map.entry("ranger", List.of("DEX", "WIS")),
                Map.entry("rogue", List.of("DEX", "INT", "CHA")),
                Map.entry("sorcerer", List.of("CHA", "CON")),
                Map.entry("warlock", List.of("CHA", "CON")),
                Map.entry("wizard", List.of("INT", "DEX"))
        );
        List<String>abilities = new ArrayList<>(List.of("STR", "DEX", "CON", "INT", "WIS", "CHA"));
        List<String> orderedAbilities = new ArrayList<>();
        List<String>primaries = primaryAbilities.getOrDefault(characterClass.toLowerCase(),List.of());
        orderedAbilities.addAll(primaries);
        for(String a:abilities)
            if(!orderedAbilities.contains(a))
                orderedAbilities.add(a);
        Map<String,Integer> stats = new HashMap<>();
        for(int i = 0;i<6;i++)
            stats.put(orderedAbilities.get(i),rolls.get(i));
        characterJson.setStats(stats);
        characterJson.setRacialTraits(apiClient.getRaceTraits(race));
        characterJson.setClassFeatures(apiClient.getClassFeatures(characterClass));
        int baseHP = 6 +(level-1)*(6+modifier(stats.get("CON")));
        characterJson.setHitPoints(baseHP);
        characterJson.setSubClasses(apiClient.getSubClassesForClass(characterClass));
        return characterJson;
    }
    public CharactersData generateCharacter()
    {
        return apiClient.getClassesAndRacesDescription();
    }
    public Map<String,String> generateAbility()
    {
        return apiClient.getAbility();
    }
    public String generateAbilityByName(String name)
    {
        return apiClient.getAbilityByName(name);
    }
    public String generateFeaturesByName(String name){
        return apiClient.getFeaturesByName(name);
    }
    public String generateSubClassesByName(String name){
        return apiClient.getSubClassesByName(name);
    }
    public String generateSpellByName(String name){
        return apiClient.getSpellByName(name);
    }
    public EquipmentData generateStartingEquipment(String characterclass)
    {
        return apiClient.getStartingEquipment(characterclass);
    }
    public List<Spell> generateKnownSpells(String characterclass,int level)
    {
        return apiClient.getSpellsByClassAndLevel(characterclass,level);
    }
    private int roll() {
        List<Integer> rolls = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            rolls.add(random.nextInt(6) + 1);
        }
        sort(rolls);
        return rolls.get(1) + rolls.get(2) + rolls.get(3);
    }

    private void sort(List<Integer> rolls) {
        for (int i = 0; i < rolls.size() - 1; i++) {
            for (int j = 0; j < rolls.size() - i - 1; j++) {
                if (rolls.get(j) > rolls.get(j + 1)) {
                    int temp = rolls.get(j);
                    rolls.set(j, rolls.get(j + 1));
                    rolls.set(j + 1, temp);
                }
            }
        }
    }
    private void sortmal(List<Integer> rolls) {
        for (int i = 0; i < rolls.size() - 1; i++) {
            for (int j = 0; j < rolls.size() - i - 1; j++) {
                if (rolls.get(j) < rolls.get(j + 1)) {
                    int temp = rolls.get(j);
                    rolls.set(j, rolls.get(j + 1));
                    rolls.set(j + 1, temp);
                }
            }
        }
    }
    private int modifier(int stat) {
        return (stat - 10) / 2;
    }
}
