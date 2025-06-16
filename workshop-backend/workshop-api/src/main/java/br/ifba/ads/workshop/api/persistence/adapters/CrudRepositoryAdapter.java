package br.ifba.ads.workshop.api.persistence.adapters;
import br.ifba.ads.workshop.api.persistence.entities.BaseEntity;
import br.ifba.ads.workshop.api.persistence.mappers.BasePersistenteMapper;
import br.ifba.ads.workshop.api.persistence.repositories.JpaBaseRepository;
import br.ifba.ads.workshop.core.application.ports.out.repositories.CrudRepositoryPort;
import br.ifba.ads.workshop.core.domain.models.ModelWithIdentifier;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;


@Component
@RequiredArgsConstructor
public abstract class CrudRepositoryAdapter<T extends ModelWithIdentifier, ID, E extends BaseEntity>
        implements CrudRepositoryPort<T, ID>
{
    private final JpaBaseRepository<E, ID> jpaBaseRepository;
    private final BasePersistenteMapper <T , E> mapper;

    @Override
    public T save(T entity) {
        var entityToSave = mapper.toEntity(entity);
        if(entity.isNew()){
            entityToSave.setDateCreate(ZonedDateTime.now());
            entityToSave.setDeleted(false);
        }
        entityToSave.setDateUpdate(ZonedDateTime.now());
        var savedEntity = jpaBaseRepository.save(entityToSave);
        return mapper.toDomain(savedEntity);
    }

    @Override
    public Optional<T> findById(ID id) {
        return jpaBaseRepository.findByIdAndDeletedIsFalse(id)
                .map(mapper::toDomain);
    }

    @Override
    public List<T> findAll() {
        return jpaBaseRepository.findAll()
                .stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public void deleteById(ID id) {
        jpaBaseRepository.softDeleteById(id);
    }

    @Override
    public boolean existsById(ID id) {
        return jpaBaseRepository.existsById(id);
    }
}
