package br.ifba.ads.workshop.core.domain.models;

public final class Room extends AuditableModel {
    private final String name;
    private final Integer capacity;
    private final String location;

    public Room(String name, Integer capacity, String location) {
        super();
        this.name = name;
        this.capacity = capacity;
        this.location = location;
    }

    public Room(java.util.UUID id, java.time.ZonedDateTime createdAt, java.time.ZonedDateTime updatedAt, boolean deleted,
                String name, Integer capacity, String location) {
        super(id, createdAt, updatedAt, deleted);
        this.name = name;
        this.capacity = capacity;
        this.location = location;
    }

    public String getName() { return name; }
    public Integer getCapacity() { return capacity; }
    public String getLocation() { return location; }
}
