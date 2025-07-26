package br.ifba.ads.workshop.infra.mappers;

import br.ifba.ads.workshop.core.domain.models.AccessLevel;
import br.ifba.ads.workshop.infra.persistence.entities.AccessLevelEntity;
import org.springframework.stereotype.Component;

@Component
public final class AccessLevelEntityMapper implements BaseEntityMapper<AccessLevel, AccessLevelEntity>{

    @Override
    public AccessLevel toDomain(AccessLevelEntity entity){
        return new AccessLevel(
                entity.getId(),
                entity.getType()
        );
    }

    @Override
    public AccessLevelEntity toEntity(AccessLevel domain){
       return new AccessLevelEntity(
                domain.getId(),
                domain.getType()
       );
    }

}
