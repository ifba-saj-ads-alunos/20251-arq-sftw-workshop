package br.ifba.ads.workshop.core.domain.models.valueObjects;

import br.ifba.ads.workshop.core.domain.exception.InvalidDataException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmailTest {
    @Test
    void shouldAcceptValidIfbaEmail() {
        assertDoesNotThrow(() -> new Email("20232TADSSAJ1234@ifba.edu.br"));
    }

    @Test
    void shouldThrowExceptionForInvalidFormat() {
        assertThrows(InvalidDataException.class, () -> new Email("invalidemail"));
    }

    @Test
    void shouldThrowExceptionForEmptyEmail() {
        assertThrows(InvalidDataException.class, () -> new Email(""));
    }

    @Test
    void shouldIdentifyStudentEmail() {
        Email email = new Email("20232TADSSAJ1234@ifba.edu.br");
        assertTrue(email.isStudentEmail());
    }

    @Test
    void shouldIdentifyIfbaEmail() {
        Email email = new Email("Professor@ifba.edu.br");
        assertTrue(email.isIfbaEmail());
    }
} 