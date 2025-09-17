package br.ifba.ads.workshop.infra.persistence.repositories;

import br.ifba.ads.workshop.infra.persistence.entities.event.EventSessionEntity;
import java.util.List;
import java.util.UUID;

public interface JpaEventSessionRepository extends JpaBaseRepository<EventSessionEntity> {
    List<EventSessionEntity> findByEventIdOrderByStartsAtAsc(UUID eventId);
}
