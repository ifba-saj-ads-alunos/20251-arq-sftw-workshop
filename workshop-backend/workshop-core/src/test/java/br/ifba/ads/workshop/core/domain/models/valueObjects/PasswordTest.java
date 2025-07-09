package br.ifba.ads.workshop.core.domain.models.valueObjects;

import br.ifba.ads.workshop.core.domain.exception.InvalidDataException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PasswordTest {
    @Test
    void shouldCreatePasswordWithValidValue() {
        assertDoesNotThrow(() -> new Password("validPass123"));
    }

    @Test
    void shouldThrowExceptionForShortPassword() {
        assertThrows(InvalidDataException.class, () -> new Password("short"));
    }

    @Test
    void shouldThrowExceptionForEmptyPassword() {
        assertThrows(IllegalArgumentException.class, () -> new Password(""));
    }
} 