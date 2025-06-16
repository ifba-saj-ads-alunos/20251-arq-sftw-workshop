package br.ifba.ads.workshop.core.application.mappers;

import br.ifba.ads.workshop.core.application.usecases.user.dtos.CreateUserCommand;
import br.ifba.ads.workshop.core.application.usecases.user.dtos.UserOutput;
import br.ifba.ads.workshop.core.domain.models.User;

public interface UserMapper {
    User toDomain(CreateUserCommand command);
    UserOutput toOutput(User user);
}
