package br.ifba.ads.workshop.core.domain.repositories.contracts;

import br.ifba.ads.workshop.core.domain.models.EventSession;

import java.util.List;
import java.util.UUID;

public interface EventSessionRepository {
    List<EventSession> saveAllForEvent(UUID eventId, List<EventSession> sessions);
    List<EventSession> findByEventId(UUID eventId);
}
