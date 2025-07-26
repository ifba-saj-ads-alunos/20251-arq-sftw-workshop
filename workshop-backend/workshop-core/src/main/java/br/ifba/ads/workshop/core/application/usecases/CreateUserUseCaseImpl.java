package br.ifba.ads.workshop.core.application.usecases;

import br.ifba.ads.workshop.core.application.gateways.PasswordEncoderGateway;
import br.ifba.ads.workshop.core.application.gateways.TransactionManagerGateway;
import br.ifba.ads.workshop.core.application.dtos.CreateUserCommand;
import br.ifba.ads.workshop.core.application.dtos.UserOutput;
import br.ifba.ads.workshop.core.domain.exception.BusinessException;
import br.ifba.ads.workshop.core.domain.exception.InternalServerException;
import br.ifba.ads.workshop.core.domain.service.UserCreationDomainService;

public final class CreateUserUseCaseImpl implements CreateUserUseCase {
    private final UserCreationDomainService userCreationDomainService;
    private final PasswordEncoderGateway passwordEncoder;
    private final TransactionManagerGateway transactionManager;

    public CreateUserUseCaseImpl(
            UserCreationDomainService userCreationDomainService,
            PasswordEncoderGateway passwordEncoder,
            TransactionManagerGateway transactionManager
    ) {
        this.userCreationDomainService = userCreationDomainService;
        this.passwordEncoder = passwordEncoder;
        this.transactionManager = transactionManager;
    }

    @Override
    public UserOutput execute(CreateUserCommand command) {
        try {
            return transactionManager.doInTransaction(() -> {
                var encodedPassword = passwordEncoder.encode(command.password());
                var newUser = userCreationDomainService.createNewUser(
                        command.name(),
                        command.email(),
                        encodedPassword,
                        command.userRole()
                );
                return UserOutput.fromUser(newUser);
            });
        } catch (BusinessException e){
            throw e;
        } catch (Exception e){
            throw new InternalServerException("Problema ao criar um usuário, tente novamente mais tarde", e);
        }
    }
}
