package br.ifba.ads.workshop.core.application.usecases.auth.impl;

import br.ifba.ads.workshop.core.application.dtos.LoginCommand;
import br.ifba.ads.workshop.core.application.dtos.LoginOutput;
import br.ifba.ads.workshop.core.application.dtos.UserOutput;
import br.ifba.ads.workshop.core.application.gateways.PasswordEncoderGateway;
import br.ifba.ads.workshop.core.application.gateways.TokenManagerGateway;
import br.ifba.ads.workshop.core.application.gateways.TransactionManagerGateway;
import br.ifba.ads.workshop.core.application.usecases.auth.LoginUseCase;
import br.ifba.ads.workshop.core.domain.models.Session;
import br.ifba.ads.workshop.core.domain.models.User;
import br.ifba.ads.workshop.core.domain.exception.BusinessException;
import br.ifba.ads.workshop.core.domain.exception.InternalServerException;
import br.ifba.ads.workshop.core.domain.repositories.UserRepository;
import br.ifba.ads.workshop.core.domain.repositories.contracts.SaveOnlyRepository;

public final class LoginUseCaseImpl implements LoginUseCase {
    private final TokenManagerGateway tokenManagerGateway;
    private final PasswordEncoderGateway passwordEncoderGateway;
    private final UserRepository userRepository;
    private final SaveOnlyRepository<Session> sessionRepository;
    private final TransactionManagerGateway transactionManagerGateway;

    public LoginUseCaseImpl(
            TokenManagerGateway tokenManagerGateway,
            PasswordEncoderGateway passwordEncoderGateway,
            UserRepository userRepository,
            TransactionManagerGateway transactionManagerGateway,
            SaveOnlyRepository<Session> sessionRepository
    ) {
        this.tokenManagerGateway = tokenManagerGateway;
        this.passwordEncoderGateway = passwordEncoderGateway;
        this.userRepository = userRepository;
        this.transactionManagerGateway = transactionManagerGateway;
        this.sessionRepository = sessionRepository;
    }

    @Override
    public LoginOutput execute(LoginCommand command) {
        try {
            final var user = authenticateUser(command);
            final var token = tokenManagerGateway.generateToken(user);

            return transactionManagerGateway.doInTransaction(() -> {
                createUserSession(user, token, command);
                updateUserLastAccess(user);
                return new LoginOutput(UserOutput.fromUser(user), token);
            });

        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalServerException(
                    "Problema interno ao realizar login, tente novamente mais tarde", e
            );
        }
    }

    private User authenticateUser(LoginCommand command) {
        return userRepository.findByEmail(command.email().value())
                .filter(user -> passwordEncoderGateway.matches(command.password(), user.getPassword()))
                .orElseThrow(() -> new BusinessException("Email ou senha inválidos"));
    }

    private void createUserSession(User user, String token, LoginCommand command) {
        final var session = new Session(
                user.getId(),
                token,
                tokenManagerGateway.getIssuedAt(token),
                tokenManagerGateway.getExpirationDate(token),
                command.userAgent(),
                command.ipAddress(),
                true
        );
        sessionRepository.saveSafely(session);
    }

    private void updateUserLastAccess(User user) {
        user.updateLastAccess();
        userRepository.saveSafely(user);
    }
}