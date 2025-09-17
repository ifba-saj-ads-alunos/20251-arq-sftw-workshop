package br.ifba.ads.workshop.infra.adapters.repositories;

import br.ifba.ads.workshop.core.domain.models.EventSession;
import br.ifba.ads.workshop.core.domain.repositories.contracts.EventSessionRepository;
import br.ifba.ads.workshop.infra.mappers.EventSessionEntityMapper;
import br.ifba.ads.workshop.infra.persistence.entities.event.EventSessionEntity;
import br.ifba.ads.workshop.infra.persistence.repositories.JpaEventSessionRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class EventSessionRepositoryAdapter implements EventSessionRepository {

    private final JpaEventSessionRepository jpaRepo;
    private final EventSessionEntityMapper mapper;

    public EventSessionRepositoryAdapter(JpaEventSessionRepository jpaRepo, EventSessionEntityMapper mapper) {
        this.jpaRepo = jpaRepo;
        this.mapper = mapper;
    }

    @Override
    public List<EventSession> saveAllForEvent(UUID eventId, List<EventSession> sessions) {
        var entities = sessions.stream().map(s -> {
            var e = mapper.toEntity(s);
            e.setEventId(eventId);
            return e;
        }).collect(Collectors.toList());
        var saved = jpaRepo.saveAll(entities);
        return saved.stream().map(mapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public List<EventSession> findByEventId(UUID eventId) {
        return jpaRepo.findByEventIdOrderByStartsAtAsc(eventId).stream().map(mapper::toDomain).collect(Collectors.toList());
    }
}
