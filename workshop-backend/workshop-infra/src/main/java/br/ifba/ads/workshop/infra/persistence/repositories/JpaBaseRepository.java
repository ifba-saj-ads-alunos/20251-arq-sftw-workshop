package br.ifba.ads.workshop.infra.persistence.repositories;

import br.ifba.ads.workshop.infra.persistence.entities.BaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.time.ZonedDateTime;
import java.util.Optional;

@NoRepositoryBean
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
