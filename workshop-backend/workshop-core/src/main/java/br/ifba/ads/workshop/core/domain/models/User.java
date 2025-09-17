package br.ifba.ads.workshop.core.domain.models;

import br.ifba.ads.workshop.core.domain.exception.InvalidDataException;
import br.ifba.ads.workshop.core.domain.models.enums.AccessLevelType;
import br.ifba.ads.workshop.core.domain.models.valueObjects.Email;
import br.ifba.ads.workshop.core.domain.models.valueObjects.EncryptedPassword;

import java.time.ZonedDateTime;
import java.util.UUID;

public class User extends AuditableModel {
    private String name;
    private String cpf;
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
            String cpf,
            Email email,
            UserRole userRole,
            AccessLevel accessLevel,
            EncryptedPassword password,
            ZonedDateTime lastAccess
    ) {
        super(id, createdAt, updatedAt, deleted);
        this.name = name;
        this.cpf = cpf;
        this.email = email;
        this.userRole = userRole;
        this.accessLevel = accessLevel;
        this.password = password;
        this.lastAccess = lastAccess;
        validateUserData();
    }

    public User(
            String name,
            String cpf,
            Email email,
            UserRole userRole,
            AccessLevel accessLevel,
            EncryptedPassword password,
            ZonedDateTime lastAccess
    ) {
        super();
        this.name = name;
        this.cpf = cpf;
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
        validateCpf(this.cpf);
        validateEmail(this.email);
        validateUserRole(this.userRole, this.email);
        validateAccessLevel(this.accessLevel);
        validateLastAccess(this.lastAccess);
    }

    private void validateCpf(String cpf) {
        if (cpf == null || cpf.trim().isEmpty()) {
            throw new InvalidDataException("CPF nao pode ser vazio");
        }
        String onlyDigits = cpf.replaceAll("\\D", "");
        if (onlyDigits.length() != 11) {
            throw new InvalidDataException("CPF deve conter 11 digitos");
        }
        this.cpf = onlyDigits;
    }

    private void validateName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new InvalidDataException("Nome nao pode ser vazio");
        }
        if (name.length() > 100) {
            throw new InvalidDataException("Nome nao pode ter mais de 100 caracteres");
        }
    }

    private void validateEmail(Email email) {
        if (email == null) {
            throw new InvalidDataException("Email nao pode ser nulo");
        }
    }

    private void validateUserRole(UserRole userRole, Email email) {
        if (userRole == null) {
            throw new InvalidDataException("Tipo do usuario nao pode ser nulo");
        }

        if (!userRole.verifyUserRole(email, userRole.getType())) {
            throw new InvalidDataException("Tipo do usuario invalido para o email fornecido");
        }
    }

    private void validateAccessLevel(AccessLevel accessLevel) {
        if (accessLevel == null) {
            throw new InvalidDataException("Nivel de acesso nao pode ser nulo");
        }
    }

    private void validateLastAccess(ZonedDateTime lastAccess) {
        if (this.lastAccess != null && lastAccess == null) {
            throw new InvalidDataException("Ultimo acesso nao pode ser nulo");
        }

        if (lastAccess == null) {
            return;
        }

        if (lastAccess.isAfter(ZonedDateTime.now())) {
            throw new InvalidDataException("Ultimo acesso nao pode ser no futuro");
        }

        if (this.lastAccess != null && lastAccess.isBefore(this.lastAccess)) {
            throw new InvalidDataException("Ultimo acesso nao pode ser anterior ao acesso anterior");
        }
    }

    public String getName() {
        return name;
    }

    public String getCpf() {
        return cpf;
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
