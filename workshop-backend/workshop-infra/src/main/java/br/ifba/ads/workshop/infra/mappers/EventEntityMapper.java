package br.ifba.ads.workshop.infra.mappers;

import br.ifba.ads.workshop.core.domain.models.Event;
import br.ifba.ads.workshop.core.domain.models.enums.EventModality;
import br.ifba.ads.workshop.core.domain.models.enums.EventStatus;
import br.ifba.ads.workshop.infra.persistence.entities.event.EventEntity;
import org.springframework.stereotype.Component;

@Component
public final class EventEntityMapper implements BaseEntityMapper<Event, EventEntity> {
    @Override
    public Event toDomain(EventEntity entity) {
        return new Event(
                entity.getId(),
                entity.getCreatedAt(),
                entity.getUpdatedAt(),
                entity.isDeleted(),
                entity.getTitle(),
                entity.getDescription(),
                entity.getStartsAt(),
                entity.getEndsAt(),
                entity.getVacancies(),
                EventModality.valueOf(entity.getModality()),
                EventStatus.valueOf(entity.getStatus()),
                entity.getLocation(),
                entity.getRemoteLink(),
                entity.getCategory()
        );
    }

    @Override
    public EventEntity toEntity(Event domain) {
        return EventEntity.builder()
                .id(domain.getId())
                .title(domain.getTitle())
                .description(domain.getDescription())
                .startsAt(domain.getStartsAt())
                .endsAt(domain.getEndsAt())
                .vacancies(domain.getVacancies())
                .modality(domain.getModality().name())
                .status(domain.getStatus().name())
                .location(domain.getLocation())
                .remoteLink(domain.getRemoteLink())
                .category(domain.getCategory())
                .createdAt(domain.getCreatedAt())
                .updatedAt(domain.getUpdatedAt())
                .deleted(domain.getDeleted())
                .build();
    }
}

