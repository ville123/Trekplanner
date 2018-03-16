package com.trekplanner.app.model;

/**
 * Created by sami on 16.3.2018.
 */

public class Trek {

    private Integer _ID;
    private String start;
    private String end;
    private String startCoords;
    private String endCoords;
    private String description;
    private String notes;
    private Double length;
    private String level;
    private String lessonsLearned;

    public Integer get_ID() {
        return _ID;
    }

    public void set_ID(Integer _ID) {
        this._ID = _ID;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getStartCoords() {
        return startCoords;
    }

    public void setStartCoords(String startCoords) {
        this.startCoords = startCoords;
    }

    public String getEndCoords() {
        return endCoords;
    }

    public void setEndCoords(String endCoords) {
        this.endCoords = endCoords;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Double getLength() {
        return length;
    }

    public void setLength(Double length) {
        this.length = length;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getLessonsLearned() {
        return lessonsLearned;
    }

    public void setLessonsLearned(String lessonsLearned) {
        this.lessonsLearned = lessonsLearned;
    }
}
