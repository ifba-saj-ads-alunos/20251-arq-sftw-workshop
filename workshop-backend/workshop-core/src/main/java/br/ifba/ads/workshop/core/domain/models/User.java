package br.ifba.ads.workshop.core.domain.models;

import br.ifba.ads.workshop.core.domain.exception.InvalidDataException;
import br.ifba.ads.workshop.core.domain.models.enums.AccessLevelType;
import br.ifba.ads.workshop.core.domain.models.valueObjects.Email;
import br.ifba.ads.workshop.core.domain.models.valueObjects.EncryptedPassword;
import java.time.ZonedDateTime;
import java.util.UUID;

public class User extends AuditableModel {
    private String name;
    private Email email;
    private UserRole userRole;
    private AccessLevel accessLevel;
    private EncryptedPassword password;
    private ZonedDateTime lastAccess;

    public User(
            UUID id,
            ZonedDateTime createdAt,
            ZonedDateTime updatedAt,
            boolean deleted,
            String name,
            Email email,
            UserRole userRole,
            AccessLevel accessLevel,
            EncryptedPassword password,
            ZonedDateTime lastAccess
    ) {
        super(id, createdAt, updatedAt, deleted);
        this.name = name;
        this.email = email;
        this.userRole = userRole;
        this.accessLevel = accessLevel;
        this.password = password;
        this.lastAccess = lastAccess;
        validateUserData();
    }

    public User(
            String name,
            Email email,
            UserRole userRole,
            AccessLevel accessLevel,
            EncryptedPassword password,
            ZonedDateTime lastAccess
    ) {
        super();
        this.name = name;
        this.email = email;
        this.userRole = userRole;
        this.accessLevel = accessLevel;
        this.password = password;
        this.lastAccess = lastAccess;
        validateUserData();
    }

    public Boolean isAdmin() {
        return this.accessLevel.getType().equals(AccessLevelType.ADMIN);
    }

    public void updateLastAccess() {
        this.lastAccess = ZonedDateTime.now();
    }


    private void validateUserData() {
        validateName(this.name);
        validateEmail(this.email);
        validateUserRole(this.userRole, this.email);
        validateAccessLevel(this.accessLevel);
        validateLastAccess(this.lastAccess);
    }

    private void validateName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new InvalidDataException("Nome não pode ser vazio");
        }
        if (name.length() > 100) {
            throw new InvalidDataException("Nome não pode ter mais de 100 caracteres");
        }
    }

    private void validateEmail(Email email) {
        if (email == null) {
            throw new InvalidDataException("Email não pode ser nulo");
        }
    }

    private void validateUserRole(UserRole userRole, Email email) {
        if (userRole == null) {
            throw new InvalidDataException("Tipo do usuário não pode ser nulo");
        }

        if (!userRole.verifyUserRole(email, userRole.getType())) {
            throw new InvalidDataException("Tipo do usuário não é válido para o email fornecido");
        }
    }

    private void validateAccessLevel(AccessLevel accessLevel) {
        if (accessLevel == null) {
            throw new InvalidDataException("Nível de acesso não pode ser nulo");
        }
    }

    private void validateLastAccess(ZonedDateTime lastAccess) {
        if (this.lastAccess != null && lastAccess == null) {
            throw new InvalidDataException("Último acesso não pode ser nulo");
        }

        if (lastAccess == null) {
            return;
        }

        if (lastAccess.isAfter(ZonedDateTime.now())) {
            throw new InvalidDataException("Último acesso não pode ser no futuro");
        }

        if(this.lastAccess != null && lastAccess.isBefore(this.lastAccess)) {
            throw new InvalidDataException("Último acesso não pode ser anterior ao acesso anterior");
        }
    }

    public String getName() {
        return name;
    }

    public Email getEmail() {
        return email;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public AccessLevel getAccessLevel() {
        return accessLevel;
    }

    public EncryptedPassword getPassword() {
        return password;
    }

    public ZonedDateTime getLastAccess() {
        return lastAccess;
    }
}