package br.ifba.ads.workshop.infra.persistence.repositories;

import br.ifba.ads.workshop.infra.persistence.entities.AccessLevelEntity;
import br.ifba.ads.workshop.core.domain.models.enums.AccessLevelType;

import java.util.Optional;


public interface JpaAccessLevelRepository extends JpaBaseRepository<AccessLevelEntity> {
    Optional<AccessLevelEntity> findByTypeAndDeletedIsFalse(AccessLevelType type);
}
