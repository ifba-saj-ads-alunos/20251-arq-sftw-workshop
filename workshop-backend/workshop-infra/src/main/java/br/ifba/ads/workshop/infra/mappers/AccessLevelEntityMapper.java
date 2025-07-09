package br.ifba.ads.workshop.infra.mappers;

import br.ifba.ads.workshop.core.domain.models.AccessLevel;
import br.ifba.ads.workshop.infra.persistence.entities.AccessLevelEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AccessLevelEntityMapper extends BaseEntityMapper<AccessLevel, AccessLevelEntity>{

    AccessLevel toDomain(AccessLevelEntity entity);

    @Mapping(target = "deleted",  ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    AccessLevelEntity toEntity(AccessLevel domain) ;

}
