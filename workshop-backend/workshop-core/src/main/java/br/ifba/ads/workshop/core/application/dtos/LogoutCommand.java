package br.ifba.ads.workshop.core.application.dtos;

import java.util.UUID;

public record LogoutCommand(
        UUID userId,
        String token
) {
}
