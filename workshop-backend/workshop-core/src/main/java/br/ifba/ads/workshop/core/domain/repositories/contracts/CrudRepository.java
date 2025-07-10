package br.ifba.ads.workshop.core.domain.repositories.contracts;

import br.ifba.ads.workshop.core.domain.models.AuditableModel;

import java.util.UUID;

public interface CrudRepository<T extends AuditableModel> extends SaveOnlyRepository<T>, ReadOnlyRepository<T> {
    Boolean deleteById(UUID id);
    Boolean delete(T model);
}
