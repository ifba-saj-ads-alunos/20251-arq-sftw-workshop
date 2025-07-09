package br.ifba.ads.workshop.infra.persistence.repositories;

import br.ifba.ads.workshop.infra.persistence.entities.BaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Optional;
import java.util.UUID;

@NoRepositoryBean
public interface JpaBaseRepository<T extends BaseEntity> extends JpaRepository<T, UUID> {

   Optional<T> findByIdAndDeletedIsFalse(UUID id);
}
