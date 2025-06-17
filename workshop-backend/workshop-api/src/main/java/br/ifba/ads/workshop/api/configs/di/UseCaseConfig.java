package br.ifba.ads.workshop.api.configs.di;

import br.ifba.ads.workshop.core.application.mappers.UserMapper;
import br.ifba.ads.workshop.core.application.ports.in.CreateUserUseCase;
import br.ifba.ads.workshop.core.application.ports.out.PasswordEncoderPort;
import br.ifba.ads.workshop.core.application.ports.out.repositories.UserRepositoryPort;
import br.ifba.ads.workshop.core.application.usecases.user.CreateUserUseCaseImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class UseCaseConfig {

    private final PasswordEncoderPort passwordEncoderPort;
    private final UserRepositoryPort userRepositoryPort;
    private final UserMapper userMapper;

    @Bean
    public CreateUserUseCase createUserUseCase(
    ) {
        return new CreateUserUseCaseImpl(userRepositoryPort, userMapper, passwordEncoderPort);
    }
}
