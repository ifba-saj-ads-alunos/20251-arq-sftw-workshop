package br.ifba.ads.workshop.core.domain.models;

import br.ifba.ads.workshop.core.domain.models.enums.UserRoleType;
import br.ifba.ads.workshop.core.domain.models.valueObjects.Email;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class UserRoleTest {
    private UserRole userRole;
    private Email studentEmail;
    private Email employeeEmail;
    private Email visitorEmail;

    @BeforeEach
    void setUp() throws Exception {
        userRole = new UserRole(UUID.randomUUID(), UserRoleType.STUDENT);
        studentEmail = new Email("20232TADSSAJ1234@ifba.edu.br");
        employeeEmail = new Email("funcionario@ifba.edu.br");
        visitorEmail = new Email("visitante@gmail.com");
    }

    @Test
    void shouldReturnTrueForMatchingRoleAndEmail() {
        assertTrue(userRole.verifyUserRole(studentEmail, UserRoleType.STUDENT));
        assertTrue(userRole.verifyUserRole(employeeEmail, UserRoleType.EMPLOYEE));
    }

    @Test
    void shouldReturnFalseForMismatchedRoleAndEmail() {
        assertFalse(userRole.verifyUserRole(visitorEmail, UserRoleType.STUDENT));
    }

    @Test
    void shouldReturnFalseForNullRole() {
        assertFalse(userRole.verifyUserRole(studentEmail, null));
    }

    @Test
    void shouldReturnFalseForNullEmail() {
        assertFalse(userRole.verifyUserRole(null, UserRoleType.STUDENT));
    }
} 