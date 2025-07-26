package br.ifba.ads.workshop.core.domain.repositories;

import br.ifba.ads.workshop.core.domain.models.Session;
import br.ifba.ads.workshop.core.domain.repositories.contracts.CrudRepository;


import java.util.Optional;

public interface SessionRepository extends CrudRepository<Session> {
    Optional<Session> findByToken(String token);
}
