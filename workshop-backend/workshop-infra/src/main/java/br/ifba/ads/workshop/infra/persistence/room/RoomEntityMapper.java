package br.ifba.ads.workshop.infra.persistence.room;

import br.ifba.ads.workshop.core.domain.models.Room;
import java.util.UUID;

public final class RoomEntityMapper {
    public static Room toDomain(RoomEntity e) {
        if (e == null) return null;
        return new Room(e.getId(), e.getCreatedAt(), e.getUpdatedAt(), e.isDeleted(), e.getName(), e.getCapacity(), e.getLocation());
    }

    public static RoomEntity toEntity(Room r) {
        if (r == null) return null;
        RoomEntity e = new RoomEntity();
        e.setId(r.getId() == null ? UUID.randomUUID() : r.getId());
        e.setName(r.getName());
        e.setCapacity(r.getCapacity());
        e.setLocation(r.getLocation());
        e.setCreatedAt(r.getCreatedAt());
        e.setUpdatedAt(r.getUpdatedAt());
    e.setDeleted(r.getDeleted() != null ? r.getDeleted() : false);
        return e;
    }
}
