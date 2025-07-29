package br.ifba.ads.workshop.core.domain.models;

import java.time.ZonedDateTime;
import java.util.UUID;

public class Event extends AuditableModel {
    private String name;
    private String description;
    private String date;
    private String location;

    public Event(String date, String description, String location, String name) {
        this.date = date;
        this.description = description;
        this.location = location;
        this.name = name;
    }

    public Event(UUID id, ZonedDateTime createdAt, ZonedDateTime updatedAt, Boolean deleted, String date, String description, String location, String name) {
        super(id, createdAt, updatedAt, deleted);
        this.date = date;
        this.description = description;
        this.location = location;
        this.name = name;
    }
    
    public Event(UUID id, ZonedDateTime createdAt, ZonedDateTime updatedAt, Boolean deleted) {
        super(id, createdAt, updatedAt, deleted);
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }
    
    
}
     