package br.ifba.ads.workshop.infra.adapters.repositories;

import org.springframework.stereotype.Component;

import br.ifba.ads.workshop.core.domain.models.Event;
import br.ifba.ads.workshop.core.domain.repositories.contracts.EventRepository;
import br.ifba.ads.workshop.infra.mappers.EventEntityMapper;
import br.ifba.ads.workshop.infra.persistence.entities.EventEntity;
import br.ifba.ads.workshop.infra.persistence.repositories.JpaEventRepository;

@Component
public class EventRepositoryAdapter implements EventRepository {
    private final JpaEventRepository jpaEventRepository;
    private final EventEntityMapper mapper;

    public EventRepositoryAdapter(JpaEventRepository jpaEventRepository, EventEntityMapper mapper) {
        this.jpaEventRepository = jpaEventRepository;
        this.mapper = mapper;
    }

    @Override
    public Event save(Event event) {
        EventEntity entity = mapper.toEntity(event);
        EventEntity saved = jpaEventRepository.save(entity);
        return mapper.toDomain(saved);
    }

}
