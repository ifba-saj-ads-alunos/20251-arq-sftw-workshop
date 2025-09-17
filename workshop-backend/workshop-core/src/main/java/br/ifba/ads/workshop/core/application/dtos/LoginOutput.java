package br.ifba.ads.workshop.core.application.dtos;

import br.ifba.ads.workshop.core.domain.models.enums.AccessLevelType;
import br.ifba.ads.workshop.core.domain.models.enums.UserRoleType;

public record LoginOutput(
        UserOutput user,
        String token,
        UserRoleType role,
        AccessLevelType accessLevel
) {
}
