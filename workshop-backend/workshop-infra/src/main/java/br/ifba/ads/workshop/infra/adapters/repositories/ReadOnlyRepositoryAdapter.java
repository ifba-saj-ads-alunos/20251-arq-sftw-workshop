package br.ifba.ads.workshop.infra.adapters.repositories;

import br.ifba.ads.workshop.core.domain.models.BaseModel;
import br.ifba.ads.workshop.core.domain.repositories.contracts.ReadOnlyRepository;
import br.ifba.ads.workshop.infra.mappers.BaseEntityMapper;
import br.ifba.ads.workshop.infra.persistence.entities.BaseEntity;
import br.ifba.ads.workshop.infra.persistence.repositories.JpaBaseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@NoRepositoryBean
public abstract class ReadOnlyRepositoryAdapter <T extends BaseModel, E extends BaseEntity>
    implements ReadOnlyRepository<T> {

    private final JpaBaseRepository<E> repository;
    private final BaseEntityMapper<T, E> mapper;

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
}
