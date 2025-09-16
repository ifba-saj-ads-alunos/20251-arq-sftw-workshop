package br.ifba.ads.workshop.core.application.dtos;

import br.ifba.ads.workshop.core.domain.models.enums.UserRoleType;
import br.ifba.ads.workshop.core.domain.models.valueObjects.Email;
import br.ifba.ads.workshop.core.domain.models.valueObjects.Password;

public record CreateUserCommand (
        String name,
        String cpf,
        Email email,
        Password password,
        UserRoleType userRole
)
{

}
