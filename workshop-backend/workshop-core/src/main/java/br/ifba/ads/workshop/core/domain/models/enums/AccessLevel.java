package br.ifba.ads.workshop.core.domain.models.enums;

public enum AccessLevel {
    ADMIN(1),
    USER(2);

    private final int value;

    private AccessLevel(int value) {
        this.value = value;
    }
    public int getValue() {
        return value;
    }
}
