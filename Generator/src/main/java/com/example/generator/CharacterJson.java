package com.example.generator;

import java.util.List;
import java.util.Map;

public class CharacterJson {
    private String race;
    private String characterClass;
    private int level;
    private Map<String,Integer>stats;
    private List<String> racialTraits;
    private List<String>classFeatures;
    private List<Spell> spellsKnown;
    private EquipmentData equipment;
    private int hitPoints;

    public CharacterJson() {
    }
    public void setgenerate(String race,String characterClass,int level)
    {
        setRace(race);
        setCharacterClass(characterClass);
        setLevel(level);
    }
    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public String getCharacterClass() {
        return characterClass;
    }

    public void setCharacterClass(String characterClass) {
        this.characterClass = characterClass;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public Map<String, Integer> getStats() {
        return stats;
    }

    public void setStats(Map<String, Integer> stats) {
        this.stats = stats;
    }

    public List<String> getRacialTraits() {
        return racialTraits;
    }

    public void setRacialTraits(List<String> racialTraits) {
        this.racialTraits = racialTraits;
    }

    public List<String> getClassFeatures() {
        return classFeatures;
    }

    public void setClassFeatures(List<String> classFeatures) {
        this.classFeatures = classFeatures;
    }

    public List<Spell> getSpellsKnown() {
        return spellsKnown;
    }

    public void setSpellsKnown(List<Spell> spellsKnown) {
        this.spellsKnown = spellsKnown;
    }

    public EquipmentData getEquipment() {
        return equipment;
    }

    public void setEquipment(EquipmentData equipment) {
        this.equipment = equipment;
    }

    public int getHitPoints() {
        return hitPoints;
    }

    public void setHitPoints(int hitPoints) {
        this.hitPoints = hitPoints;
    }
}
