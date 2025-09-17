package br.ifba.ads.workshop.core.application.dtos;

import br.ifba.ads.workshop.core.domain.models.Event;
import br.ifba.ads.workshop.core.domain.models.enums.EventModality;
import br.ifba.ads.workshop.core.domain.models.enums.EventStatus;

import java.time.ZonedDateTime;
import java.util.UUID;

public record EventOutput(
        UUID id,
        String title,
        String description,
        ZonedDateTime startsAt,
        ZonedDateTime endsAt,
        Integer vacancies,
        EventModality modality,
        EventStatus status,
        String location,
        String remoteLink,
        String category,
        UUID organizerId,
        String rejectionJustification
) {
    public static EventOutput fromDomain(Event event) {
        return new EventOutput(
                event.getId(),
                event.getTitle(),
                event.getDescription(),
                event.getStartsAt(),
                event.getEndsAt(),
                event.getVacancies(),
                event.getModality(),
                event.getStatus(),
                event.getLocation(),
                event.getRemoteLink(),
                event.getCategory(),
                event.getOrganizerId(),
                event.getRejectionJustification()
        );
    }
}

