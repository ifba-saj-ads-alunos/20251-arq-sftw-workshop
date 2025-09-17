package br.ifba.ads.workshop.infra.persistence.room;

import br.ifba.ads.workshop.core.domain.models.Room;
import br.ifba.ads.workshop.core.domain.repositories.RoomRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class RoomRepositoryAdapter implements RoomRepository {
    private final JpaRoomRepository jpa;

    public RoomRepositoryAdapter(JpaRoomRepository jpa) {
        this.jpa = jpa;
    }

    @Override
    public Room save(Room room) {
        RoomEntity e = RoomEntityMapper.toEntity(room);
        RoomEntity saved = jpa.save(e);
        return RoomEntityMapper.toDomain(saved);
    }

    @Override
    public Optional<Room> findById(UUID id) {
        return jpa.findById(id).map(RoomEntityMapper::toDomain);
    }

    @Override
    public Optional<Room> findByName(String name) {
        return jpa.findByName(name).map(RoomEntityMapper::toDomain);
    }

    @Override
    public List<Room> findAll() {
        return jpa.findAll().stream().map(RoomEntityMapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public void deleteById(UUID id) {
        jpa.deleteById(id);
    }
}
