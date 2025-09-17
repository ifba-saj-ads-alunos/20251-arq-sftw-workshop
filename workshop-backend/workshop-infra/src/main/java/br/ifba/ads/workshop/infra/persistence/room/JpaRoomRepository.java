package br.ifba.ads.workshop.infra.persistence.room;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface JpaRoomRepository extends JpaRepository<RoomEntity, UUID> {
    Optional<RoomEntity> findByName(String name);
}
