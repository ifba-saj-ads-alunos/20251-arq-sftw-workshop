package br.ifba.ads.workshop.infra.mappers;

import br.ifba.ads.workshop.core.domain.models.ModelWithIdentifier;
import br.ifba.ads.workshop.infra.persistence.entities.BaseEntity;
import org.springframework.data.domain.Page;

public interface BaseEntityMapper<T extends ModelWithIdentifier,  E extends BaseEntity>{
    T toDomain(E entity);
    E toEntity(T domain);
    Page
}
