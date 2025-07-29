package br.ifba.ads.workshop.infra.mappers;

import br.ifba.ads.workshop.core.domain.models.Event;
import br.ifba.ads.workshop.infra.persistence.entities.EventEntity;
import org.springframework.stereotype.Component;

@Component
public class EventEntityMapper implements BaseEntityMapper<Event, EventEntity> {
    @Override
    public Event toDomain(EventEntity entity) {
        return new Event(
            entity.getId(),
            entity.getCreatedAt(),
            entity.getUpdatedAt(),
            entity.isDeleted(),
            entity.getName(),
            entity.getDescription(),
            entity.getDate(),
            entity.getLocation()
        );
    }

    @Override
    public EventEntity toEntity(Event domain) {
        return EventEntity.builder()
            .id(domain.getId())
            .name(domain.getName())
            .description(domain.getDescription())
            .date(domain.getDate())
            .location(domain.getLocation())
            .createdAt(domain.getCreatedAt())
            .updatedAt(domain.getUpdatedAt())
            .deleted(domain.getDeleted())
            .build();
    }
}