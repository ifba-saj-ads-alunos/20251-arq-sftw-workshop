package br.ifba.ads.workshop.infra.persistence.repositories;

import br.ifba.ads.workshop.infra.persistence.entities.user.UserEntity;

import java.util.Optional;

public interface JpaUserRepository extends JpaBaseRepository<UserEntity> {
    Optional<UserEntity> findByEmailAndDeletedIsFalse(String email);
}
