package br.ifba.ads.workshop.api.persistence.repositories;

import br.ifba.ads.workshop.api.persistence.entities.user.UserEntity;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface JpaUserRepository extends JpaBaseRepository<UserEntity, UUID> {
    Optional<UserEntity> findByEmailAndDeletedIsFalse(String email);
}
