package com.trekplanner.app.model;

/**
 * Created by Shakur on 24.3.2018.
 */

public class TrekItem  {

    private Long id;
    private Long item_ID;
    private Long trek_ID;
    private int count;
    private String notes;
    private Double totalWeight;
    private String status;
    private boolean was_used;
    private Item item;

    public void setId(Long id) {
        this.id = id;
    }

    public void setItem_ID(Long item_ID){
        this.item_ID = item_ID;
    }

    public void setTrek_ID(Long trek_ID){
        this.trek_ID = trek_ID;
    }

    public void setCount(int count){
        this.count = count;
    }

    public void setNotes(String notes){
        this.notes = notes;
    }

    public void setTotalWeight(double totalWeight){
        this.totalWeight = totalWeight;
    }

    public void setStatus(String status){
        this.status = status;
    }

    public void setWas_used(boolean was_used){
        this.was_used = was_used;
    }

    public Long getId() {
        return id;
    }

    public Long getItem_ID(){
        return item_ID;
    }

    public Long getTrek_ID(){
        return trek_ID;
    }

    public int getCount(){
        return count;
    }

    public String getNotes(){
        return notes;
    }

    public Double getTotalWeight(){
        return totalWeight;
    }

    public String getStatus(){
        return status;
    }

    public boolean getWas_used(){
        return was_used;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Item getItem() {
        return item;
    }
}