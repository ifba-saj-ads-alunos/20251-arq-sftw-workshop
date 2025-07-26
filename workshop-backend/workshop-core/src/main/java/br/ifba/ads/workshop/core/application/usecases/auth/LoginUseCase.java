package br.ifba.ads.workshop.core.application.usecases.auth;

import br.ifba.ads.workshop.core.application.dtos.LoginCommand;
import br.ifba.ads.workshop.core.application.dtos.LoginOutput;

public interface LoginUseCase {
    LoginOutput execute(LoginCommand command);
}
