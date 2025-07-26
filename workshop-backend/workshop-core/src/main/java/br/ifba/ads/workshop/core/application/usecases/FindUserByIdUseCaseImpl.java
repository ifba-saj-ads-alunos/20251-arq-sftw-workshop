package br.ifba.ads.workshop.core.application.usecases;

import br.ifba.ads.workshop.core.application.dtos.UserOutput;
import br.ifba.ads.workshop.core.domain.exception.ResourceNotFoundException;
import br.ifba.ads.workshop.core.domain.models.User;
import br.ifba.ads.workshop.core.domain.repositories.contracts.ReadOnlyRepository;

import java.util.UUID;

public final class FindUserByIdUseCaseImpl implements FindUserByIdUseCase {

    private final ReadOnlyRepository<User> userRepository;

    public FindUserByIdUseCaseImpl(ReadOnlyRepository<User> userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserOutput execute(UUID userId) {
        return userRepository.findById(userId)
                .map(UserOutput::fromUser)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));
    }
}
