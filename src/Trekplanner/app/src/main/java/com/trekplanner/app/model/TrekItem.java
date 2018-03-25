package com.trekplanner.app.model;

/**
 * Created by sami on 16.3.2018.
 */

public class TrekItem {

    private Integer id;
    private Integer itemId;
    private Integer trekId;
    private Integer count;
    private String notes;
    private Double totalWeight;
    private String status;
    private boolean wasUsed;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public Integer getTrekId() {
        return trekId;
    }

    public void setTrekId(Integer trekId) {
        this.trekId = trekId;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Double getTotalWeight() {
        return totalWeight;
    }

    public void setTotalWeight(Double totalWeight) {
        this.totalWeight = totalWeight;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isWasUsed() {
        return wasUsed;
    }

    public void setWasUsed(boolean wasUsed) {
        this.wasUsed = wasUsed;
    }
}
