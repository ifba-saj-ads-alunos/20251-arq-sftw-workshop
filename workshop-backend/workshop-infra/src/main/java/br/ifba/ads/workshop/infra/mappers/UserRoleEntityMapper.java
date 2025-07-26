package br.ifba.ads.workshop.infra.mappers;

import br.ifba.ads.workshop.core.domain.models.UserRole;
import br.ifba.ads.workshop.infra.persistence.entities.user.UserRoleEntity;
import org.springframework.stereotype.Component;

@Component
public final class UserRoleEntityMapper implements BaseEntityMapper<UserRole, UserRoleEntity>{

    @Override
    public UserRole toDomain(UserRoleEntity entity) {
        return new UserRole(
                entity.getId(),
                entity.getType()
        );
    }

    @Override
    public UserRoleEntity toEntity(UserRole domain) {
        return new UserRoleEntity(
                domain.getId(),
                domain.getType()
        );
    }
}
