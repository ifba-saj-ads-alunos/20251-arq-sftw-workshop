package br.ifba.ads.workshop.core.domain.models.valueObjects;

import br.ifba.ads.workshop.core.domain.models.enums.AccessLevelType;
import br.ifba.ads.workshop.core.domain.models.enums.UserRoleType;

import java.util.UUID;

public record AuthenticatedUser(
        UUID userId,
        AccessLevelType accessLevel,
        UserRoleType userRole,
        Email email,
        EncryptedPassword password
) {
}
