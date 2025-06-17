package br.ifba.ads.workshop.api.mappers;

import br.ifba.ads.workshop.api.persistence.entities.BaseEntity;
import br.ifba.ads.workshop.core.domain.models.ModelWithIdentifier;

public interface BaseEntityMapper<T extends ModelWithIdentifier,  E extends BaseEntity>{
    T toDomain(E entity);
    E toEntity(T domain);
}
