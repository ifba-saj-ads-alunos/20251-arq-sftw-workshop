package br.ifba.ads.workshop.core.domain.service;

import br.ifba.ads.workshop.core.domain.exception.InternalServerException;
import br.ifba.ads.workshop.core.domain.exception.InvalidDataException;
import br.ifba.ads.workshop.core.domain.exception.ResourceAlreadyExistsException;
import br.ifba.ads.workshop.core.domain.models.AccessLevel;
import br.ifba.ads.workshop.core.domain.models.User;
import br.ifba.ads.workshop.core.domain.models.UserRole;
import br.ifba.ads.workshop.core.domain.models.enums.AccessLevelType;
import br.ifba.ads.workshop.core.domain.models.enums.UserRoleType;
import br.ifba.ads.workshop.core.domain.models.valueObjects.Email;
import br.ifba.ads.workshop.core.domain.models.valueObjects.EncryptedPassword;
import br.ifba.ads.workshop.core.domain.repositories.AccessLevelRepository;
import br.ifba.ads.workshop.core.domain.repositories.UserRepository;
import br.ifba.ads.workshop.core.domain.repositories.UserRoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserCreationDomainServiceTest {
    @Mock UserRepository userRepository;
    @Mock AccessLevelRepository accessLevelRepository;
    @Mock UserRoleRepository userRoleRepository;
    @InjectMocks UserCreationDomainService service;

    String name;
    Email email;
    EncryptedPassword encryptedPassword;
    UserRoleType userRoleType;
    UserRole userRole;
    AccessLevel accessLevel;

    @BeforeEach
    void setUp() throws InternalServerException {
        name = UserRoleType.STUDENT.name();
        email = new Email("20232tadsaj1234@ifba.edu.br");
        encryptedPassword = new EncryptedPassword("$2a$12$m/q4H5FU2CCQ34ggdanrWOcPJX1iQRPs.B9VcZynoYO8W3P5oX1/u");
        userRoleType = UserRoleType.STUDENT;
        userRole = new UserRole(UUID.randomUUID(), UserRoleType.STUDENT);
        accessLevel = new AccessLevel(UUID.randomUUID(), AccessLevelType.USER);
    }

    @Test
    void shouldCreateUserSuccessfully() {
        when(userRoleRepository.findByType(userRoleType)).thenReturn(Optional.of(userRole));
        when(accessLevelRepository.findByType(AccessLevelType.USER)).thenReturn(Optional.of(accessLevel));
        when(userRepository.findByEmail(email.value())).thenReturn(Optional.empty());
        User user = service.createNewUser(name, email, encryptedPassword, userRoleType);
        assertNotNull(user);
        assertEquals(name, user.getName());
        assertEquals(email, user.getEmail());
        assertEquals(userRole, user.getUserRole());
        assertEquals(accessLevel, user.getAccessLevel());
        assertEquals(encryptedPassword, user.getPassword());
    }

    @Test
    void shouldThrowWhenUserAlreadyExists() {
        when(userRoleRepository.findByType(userRoleType)).thenReturn(Optional.of(userRole));
        when(accessLevelRepository.findByType(AccessLevelType.USER)).thenReturn(Optional.of(accessLevel));
        when(userRepository.findByEmail(email.value())).thenReturn(Optional.of(mock(User.class)));
        assertThrows(ResourceAlreadyExistsException.class, () ->
            service.createNewUser(name, email, encryptedPassword, userRoleType)
        );
    }

    @Test
    void shouldThrowWhenUserRoleNotFound() {
        when(userRoleRepository.findByType(userRoleType)).thenReturn(Optional.empty());
        assertThrows(InternalServerException.class, () ->
            service.createNewUser(name, email, encryptedPassword, userRoleType)
        );
    }

    @Test
    void shouldThrowWhenAccessLevelNotFound() {
        when(userRoleRepository.findByType(userRoleType)).thenReturn(Optional.of(userRole));
        when(accessLevelRepository.findByType(AccessLevelType.USER)).thenReturn(Optional.empty());
        assertThrows(InternalServerException.class, () ->
            service.createNewUser(name, email, encryptedPassword, userRoleType)
        );
    }
} 