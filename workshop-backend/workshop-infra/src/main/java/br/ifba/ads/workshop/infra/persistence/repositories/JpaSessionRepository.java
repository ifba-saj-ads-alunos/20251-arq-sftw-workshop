package br.ifba.ads.workshop.infra.persistence.repositories;

import br.ifba.ads.workshop.infra.persistence.entities.SessionsEntity;
import java.util.Optional;

public interface JpaSessionRepository extends JpaBaseRepository<SessionsEntity> {
    Optional<SessionsEntity> findByToken (String token);
}
