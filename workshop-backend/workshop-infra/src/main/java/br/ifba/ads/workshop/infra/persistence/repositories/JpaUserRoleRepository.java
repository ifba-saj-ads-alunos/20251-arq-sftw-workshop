package br.ifba.ads.workshop.infra.persistence.repositories;

import br.ifba.ads.workshop.core.domain.models.enums.UserRoleType;
import br.ifba.ads.workshop.infra.persistence.entities.user.UserRoleEntity;

import java.util.Optional;

public interface JpaUserRoleRepository extends JpaBaseRepository<UserRoleEntity> {
    Optional<UserRoleEntity> findByTypeAndDeletedIsFalse(UserRoleType type);
}
