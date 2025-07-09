package br.ifba.ads.workshop.core.domain.repositories;

import br.ifba.ads.workshop.core.domain.models.User;
import br.ifba.ads.workshop.core.domain.repositories.contracts.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User> {
    Optional<User> findByEmail(String email);
}
