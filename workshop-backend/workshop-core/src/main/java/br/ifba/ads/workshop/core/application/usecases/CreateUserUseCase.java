package br.ifba.ads.workshop.core.application.usecases;

import br.ifba.ads.workshop.core.application.dtos.CreateUserCommand;
import br.ifba.ads.workshop.core.application.dtos.UserOutput;

public interface CreateUserUseCase {
    UserOutput execute(CreateUserCommand command);
}
