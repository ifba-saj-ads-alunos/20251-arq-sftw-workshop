package br.ifba.ads.workshop.infra.mappers;

import br.ifba.ads.workshop.core.domain.models.BaseModel;
import br.ifba.ads.workshop.infra.persistence.entities.BaseEntity;

public interface BaseEntityMapper<T extends BaseModel,  E extends BaseEntity>{
    T toDomain(E entity);
    E toEntity(T domain);
}
