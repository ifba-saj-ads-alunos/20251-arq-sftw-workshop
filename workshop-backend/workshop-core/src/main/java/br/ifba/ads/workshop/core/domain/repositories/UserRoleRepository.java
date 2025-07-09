package br.ifba.ads.workshop.core.domain.repositories;

import br.ifba.ads.workshop.core.domain.models.UserRole;
import br.ifba.ads.workshop.core.domain.models.enums.UserRoleType;
import br.ifba.ads.workshop.core.domain.repositories.contracts.ReadOnlyRepository;

import java.util.Optional;

public interface UserRoleRepository extends ReadOnlyRepository<UserRole> {
    Optional<UserRole> findByType(UserRoleType type);
}
