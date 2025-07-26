package br.ifba.ads.workshop.infra.configs.di;

import br.ifba.ads.workshop.core.application.gateways.PasswordEncoderGateway;
import br.ifba.ads.workshop.core.application.gateways.TokenManagerGateway;
import br.ifba.ads.workshop.core.application.gateways.TransactionManagerGateway;
import br.ifba.ads.workshop.core.application.usecases.auth.LoginUseCase;
import br.ifba.ads.workshop.core.application.usecases.auth.LogoutUseCase;
import br.ifba.ads.workshop.core.application.usecases.auth.impl.LoginUseCaseImpl;
import br.ifba.ads.workshop.core.application.usecases.auth.impl.LogoutUseCaseImpl;
import br.ifba.ads.workshop.core.domain.repositories.SessionRepository;
import br.ifba.ads.workshop.core.domain.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class UserAuthDIConfiguration {

    private final UserRepository userRepository;
    private final SessionRepository sessionRepository;
    private final TransactionManagerGateway transactionManagerGateway;

    @Bean
    LoginUseCase loginUseCase(
            TokenManagerGateway tokenManagerGateway,
            PasswordEncoderGateway passwordEncoderGateway
    ) {
        return new LoginUseCaseImpl(
                tokenManagerGateway,
                passwordEncoderGateway,
                userRepository,
                transactionManagerGateway,
                sessionRepository
        );
    }

    @Bean
    LogoutUseCase logoutUseCase() {
        return new LogoutUseCaseImpl(
                transactionManagerGateway,
                userRepository,
                sessionRepository
        );
    }
}
