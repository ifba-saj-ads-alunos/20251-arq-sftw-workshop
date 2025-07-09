package br.ifba.ads.workshop.infra.adapters.repositories;

import br.ifba.ads.workshop.core.domain.models.UserRole;
import br.ifba.ads.workshop.core.domain.models.enums.UserRoleType;
import br.ifba.ads.workshop.core.domain.repositories.UserRoleRepository;
import br.ifba.ads.workshop.infra.mappers.UserRoleEntityMapper;
import br.ifba.ads.workshop.infra.persistence.entities.user.UserRoleEntity;
import br.ifba.ads.workshop.infra.persistence.repositories.JpaUserRoleRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserRoleRepositoryAdapter extends ReadOnlyRepositoryAdapter<UserRole, UserRoleEntity>
    implements UserRoleRepository
{

    private final JpaUserRoleRepository repository;
    private final UserRoleEntityMapper mapper;


    public UserRoleRepositoryAdapter(
            JpaUserRoleRepository repository,
            UserRoleEntityMapper mapper) {
        super(repository, mapper);
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Optional<UserRole> findByType(UserRoleType type) {
        var userRoleEntity = repository.findByTypeAndDeletedIsFalse(type);
        return userRoleEntity.map(mapper::toDomain);
    }
}
