package br.ifba.ads.workshop.core.application.dtos;

import br.ifba.ads.workshop.core.domain.models.enums.EventModality;

import java.time.ZonedDateTime;

public record CreateEventCommand(
        String title,
        String description,
        ZonedDateTime startsAt,
        ZonedDateTime endsAt,
        Integer vacancies,
        EventModality modality,
        String location,
        String remoteLink,
        String category
) {}

