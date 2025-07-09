package br.ifba.ads.workshop.infra.adapters.repositories;

import br.ifba.ads.workshop.core.domain.models.AccessLevel;
import br.ifba.ads.workshop.core.domain.models.enums.AccessLevelType;
import br.ifba.ads.workshop.core.domain.repositories.AccessLevelRepository;
import br.ifba.ads.workshop.infra.mappers.AccessLevelEntityMapper;
import br.ifba.ads.workshop.infra.persistence.entities.AccessLevelEntity;
import br.ifba.ads.workshop.infra.persistence.repositories.JpaAccessLevelRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class AccessLevelRepositoryAdapter extends ReadOnlyRepositoryAdapter<AccessLevel, AccessLevelEntity>
        implements AccessLevelRepository {

    private final JpaAccessLevelRepository repository;
    private final AccessLevelEntityMapper mapper;


    public AccessLevelRepositoryAdapter(
            JpaAccessLevelRepository repository,
            AccessLevelEntityMapper mapper)
    {
        super(repository, mapper);
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Optional<AccessLevel> findByType(AccessLevelType type) {
        var accessLevelEntity = repository.findByTypeAndDeletedIsFalse(type);
        return accessLevelEntity.map(mapper::toDomain);
    }

}
