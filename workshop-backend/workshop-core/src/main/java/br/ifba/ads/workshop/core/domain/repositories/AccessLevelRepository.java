package br.ifba.ads.workshop.core.domain.repositories;

import br.ifba.ads.workshop.core.domain.models.AccessLevel;
import br.ifba.ads.workshop.core.domain.models.enums.AccessLevelType;
import br.ifba.ads.workshop.core.domain.repositories.contracts.ReadOnlyRepository;

import java.util.Optional;

public interface AccessLevelRepository extends ReadOnlyRepository<AccessLevel> {
    Optional<AccessLevel> findByType(AccessLevelType type);
}
