package br.ifba.ads.workshop.api.persistence.repositories;

import br.ifba.ads.workshop.api.persistence.entities.user.UserRoleEntity;
import br.ifba.ads.workshop.core.domain.models.enums.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface JpaUserRoleRepository extends JpaRepository<UserRoleEntity, UUID> {
    Optional<UserRoleEntity> findByRoleAndDeletedIsFalse(UserRole role);
}
