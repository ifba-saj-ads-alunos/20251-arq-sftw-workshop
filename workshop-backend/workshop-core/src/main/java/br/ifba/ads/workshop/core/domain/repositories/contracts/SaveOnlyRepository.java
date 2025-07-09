package br.ifba.ads.workshop.core.domain.repositories.contracts;

import br.ifba.ads.workshop.core.domain.models.AuditableModel;

public interface SaveOnlyRepository<T extends AuditableModel>{
    T save(T model);
    void saveSafely(T model);
}
