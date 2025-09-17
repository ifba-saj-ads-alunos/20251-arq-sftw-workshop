package br.ifba.ads.workshop.core.domain.models;

import java.time.ZonedDateTime;

public final class EventSession extends AuditableModel {
    private final ZonedDateTime startsAt;
    private final ZonedDateTime endsAt;
    private final Integer capacity;
    private final String room; // optional room name or identifier

    public EventSession(ZonedDateTime startsAt, ZonedDateTime endsAt, Integer capacity, String room) {
        super();
        this.startsAt = startsAt;
        this.endsAt = endsAt;
        this.capacity = capacity;
        this.room = room;
    }

    public EventSession(java.util.UUID id, java.time.ZonedDateTime createdAt, java.time.ZonedDateTime updatedAt, boolean deleted,
                   ZonedDateTime startsAt, ZonedDateTime endsAt, Integer capacity, String room) {
        super(id, createdAt, updatedAt, deleted);
        this.startsAt = startsAt;
        this.endsAt = endsAt;
        this.capacity = capacity;
        this.room = room;
    }

    public ZonedDateTime getStartsAt() { return startsAt; }
    public ZonedDateTime getEndsAt() { return endsAt; }
    public Integer getCapacity() { return capacity; }
    public String getRoom() { return room; }
}
