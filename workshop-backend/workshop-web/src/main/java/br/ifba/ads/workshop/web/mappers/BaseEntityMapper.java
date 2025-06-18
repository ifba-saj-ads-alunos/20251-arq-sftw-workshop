package br.ifba.ads.workshop.web.mappers;

import br.ifba.ads.workshop.infra.persistence.entities.BaseEntity;
import br.ifba.ads.workshop.core.domain.models.ModelWithIdentifier;

public interface BaseEntityMapper<T extends ModelWithIdentifier,  E extends BaseEntity>{
    T toDomain(E entity);
    E toEntity(T domain);
}
