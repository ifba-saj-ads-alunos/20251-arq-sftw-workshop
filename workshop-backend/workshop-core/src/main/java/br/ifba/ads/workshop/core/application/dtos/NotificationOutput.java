package br.ifba.ads.workshop.core.application.dtos;

import java.time.ZonedDateTime;
import java.util.UUID;

public record NotificationOutput(
        UUID id,
        UUID userId,
        String title,
        String message,
        ZonedDateTime sentAt,
        Boolean read
) {}
