package br.ifba.ads.workshop.infra.mappers;

import br.ifba.ads.workshop.core.domain.models.valueObjects.Email;
import br.ifba.ads.workshop.core.domain.models.valueObjects.EncryptedPassword;
import br.ifba.ads.workshop.infra.persistence.entities.user.UserEntity;
import br.ifba.ads.workshop.core.domain.models.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserEntityMapper implements BaseEntityMapper<User, UserEntity> {

    private final AccessLevelEntityMapper  accessLevelEntityMapper;
    private final UserRoleEntityMapper  userRoleEntityMapper;

    @Override
    public User toDomain(UserEntity entity) {
        return new User(
                entity.getId(),
                entity.getCreatedAt(),
                entity.getUpdatedAt(),
                entity.isDeleted(),
                entity.getName(),
                new Email(entity.getEmail()),
                userRoleEntityMapper.toDomain(entity.getUserRole()),
                accessLevelEntityMapper.toDomain(entity.getAccessLevel()),
                new EncryptedPassword(entity.getPassword())
        );
    }

    @Override
    public UserEntity toEntity(User domain){
        var userEntity = new UserEntity(
                domain.getName(),
                domain.getEmail().value(),
                userRoleEntityMapper.toEntity(domain.getUserRole()),
                accessLevelEntityMapper.toEntity(domain.getAccessLevel()),
                domain.getPassword().value()
        );
        userEntity.setId(domain.getId());
        return userEntity;
    }


}
