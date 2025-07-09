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
        assertThrows(InvalidDataException.class, () -> new User("", email, userRole, accessLevel, encryptedPassword));
    }

    @Test
    void shouldThrowExceptionForLongName() {
        String longName = "a".repeat(101);
        assertThrows(InvalidDataException.class, () -> new User(longName, email, userRole, accessLevel, encryptedPassword));
    }

    @Test
    void shouldThrowExceptionForNullEmail() {
        assertThrows(InvalidDataException.class, () -> new User("Test", null, userRole, accessLevel, encryptedPassword));
    }

    @Test
    void shouldThrowExceptionForNullUserRole() {
        assertThrows(InvalidDataException.class, () -> new User("Test", email, null, accessLevel, encryptedPassword));
    }

    @Test
    void shouldThrowExceptionForNullAccessLevel() {
        assertThrows(InvalidDataException.class, () -> new User("Test", email, userRole, null, encryptedPassword));
    }
} 