package br.ifba.ads.workshop.core.domain.models;

import br.ifba.ads.workshop.core.domain.exception.InvalidDataException;
import br.ifba.ads.workshop.core.domain.models.enums.AccessLevel;
import br.ifba.ads.workshop.core.domain.models.enums.UserRole;
import br.ifba.ads.workshop.core.domain.utils.UserRoleUtils;
import lombok.Getter;

@Getter
public class User extends ModelWithIdentifier {
    private String name;
    private String email;
    private UserRole userRole;
    private AccessLevel accessLevel;
    private String password;


    public User(String id, String name, String email, UserRole userRole, AccessLevel accessLevel, String password)
            throws InvalidDataException
    {
        super(id);
        this.name = name;
        this.email = email;
        this.userRole = userRole;
        this.accessLevel = accessLevel;
        this.password = password;
        validateUserData();
    }

    public void updateProfile(String name, String email) throws InvalidDataException {
        validateName(name);
        validateEmail(email);
        this.name = name;
        this.email = email;
    }

    public void assignRole(UserRole newUserRole, String email) throws InvalidDataException {
        validateUserRole(newUserRole, email);
        this.userRole = newUserRole;
    }

    public void changePassword(String newPassword) throws InvalidDataException {
        validatePassword(newPassword);
        this.password = newPassword;
    }

    private void validateUserData()
            throws InvalidDataException
    {
        validateName(this.name);
        validateEmail(this.email);
        validateUserRole(this.userRole, this.email);
        validatePassword(this.password);
        validateAccessLevel(this.accessLevel);
    }

    private void validateName(String name) throws InvalidDataException {
        if (name == null || name.trim().isEmpty()) {
            throw new InvalidDataException("Nome não pode ser vazio");
        }
        if (name.length() > 100) {
            throw new InvalidDataException("Nome não pode ter mais de 100 caracteres");
        }
    }

    private void validateEmail(String email)
            throws InvalidDataException
    {
        if (email == null || email.trim().isEmpty()) {
            throw new InvalidDataException("Email não pode ser vazio");
        }
        if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new InvalidDataException("Formato de email inválido");
        }
    }

    private void validateUserRole(UserRole userRole, String email)
            throws InvalidDataException
    {
        if (userRole == null) {
            throw new InvalidDataException("Tipo do usuário não pode ser nulo");
        }

        if (!UserRoleUtils.verifyUserRole(email, userRole)) {
            throw new InvalidDataException("Tipo do usuário não é válido para o email fornecido");
        }
    }

    private void validatePassword(String password)
            throws InvalidDataException
    {
        if (password == null || password.trim().isEmpty()) {
            throw new InvalidDataException("Senha não pode ser vazia");
        }
        if (password.length() < 8) {
            throw new InvalidDataException("Senha deve ter pelo menos 8 caracteres");
        }
    }

    private void validateAccessLevel(AccessLevel accessLevel)
            throws InvalidDataException
    {
        if (accessLevel == null) {
            throw new InvalidDataException("Nível de acesso não pode ser nulo");
        }
    }
}