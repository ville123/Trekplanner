package com.trekplanner.app.model;

/**
 * Created by Shakur on 24.3.2018.
 */

public class TrekItem  {

    private int item_ID;
    private int trek_ID;
    private int count;
    private String notes;
    private Double totalWeight;
    private String status;
    private int was_used;

    public void setItem_ID(int item_ID){
        this.item_ID = item_ID;
    }

    public void setTrek_ID(int trek_ID){
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

    public void setWas_used(int was_used){
        this.was_used = was_used;
    }

    public int getItem_ID(){
        return item_ID;
    }

    public int getTrek_ID(){
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

    public int getWas_used(){
        return was_used;
    }
}
