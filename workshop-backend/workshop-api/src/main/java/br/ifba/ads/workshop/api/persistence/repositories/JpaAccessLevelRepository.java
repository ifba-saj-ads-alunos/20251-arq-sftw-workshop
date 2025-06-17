package br.ifba.ads.workshop.api.persistence.repositories;

import br.ifba.ads.workshop.api.persistence.entities.AccessLevelEntity;
import br.ifba.ads.workshop.core.domain.models.enums.AccessLevel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface JpaAccessLevelRepository extends JpaRepository<AccessLevelEntity, UUID> {
    Optional<AccessLevelEntity> findByLevelAndDeletedIsFalse(AccessLevel level);
}
