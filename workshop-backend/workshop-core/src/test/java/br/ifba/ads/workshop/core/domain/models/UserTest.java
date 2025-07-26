package br.ifba.ads.workshop.core.domain.models;

import br.ifba.ads.workshop.core.domain.exception.InvalidDataException;
import br.ifba.ads.workshop.core.domain.models.enums.AccessLevelType;
import br.ifba.ads.workshop.core.domain.models.enums.UserRoleType;
import br.ifba.ads.workshop.core.domain.models.valueObjects.Email;
import br.ifba.ads.workshop.core.domain.models.valueObjects.EncryptedPassword;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {
    private UserRole userRole;
    private AccessLevel accessLevel;
    private EncryptedPassword encryptedPassword;
    private Email email;

    @BeforeEach
    void setUp()  {
        userRole = new UserRole(UUID.randomUUID(), UserRoleType.STUDENT);
        accessLevel = new AccessLevel(UUID.randomUUID(), AccessLevelType.USER);
        encryptedPassword = new EncryptedPassword("$2a$12$QXJ8wXKNjCnQogGT5yz7L.ISP7f6Z8dfzQ8RClwKBh.5ECUE5XouK");
        email = new Email("20232TADSSAJ1234@ifba.edu.br");
    }

    @Test
    void shouldThrowExceptionForEmptyName() {
        assertThrows(InvalidDataException.class, () -> new User("", email, userRole, accessLevel, encryptedPassword, null));
    }

    @Test
    void shouldThrowExceptionForLongName() {
        String longName = "a".repeat(101);
        assertThrows(InvalidDataException.class, () -> new User(longName, email, userRole, accessLevel, encryptedPassword, null));
    }

    @Test
    void shouldThrowExceptionForNullEmail() {
        assertThrows(InvalidDataException.class, () -> new User("Test", null, userRole, accessLevel, encryptedPassword, null));
    }

    @Test
    void shouldThrowExceptionForNullUserRole() {
        assertThrows(InvalidDataException.class, () -> new User("Test", email, null, accessLevel, encryptedPassword, null));
    }

    @Test
    void shouldThrowExceptionForNullAccessLevel() {
        assertThrows(InvalidDataException.class, () -> new User("Test", email, userRole, null, encryptedPassword, null));
    }

    @Test
    void shouldAllowNullLastAccessOnCreation() {
        assertDoesNotThrow(() -> new User("Test", email, userRole, accessLevel, encryptedPassword, null));
    }

    @Test
    void shouldValidateLastAccess() {
        // Criação com lastAccess nulo (deve permitir)
        assertDoesNotThrow(() -> new User("Test", email, userRole, accessLevel, encryptedPassword, null));

        // Criação com lastAccess no futuro (deve lançar exceção)
        ZonedDateTime future = ZonedDateTime.now().plusDays(1);
        assertThrows(InvalidDataException.class, () ->
                new User("Test", email, userRole, accessLevel, encryptedPassword, future)
        );

        // Criação com valor válido (data no passado)
        ZonedDateTime past = ZonedDateTime.now().minusDays(2);
        User user = new User("Test", email, userRole, accessLevel, encryptedPassword, past);
        assertEquals(past, user.getLastAccess());

        // updateLastAccess deve atualizar com ZonedDateTime.now()
        ZonedDateTime before = ZonedDateTime.now().minusSeconds(1);
        assertDoesNotThrow(user::updateLastAccess);
        assertTrue(user.getLastAccess().isAfter(before));
    }
} 