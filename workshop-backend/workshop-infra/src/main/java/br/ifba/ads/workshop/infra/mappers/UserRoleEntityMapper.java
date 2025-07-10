package br.ifba.ads.workshop.infra.mappers;

import br.ifba.ads.workshop.core.domain.models.UserRole;
import br.ifba.ads.workshop.infra.persistence.entities.user.UserRoleEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserRoleEntityMapper extends BaseEntityMapper<UserRole, UserRoleEntity>{

    UserRole toDomain(UserRoleEntity entity);

    @Mapping(target = "deleted",  ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    UserRoleEntity toEntity(UserRole domain);
}
