package br.ifba.ads.workshop.core.domain.models.valueObjects;

import br.ifba.ads.workshop.core.domain.exception.InvalidDataException;

public record Password(String value) {
    public Password {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("Senha não pode ser vazia");
        }
        if (value.length() < 8) {
            throw new InvalidDataException("Senha deve ter pelo menos 8 caracteres");
        }
    }
}
