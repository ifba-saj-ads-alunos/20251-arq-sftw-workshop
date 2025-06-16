package br.ifba.ads.workshop.api.persistence.mappers;

import br.ifba.ads.workshop.api.persistence.entities.user.UserEntity;
import br.ifba.ads.workshop.core.domain.models.User;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface UserPersistenteMapper extends BasePersistenteMapper<User, UserEntity> {
    UserEntity toEntity(User user);
    User toDomain(UserEntity userEntity);
}
