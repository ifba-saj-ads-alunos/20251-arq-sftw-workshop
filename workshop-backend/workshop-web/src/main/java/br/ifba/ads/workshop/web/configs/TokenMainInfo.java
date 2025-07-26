package br.ifba.ads.workshop.web.configs;

import br.ifba.ads.workshop.core.domain.models.enums.AccessLevelType;
import br.ifba.ads.workshop.core.domain.models.enums.UserRoleType;

import java.util.UUID;

public record TokenMainInfo(
        UUID userId,
        UserRoleType role,
        AccessLevelType accessLevel
) {
}
