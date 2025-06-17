package br.ifba.ads.workshop.core.application.usecases.user.dtos;

import java.util.UUID;

public record UserOutput(
        UUID id,
        String name,
        String email,
        int userRoleId,
        int accessLevelId
)
{
}
