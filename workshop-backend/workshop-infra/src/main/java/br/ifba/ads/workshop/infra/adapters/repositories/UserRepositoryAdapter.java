package br.ifba.ads.workshop.infra.adapters.repositories;

import br.ifba.ads.workshop.infra.persistence.entities.user.UserEntity;
import br.ifba.ads.workshop.infra.mappers.UserEntityMapper;
import br.ifba.ads.workshop.infra.persistence.repositories.JpaUserRepository;
import br.ifba.ads.workshop.core.domain.repositories.UserRepository;
import br.ifba.ads.workshop.core.domain.models.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserRepositoryAdapter extends CrudRepositoryAdapter<User, UserEntity> implements UserRepository {

    private final JpaUserRepository repository;
    private final UserEntityMapper mapper;

    public UserRepositoryAdapter(
            UserEntityMapper userMapper,
            JpaUserRepository repository
    ) {
        super(repository, userMapper);
        this.mapper = userMapper;
        this.repository = repository;
    }

    @Override
    public Optional<User> findByEmail(String email) {
        var userEntity = repository.findByEmailAndDeletedIsFalse(email);
        return userEntity.map(mapper::toDomain);
    }

    @Override
    public Optional<User> findByCpf(String cpf) {
        var userEntity = repository.findByCpfAndDeletedIsFalse(cpf);
        return userEntity.map(mapper::toDomain);
    }
}
