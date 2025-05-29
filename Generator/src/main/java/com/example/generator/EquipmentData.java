package com.example.generator;

import java.util.List;

public class EquipmentData {
    private List<EquipmentOption> proposed;
    private List<EquipmentItem> selected;

    public EquipmentData(List<EquipmentOption> proposed, List<EquipmentItem> selected) {
        this.proposed = proposed;
        this.selected = selected;
    }

    public List<EquipmentOption> getProposed() {
        return proposed;
    }

    public void setProposed(List<EquipmentOption> proposed) {
        this.proposed = proposed;
    }

    public List<EquipmentItem> getSelected() {
        return selected;
    }

    public void setSelected(List<EquipmentItem> selected) {
        this.selected = selected;
    }
}
