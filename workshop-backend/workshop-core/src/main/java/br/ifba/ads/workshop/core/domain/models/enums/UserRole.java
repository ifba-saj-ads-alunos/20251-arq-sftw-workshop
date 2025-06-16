package br.ifba.ads.workshop.core.domain.models.enums;

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
        throw new IllegalArgumentException("Invalid UserRole value: " + value);
    }
}
