package br.ifba.ads.workshop.core.application.usecases.auth;

import br.ifba.ads.workshop.core.application.dtos.LogoutCommand;

public interface LogoutUseCase {
    void execute(LogoutCommand command);
}
