package com.example.generator.Data;

import java.util.List;
import java.util.Map;

public class CharactersData {
    private Map<String, String> races;
    private List<String> classes;

    public Map<String, String> getRaces() {
        return races;
    }

    public void setRaces(Map<String, String> races) {
        this.races = races;
    }

    public List<String> getClasses() {
        return classes;
    }

    public void setClasses(List<String> classes) {
        this.classes = classes;
    }
}
