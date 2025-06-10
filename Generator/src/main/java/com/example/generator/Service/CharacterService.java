package com.example.generator.Service;

import com.example.generator.Data.CharacterJson;
import com.example.generator.Api.DndApiClient;
import com.example.generator.Data.CharactersData;
import com.example.generator.Data.EquipmentData;
import com.example.generator.Data.Spell;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Random;

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
        Map<String,Integer>stats = Map.of(
                "STR", roll(),
                "DEX", roll(),
                "CON", roll(),
                "INT", roll(),
                "WIS", roll(),
                "CHA", roll()
        );
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
    public EquipmentData generateStartingEquipment(String characterclass)
    {
        return apiClient.getStartingEquipment(characterclass);
    }
    public List<Spell> generateKnownSpells(String characterclass,int level)
    {
        return apiClient.getSpellsByClassAndLevel(characterclass,level);
    }
    private int roll(){
        return random.nextInt(20)+1;
    }
    private int modifier(int stat) {
        return (stat - 10) / 2;
    }
}
