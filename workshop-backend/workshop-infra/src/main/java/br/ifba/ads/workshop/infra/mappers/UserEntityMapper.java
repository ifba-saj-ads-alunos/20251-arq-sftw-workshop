package br.ifba.ads.workshop.infra.mappers;

import org.springframework.stereotype.Component;

import br.ifba.ads.workshop.core.domain.models.User;
import br.ifba.ads.workshop.core.domain.models.valueObjects.Email;
import br.ifba.ads.workshop.core.domain.models.valueObjects.EncryptedPassword;
import br.ifba.ads.workshop.infra.persistence.entities.user.UserEntity;
import lombok.RequiredArgsConstructor;

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
                entity.getCpf(),
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
                .cpf(domain.getCpf())
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
