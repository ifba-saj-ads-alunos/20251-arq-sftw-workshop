package br.ifba.ads.workshop.core.domain.models.valueObjects;

import br.ifba.ads.workshop.core.domain.exception.InternalServerException;
import br.ifba.ads.workshop.core.domain.exception.InvalidDataException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EncryptedPasswordTest {
    @Test
    void shouldAcceptValidBCryptHash() {
        assertDoesNotThrow(() -> new EncryptedPassword("$2a$12$QXJ8wXKNjCnQogGT5yz7L.ISP7f6Z8dfzQ8RClwKBh.5ECUE5XouK"));
    }

    @Test
    void shouldThrowExceptionForPlainPassword() {
        assertThrows(InternalServerException.class, () -> new EncryptedPassword("plainPassword"));
    }

    @Test
    void shouldThrowExceptionForEmptyPassword() {
        assertThrows(InvalidDataException.class, () -> new EncryptedPassword(""));
    }
} 