package br.ifba.ads.workshop.core.domain.models.valueObjects;

import br.ifba.ads.workshop.core.domain.exception.InvalidDataException;
import br.ifba.ads.workshop.core.domain.exception.InternalServerException;


public record EncryptedPassword(String value) {
    public EncryptedPassword {
        if (value == null || value.trim().isEmpty()) {
            throw new InvalidDataException("Senha não pode ser vazia");
        }
        if (!value.matches("^\\$2[aby]\\$.{56}$")) {
            throw new InternalServerException("Senha deve estar criptografada (BCrypt)");
        }
    }
}