package br.ifba.ads.workshop.api.mappers;

import br.ifba.ads.workshop.api.persistence.entities.user.UserEntity;
import br.ifba.ads.workshop.core.domain.models.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class UserEntityMapper implements BaseEntityMapper<User, UserEntity> {

    @Autowired
    protected EntityMapperService entityMapperService;

    @Mapping(target = "userRole", expression = "java(userEntity.getUserRole().getRole())")
    @Mapping(target = "accessLevel", expression = "java(userEntity.getAccessLevel().getLevel())")
    public abstract User toDomain(UserEntity userEntity);

    @Mapping(target = "userRole", expression = "java(entityMapperService.findUserRoleEntity(user.getUserRole()))")
    @Mapping(target = "accessLevel", expression = "java(entityMapperService.findAccessLevelEntity(user.getAccessLevel()))")
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "dateCreate", ignore = true)
    @Mapping(target = "dateUpdate", ignore = true)
    @Mapping(target = "isActive", ignore = true)
    public abstract UserEntity toEntity(User user);

}
