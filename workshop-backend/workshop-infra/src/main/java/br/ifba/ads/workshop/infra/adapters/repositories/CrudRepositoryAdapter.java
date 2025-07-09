package br.ifba.ads.workshop.infra.adapters.repositories;

import br.ifba.ads.workshop.core.domain.exception.InternalServerException;
import br.ifba.ads.workshop.core.domain.models.AuditableModel;
import br.ifba.ads.workshop.core.domain.repositories.contracts.CrudRepository;
import br.ifba.ads.workshop.infra.persistence.entities.BaseEntity;
import br.ifba.ads.workshop.infra.mappers.BaseEntityMapper;
import br.ifba.ads.workshop.infra.persistence.repositories.JpaBaseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@NoRepositoryBean
public abstract class CrudRepositoryAdapter<T extends AuditableModel, E extends BaseEntity>
        implements CrudRepository<T> {

    private final JpaBaseRepository<E> repository;
    private final BaseEntityMapper<T, E> mapper;

    @Override
    public Boolean deleteById(UUID id) {
        Optional<T> model = findById(id);
        if (model.isPresent()) {
            T modelToDelete = model.get();
            modelToDelete.markAsDeleted();
            save(modelToDelete);
            return true;
        }
        return false;
    }

    @Override
    public Boolean delete(T model) {
        if (model == null) {
            return false;
        }
        try {
            model.markAsDeleted();
            save(model);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Optional<T> findById(UUID id) {
        if (id == null) {
            return Optional.empty();
        }

        return repository.findByIdAndDeletedIsFalse(id)
                .map(mapper::toDomain);
    }

    @Override
    public List<T> findAll() {
        return repository.findAll()
                .stream()
                .filter(entity -> !entity.isDeleted())
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Boolean existsById(UUID id) {
        if (id == null) {
            return false;
        }
        return repository.findByIdAndDeletedIsFalse(id).isPresent();
    }

    @Override
    public T save(T model) {
        if (model == null) {
            throw new InternalServerException("Model cannot be null");
        }

        model.markAsUpdated();

        E entity = mapper.toEntity(model);
        E savedEntity = repository.save(entity);
        return mapper.toDomain(savedEntity);
    }

    @Override
    public void saveSafely(T model)  {
        try {
            save(model);
        } catch (Exception e) {
            throw new InternalServerException("Erro ao salvar modelo no banco: " + e.getMessage(), e);
        }
    }
}