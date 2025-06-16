package br.ifba.ads.workshop.api.persistence.adapters;

import br.ifba.ads.workshop.api.persistence.entities.user.UserEntity;
import br.ifba.ads.workshop.api.persistence.mappers.UserPersistenteMapper;
import br.ifba.ads.workshop.api.persistence.repositories.JpaUserRepository;
import br.ifba.ads.workshop.core.application.ports.out.repositories.UserRepositoryPort;
import br.ifba.ads.workshop.core.domain.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class UserRepositoryAdapter extends CrudRepositoryAdapter<User, UUID, UserEntity> implements UserRepositoryPort {

    private final JpaUserRepository repository;
    private final UserPersistenteMapper mapper;

    public UserRepositoryAdapter(
            @Autowired UserPersistenteMapper userMapper,
            @Autowired JpaUserRepository repository, UserPersistenteMapper mapper
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
