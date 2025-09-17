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
    private static final String VALID_CPF = "12345678901";

    private UserRole userRole;
    private AccessLevel accessLevel;
    private EncryptedPassword encryptedPassword;
    private Email email;

    @BeforeEach
    void setUp() {
        userRole = new UserRole(UUID.randomUUID(), UserRoleType.STUDENT);
        accessLevel = new AccessLevel(UUID.randomUUID(), AccessLevelType.USER);
        encryptedPassword = new EncryptedPassword("$2a$12$QXJ8wXKNjCnQogGT5yz7L.ISP7f6Z8dfzQ8RClwKBh.5ECUE5XouK");
        email = new Email("20232TADSSAJ1234@ifba.edu.br");
    }

    @Test
    void shouldThrowExceptionForEmptyName() {
        assertThrows(InvalidDataException.class, () -> new User("", VALID_CPF, email, userRole, accessLevel, encryptedPassword, null));
    }

    @Test
    void shouldThrowExceptionForLongName() {
        String longName = "a".repeat(101);
        assertThrows(InvalidDataException.class, () -> new User(longName, VALID_CPF, email, userRole, accessLevel, encryptedPassword, null));
    }

    @Test
    void shouldThrowExceptionForNullEmail() {
        assertThrows(InvalidDataException.class, () -> new User("Test", VALID_CPF, null, userRole, accessLevel, encryptedPassword, null));
    }

    @Test
    void shouldThrowExceptionForNullUserRole() {
        assertThrows(InvalidDataException.class, () -> new User("Test", VALID_CPF, email, null, accessLevel, encryptedPassword, null));
    }

    @Test
    void shouldThrowExceptionForNullAccessLevel() {
        assertThrows(InvalidDataException.class, () -> new User("Test", VALID_CPF, email, userRole, null, encryptedPassword, null));
    }

    @Test
    void shouldThrowExceptionWhenCpfIsNull() {
        assertThrows(InvalidDataException.class, () -> new User("Test", null, email, userRole, accessLevel, encryptedPassword, null));
    }

    @Test
    void shouldThrowExceptionWhenCpfIsBlank() {
        assertThrows(InvalidDataException.class, () -> new User("Test", "   ", email, userRole, accessLevel, encryptedPassword, null));
    }

    @Test
    void shouldThrowExceptionWhenCpfHasInvalidLength() {
        assertThrows(InvalidDataException.class, () -> new User("Test", "123456", email, userRole, accessLevel, encryptedPassword, null));
    }

    @Test
    void shouldNormalizeCpfRemovingNonDigits() {
        User user = new User("Test", "123.456.789-01", email, userRole, accessLevel, encryptedPassword, null);
        assertEquals(VALID_CPF, user.getCpf());
    }

    @Test
    void shouldAllowNullLastAccessOnCreation() {
        assertDoesNotThrow(() -> new User("Test", VALID_CPF, email, userRole, accessLevel, encryptedPassword, null));
    }

    @Test
    void shouldValidateLastAccess() {
        assertDoesNotThrow(() -> new User("Test", VALID_CPF, email, userRole, accessLevel, encryptedPassword, null));

        ZonedDateTime future = ZonedDateTime.now().plusDays(1);
        assertThrows(InvalidDataException.class, () ->
                new User("Test", VALID_CPF, email, userRole, accessLevel, encryptedPassword, future)
        );

        ZonedDateTime past = ZonedDateTime.now().minusDays(2);
        User user = new User("Test", VALID_CPF, email, userRole, accessLevel, encryptedPassword, past);
        assertEquals(past, user.getLastAccess());

        ZonedDateTime before = ZonedDateTime.now().minusSeconds(1);
        assertDoesNotThrow(user::updateLastAccess);
        assertTrue(user.getLastAccess().isAfter(before));
    }
}
