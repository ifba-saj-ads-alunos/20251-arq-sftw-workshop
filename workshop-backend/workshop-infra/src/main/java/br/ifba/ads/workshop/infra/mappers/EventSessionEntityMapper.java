package br.ifba.ads.workshop.infra.mappers;

import br.ifba.ads.workshop.core.domain.models.EventSession;
import br.ifba.ads.workshop.infra.persistence.entities.event.EventSessionEntity;
import org.springframework.stereotype.Component;

@Component
public final class EventSessionEntityMapper implements BaseEntityMapper<EventSession, EventSessionEntity> {
    @Override
    public EventSession toDomain(EventSessionEntity entity) {
        return new EventSession(
                entity.getId(),
                entity.getCreatedAt(),
                entity.getUpdatedAt(),
                entity.isDeleted(),
                entity.getStartsAt(),
                entity.getEndsAt(),
                entity.getCapacity(),
                entity.getRoom()
        );
    }

    @Override
    public EventSessionEntity toEntity(EventSession domain) {
        return EventSessionEntity.builder()
                .id(domain.getId())
                .eventId(null) // caller must set eventId
                .startsAt(domain.getStartsAt())
                .endsAt(domain.getEndsAt())
                .capacity(domain.getCapacity())
                .room(domain.getRoom())
                .createdAt(domain.getCreatedAt())
                .updatedAt(domain.getUpdatedAt())
                .deleted(domain.getDeleted())
                .build();
    }
}
