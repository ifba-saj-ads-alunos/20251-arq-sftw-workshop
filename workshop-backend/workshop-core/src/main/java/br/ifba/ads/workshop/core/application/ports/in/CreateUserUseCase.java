package br.ifba.ads.workshop.core.application.ports.in;

import br.ifba.ads.workshop.core.application.exceptions.InvalidOperationException;
import br.ifba.ads.workshop.core.application.exceptions.ResourceAlreadyExistsException;
import br.ifba.ads.workshop.core.application.usecases.user.dtos.CreateUserCommand;
import br.ifba.ads.workshop.core.application.usecases.user.dtos.UserOutput;

public interface CreateUserUseCase {
    UserOutput execute(CreateUserCommand command)
            throws InvalidOperationException, ResourceAlreadyExistsException;

}
