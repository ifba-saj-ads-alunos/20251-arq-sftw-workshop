package br.ifba.ads.workshop.api.persistence.adapters;

import br.ifba.ads.workshop.api.persistence.entities.user.UserEntity;
import br.ifba.ads.workshop.api.mappers.UserEntityMapper;
import br.ifba.ads.workshop.api.persistence.repositories.JpaUserRepository;
import br.ifba.ads.workshop.core.application.ports.out.repositories.UserRepositoryPort;
import br.ifba.ads.workshop.core.domain.models.User;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class UserRepositoryAdapter extends CrudRepositoryAdapter<User, UUID, UserEntity> implements UserRepositoryPort {

    private final JpaUserRepository repository;
    private final UserEntityMapper mapper;

    public UserRepositoryAdapter(
            UserEntityMapper userMapper,
            JpaUserRepository repository, UserEntityMapper mapper
    ) {
        super(repository, userMapper);
        this.mapper = mapper;
        this.repository = repository;
    }

    @Override
    public Optional<User> findByEmail(String email) {
        var userEntity = repository.findByEmailAndDeletedIsFalse(email);
        return userEntity.map(mapper::toDomain);
    }
}
