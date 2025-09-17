package br.ifba.ads.workshop.core.domain.repositories;

import br.ifba.ads.workshop.core.domain.models.Room;
import java.util.List;
import java.util.Optional;

public interface RoomRepository {
    Room save(Room room);
    Optional<Room> findById(java.util.UUID id);
    Optional<Room> findByName(String name);
    List<Room> findAll();
    void deleteById(java.util.UUID id);
}
