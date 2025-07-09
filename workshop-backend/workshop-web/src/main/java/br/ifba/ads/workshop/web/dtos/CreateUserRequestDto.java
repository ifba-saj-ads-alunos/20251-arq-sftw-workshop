package br.ifba.ads.workshop.web.dtos;

import br.ifba.ads.workshop.core.application.dtos.CreateUserCommand;
import br.ifba.ads.workshop.core.domain.models.enums.UserRoleType;
import br.ifba.ads.workshop.core.domain.models.valueObjects.Password;
import br.ifba.ads.workshop.core.domain.validators.EnumValidator;

public record CreateUserRequestDto(
        String name,
        String email,
        String password,
        String userRole
) {
        public CreateUserCommand  toCreateUserCommand() {
                EnumValidator.validateName(userRole, UserRoleType.class);
                return new CreateUserCommand(
                        name,
                        new br.ifba.ads.workshop.core.domain.models.valueObjects.Email(
                                email
                        ),
                        new Password(password),
                        UserRoleType.valueOf(userRole.toUpperCase())
                );
        }
}
