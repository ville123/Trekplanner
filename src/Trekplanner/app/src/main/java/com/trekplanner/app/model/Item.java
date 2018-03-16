package com.trekplanner.app.model;

/**
 * Created by sami on 16.3.2018.
 */

public class Item {

    private Integer _ID;
    private String type;
    private String status;
    private Double weight;
    private String name;
    private String notes;
    private String pic;
    private boolean isDefault;
    private Double energy;
    private Double protein;
    private String deadline;

    public void set_ID(Integer _ID) {
        this._ID = _ID;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public void setDefault(boolean aDefault) {
        isDefault = aDefault;
    }

    public void setEnergy(Double energy) {
        this.energy = energy;
    }

    public void setProtein(Double protein) {
        this.protein = protein;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public Integer get_ID() {
        return _ID;
    }

    public String getType() {
        return type;
    }

    public String getStatus() {
        return status;
    }

    public Double getWeight() {
        return weight;
    }

    public String getName() {
        return name;
    }

    public String getNotes() {
        return notes;
    }

    public String getPic() {
        return pic;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public Double getEnergy() {
        return energy;
    }

    public Double getProtein() {
        return protein;
    }

    public String getDeadline() {
        return deadline;
    }
}
