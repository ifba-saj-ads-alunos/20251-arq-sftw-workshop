package br.ifba.ads.workshop.core.application.usecases;

import br.ifba.ads.workshop.core.application.dtos.UserOutput;

import java.util.UUID;

public interface FindUserByIdUseCase {
    UserOutput execute(UUID userId);
}
