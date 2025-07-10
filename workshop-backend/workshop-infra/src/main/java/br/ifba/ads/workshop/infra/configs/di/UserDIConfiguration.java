package br.ifba.ads.workshop.infra.configs.di;

import br.ifba.ads.workshop.core.application.gateways.PasswordEncoderGateway;
import br.ifba.ads.workshop.core.application.gateways.TransactionManagerGateway;
import br.ifba.ads.workshop.core.application.usecases.CreateUserUseCase;
import br.ifba.ads.workshop.core.application.usecases.CreateUserUseCaseImpl;
import br.ifba.ads.workshop.core.domain.repositories.AccessLevelRepository;
import br.ifba.ads.workshop.core.domain.repositories.UserRepository;
import br.ifba.ads.workshop.core.domain.repositories.UserRoleRepository;
import br.ifba.ads.workshop.core.domain.service.UserCreationDomainService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserDIConfiguration {

    @Bean
    public UserCreationDomainService userCreationDomainService(
            UserRepository userRepository,
            AccessLevelRepository accessLevelRepository,
            UserRoleRepository userRoleRepository
    ){
        return new UserCreationDomainService(
                userRepository,
                accessLevelRepository,
                userRoleRepository
        );
    }

    @Bean
    public CreateUserUseCase createUserUseCase(
            UserCreationDomainService userCreationDomainService,
            PasswordEncoderGateway passwordEncoderGateway,
            TransactionManagerGateway transactionManagerGateway
    ){
        return new CreateUserUseCaseImpl(
                userCreationDomainService,
                passwordEncoderGateway,
                transactionManagerGateway
        );
    }
}
