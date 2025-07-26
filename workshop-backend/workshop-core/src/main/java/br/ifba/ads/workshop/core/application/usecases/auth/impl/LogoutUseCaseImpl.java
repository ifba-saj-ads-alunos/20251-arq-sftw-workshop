package br.ifba.ads.workshop.core.application.usecases.auth.impl;

import br.ifba.ads.workshop.core.application.dtos.LogoutCommand;
import br.ifba.ads.workshop.core.application.gateways.TransactionManagerGateway;
import br.ifba.ads.workshop.core.application.usecases.auth.LogoutUseCase;
import br.ifba.ads.workshop.core.domain.exception.BusinessException;
import br.ifba.ads.workshop.core.domain.exception.InternalServerException;
import br.ifba.ads.workshop.core.domain.exception.ResourceNotFoundException;
import br.ifba.ads.workshop.core.domain.models.User;
import br.ifba.ads.workshop.core.domain.repositories.SessionRepository;
import br.ifba.ads.workshop.core.domain.repositories.contracts.ReadOnlyRepository;

public final class LogoutUseCaseImpl implements LogoutUseCase {
    private final TransactionManagerGateway transactionManager;
    private final ReadOnlyRepository<User> userRepository;
    private final SessionRepository sessionRepository;


    public LogoutUseCaseImpl(
            TransactionManagerGateway transactionManager,
            ReadOnlyRepository<User> userRepository,
            SessionRepository sessionRepository
    ) {
        this.transactionManager = transactionManager;
        this.userRepository = userRepository;
        this.sessionRepository = sessionRepository;
    }

    @Override
    public void execute(LogoutCommand command) {
        final var user = userRepository.findById(command.userId())
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));
        try {
            transactionManager.doInTransaction(() -> {
                final var session = sessionRepository.findByToken(command.token())
                        .orElseThrow(() -> new ResourceNotFoundException("Sessão não encontrada"));
                if (!session.getUserId().equals(user.getId())) {
                    throw new BusinessException("Sessão não pertence ao usuário");
                }
                session.desactive();
                sessionRepository.delete(session);
            });
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            throw new InternalServerException("Erro ao realizar logout, tente novamente mais tarde", e);
        }

    }
}
