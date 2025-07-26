package br.ifba.ads.workshop.infra.configs.di;

import br.ifba.ads.workshop.core.application.gateways.PasswordEncoderGateway;
import br.ifba.ads.workshop.core.application.gateways.TransactionManagerGateway;
import br.ifba.ads.workshop.core.application.usecases.CreateUserUseCase;
import br.ifba.ads.workshop.core.application.usecases.CreateUserUseCaseImpl;
import br.ifba.ads.workshop.core.application.usecases.FindUserByIdUseCase;
import br.ifba.ads.workshop.core.application.usecases.FindUserByIdUseCaseImpl;
import br.ifba.ads.workshop.core.domain.repositories.AccessLevelRepository;
import br.ifba.ads.workshop.core.domain.repositories.UserRepository;
import br.ifba.ads.workshop.core.domain.repositories.UserRoleRepository;
import br.ifba.ads.workshop.core.domain.service.UserCreationDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class UserDIConfiguration {

    private final UserRepository userRepository;

    @Bean
    public UserCreationDomainService userCreationDomainService(
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

    @Bean
    public FindUserByIdUseCase findUserByIdUseCase(){
        return new FindUserByIdUseCaseImpl(userRepository);
    }
}
