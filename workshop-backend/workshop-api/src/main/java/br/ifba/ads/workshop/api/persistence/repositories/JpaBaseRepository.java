package br.ifba.ads.workshop.api.persistence.repositories;

import br.ifba.ads.workshop.api.persistence.entities.BaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.ZonedDateTime;
import java.util.Optional;


public interface JpaBaseRepository<T extends BaseEntity, ID> extends JpaRepository<T, ID> {

   Optional<T> findByIdAndDeletedIsFalse(ID id);

    default void softDelete(T entity) {
        entity.setDateUpdate(ZonedDateTime.now());
        entity.setDeleted(true);
        this.save(entity);
    }

    default void softDeleteById(ID id) {
        Optional<T> entity = this.findByIdAndDeletedIsFalse(id);
        entity.ifPresent(this::softDelete);
    }
}
