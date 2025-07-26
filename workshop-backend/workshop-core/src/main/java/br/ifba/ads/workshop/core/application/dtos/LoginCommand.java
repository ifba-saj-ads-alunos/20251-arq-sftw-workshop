package br.ifba.ads.workshop.core.application.dtos;

import br.ifba.ads.workshop.core.domain.models.valueObjects.Email;
import br.ifba.ads.workshop.core.domain.models.valueObjects.Password;

public record LoginCommand(
        Email email,
        Password password,
        String userAgent,
        String ipAddress
) {
}
