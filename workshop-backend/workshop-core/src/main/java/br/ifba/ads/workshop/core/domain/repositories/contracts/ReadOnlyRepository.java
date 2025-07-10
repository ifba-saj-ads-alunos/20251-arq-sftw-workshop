package br.ifba.ads.workshop.core.domain.repositories.contracts;

import br.ifba.ads.workshop.core.domain.models.BaseModel;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ReadOnlyRepository<T extends BaseModel>{
    Optional<T> findById(UUID id);
    List<T> findAll();
    Boolean existsById(UUID id);
}
