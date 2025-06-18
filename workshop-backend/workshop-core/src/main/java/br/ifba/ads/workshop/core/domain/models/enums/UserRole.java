package br.ifba.ads.workshop.core.domain.models.enums;

import br.ifba.ads.workshop.core.domain.exception.InvalidDataException;

public enum UserRole {
    STUDENT(1),
    EMPLOYEE(2),
    TEACHER(3),
    VISITOR(4);

    private final int value;

    private UserRole(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static UserRole fromValue(int value) {
        for (UserRole role : UserRole.values()) {
            if (role.getValue() == value) {
                return role;
            }
        }
        throw new InvalidDataException("Invalid UserRole value: " + value);
    }
}
