package br.ifba.ads.workshop.web.dtos;

import br.ifba.ads.workshop.core.application.dtos.CreateUserCommand;
import br.ifba.ads.workshop.core.domain.models.enums.UserRoleType;
import br.ifba.ads.workshop.core.domain.models.valueObjects.Password;
import br.ifba.ads.workshop.core.domain.validators.EnumValidator;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record CreateUserRequestDto(
        @NotBlank(message = "Nome e obrigatorio")
        @Size(max = 100, message = "Nome deve ter no maximo 100 caracteres")
        String name,

        @NotBlank(message = "CPF e obrigatorio")
        @Pattern(regexp = "^\\d{3}\\.?\\d{3}\\.?\\d{3}-?\\d{2}$", message = "CPF deve conter 11 digitos numericos")
        String cpf,

        @NotBlank(message = "Email e obrigatorio")
        @Email(message = "Email invalido")
        String email,

        @NotBlank(message = "Senha e obrigatoria")
        @Size(min = 6, message = "Senha deve ter pelo menos 6 caracteres")
        String password,

        @NotBlank(message = "Perfil de usuario e obrigatorio")
        String userRole
) {
        public CreateUserCommand toCreateUserCommand() {
                EnumValidator.validateName(userRole, UserRoleType.class);
                return new CreateUserCommand(
                        name,
                        cpf,
                        new br.ifba.ads.workshop.core.domain.models.valueObjects.Email(email),
                        new Password(password),
                        UserRoleType.valueOf(userRole.toUpperCase())
                );
        }
}
