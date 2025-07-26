package br.ifba.ads.workshop.core.application.dtos;

import br.ifba.ads.workshop.core.domain.models.User;
import br.ifba.ads.workshop.core.domain.models.enums.AccessLevelType;
import br.ifba.ads.workshop.core.domain.models.enums.UserRoleType;

import java.time.ZonedDateTime;
import java.util.UUID;

public record UserOutput(
        UUID id,
        String name,
        String email,
        UserRoleType userRole,
        AccessLevelType accessLevel,
        ZonedDateTime createdAt,
        ZonedDateTime updatedAt,
        ZonedDateTime lastAccess
)
{
   public static UserOutput fromUser(User user) {
       return new UserOutput(
               user.getId(),
               user.getName(),
               user.getEmail().value(),
               user.getUserRole().getType(),
               user.getAccessLevel().getType(),
               user.getCreatedAt(),
               user.getUpdatedAt(),
                user.getLastAccess()
       );
   }
}
