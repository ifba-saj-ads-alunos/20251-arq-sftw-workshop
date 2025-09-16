package br.ifba.ads.workshop.core.application.usecases.user;

import br.ifba.ads.workshop.core.application.dtos.CreateUserCommand;
import br.ifba.ads.workshop.core.application.dtos.UserOutput;
import br.ifba.ads.workshop.core.application.gateways.PasswordEncoderGateway;
import br.ifba.ads.workshop.core.application.gateways.TransactionManagerGateway;
import br.ifba.ads.workshop.core.application.usecases.CreateUserUseCaseImpl;
import br.ifba.ads.workshop.core.domain.exception.InternalServerException;
import br.ifba.ads.workshop.core.domain.models.User;
import br.ifba.ads.workshop.core.domain.models.enums.UserRoleType;
import br.ifba.ads.workshop.core.domain.models.enums.AccessLevelType;
import br.ifba.ads.workshop.core.domain.models.valueObjects.Email;
import br.ifba.ads.workshop.core.domain.models.valueObjects.EncryptedPassword;
import br.ifba.ads.workshop.core.domain.service.UserCreationDomainService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateUserUseCaseImplTest {
    @Mock UserCreationDomainService userCreationDomainService;
    @Mock PasswordEncoderGateway passwordEncoder;
    @Mock TransactionManagerGateway transactionManager;
    @InjectMocks CreateUserUseCaseImpl useCase;

    CreateUserCommand command;
    User user;
    UserOutput userOutput;
    Email email;
    EncryptedPassword encryptedPassword;

    @BeforeEach
    void setUp() {
        email = new Email("test@ifba.edu.br");
        //12345
        encryptedPassword = new EncryptedPassword("$2a$12$m/q4H5FU2CCQ34ggdanrWOcPJX1iQRPs.B9VcZynoYO8W3P5oX1/u");
    command = new CreateUserCommand("Test User", "12345678901", email, new br.ifba.ads.workshop.core.domain.models.valueObjects.Password("password123"), UserRoleType.STUDENT);
        user = mock(User.class);
        userOutput = new UserOutput(UUID.randomUUID(),"Test User", email.value(), UserRoleType.STUDENT, AccessLevelType.USER, null, null, null);
    }

    private void mockTransactionManager() {
        when(transactionManager.doInTransaction(any(Supplier.class))).thenAnswer(invocation -> ((Supplier<?>) invocation.getArgument(0)).get());
    }

    @Test
    void shouldCreateUserSuccessfully() {
        mockTransactionManager();
        when(passwordEncoder.encode(command.password())).thenReturn(encryptedPassword);
    when(userCreationDomainService.createNewUser(command.name(), command.cpf(), command.email(), encryptedPassword, command.userRole())).thenReturn(user);
        try (var mocked = mockStatic(UserOutput.class)) {
            mocked.when(() -> UserOutput.fromUser(user)).thenReturn(userOutput);
            UserOutput result = useCase.execute(command);
            assertNotNull(result);
            assertEquals(userOutput, result);
        }
    }

    @Test
    void shouldThrowInternalServerExceptionWhenDomainServiceFails() {
        mockTransactionManager();
        when(passwordEncoder.encode(command.password())).thenReturn(encryptedPassword);
    when(userCreationDomainService.createNewUser(any(), any(), any(), any(), any())).thenThrow(new InternalServerException("Erro ao criar usuário"));
        assertThrows(InternalServerException.class, () -> useCase.execute(command));
    }

    @Test
    void shouldThrowInternalServerExceptionWhenPasswordEncoderFails() {
        mockTransactionManager();
        when(passwordEncoder.encode(command.password())).thenThrow(new RuntimeException("Encoder error"));
        assertThrows(InternalServerException.class, () -> useCase.execute(command));
    }

    @Test
    void shouldThrowInternalServerExceptionWhenTransactionFails() {
        when(transactionManager.doInTransaction(any(Supplier.class))).thenThrow(new RuntimeException("Transaction error"));
        assertThrows(InternalServerException.class, () -> useCase.execute(command));
    }
}