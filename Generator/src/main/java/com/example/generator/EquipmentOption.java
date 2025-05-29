package com.example.generator;

import java.util.List;

public class EquipmentOption {
    private String description;
    private List<String> options;

    public EquipmentOption(String desc, List<String> choices) {
        this.description =desc;
        this.options=choices;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }
}
