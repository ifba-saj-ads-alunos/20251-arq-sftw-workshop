package br.ifba.ads.workshop.api.mappers;

import br.ifba.ads.workshop.core.application.mappers.UserMapper;
import br.ifba.ads.workshop.core.application.usecases.user.dtos.CreateUserCommand;
import br.ifba.ads.workshop.core.application.usecases.user.dtos.UserOutput;
import br.ifba.ads.workshop.core.domain.models.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring", uses = {EntityMapperService.class})
public interface UserApiMapper extends UserMapper {
    @Override
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "userRole", expression = "java(UserRole.fromValue(command.userRoleId()))")
    @Mapping(target = "accessLevel", constant = "USER")
    User toDomain(CreateUserCommand command);

    @Override
    @Mapping(target = "id", expression = "java(UUID.fromString(user.getId()))")
    @Mapping(target = "userRoleId", expression = "java(user.getUserRole().getValue())")
    @Mapping(target = "accessLevelId", expression = "java(user.getAccessLevel().getValue())")

    UserOutput toOutput(User user);
}
