package br.ifba.ads.workshop.infra.mappers;

import br.ifba.ads.workshop.core.domain.models.valueObjects.Email;
import br.ifba.ads.workshop.core.domain.models.valueObjects.EncryptedPassword;
import br.ifba.ads.workshop.infra.persistence.entities.user.UserEntity;
import br.ifba.ads.workshop.core.domain.models.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public final class UserEntityMapper implements BaseEntityMapper<User, UserEntity> {

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
                new EncryptedPassword(entity.getPassword()),
                entity.getLastAccess()
        );
    }

    @Override
    public UserEntity toEntity(User domain){
        return UserEntity.builder()
                .id(domain.getId())
                .name(domain.getName())
                .email(domain.getEmail().value())
                .userRole(userRoleEntityMapper.toEntity(domain.getUserRole()))
                .accessLevel(accessLevelEntityMapper.toEntity(domain.getAccessLevel()))
                .password(domain.getPassword().value())
                .lastAccess(domain.getLastAccess())
                .createdAt(domain.getCreatedAt())
                .updatedAt(domain.getUpdatedAt())
                .deleted(domain.getDeleted())
                .build();
    }


}
