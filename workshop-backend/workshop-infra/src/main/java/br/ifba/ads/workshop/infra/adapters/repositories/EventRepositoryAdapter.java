package br.ifba.ads.workshop.infra.adapters.repositories;

import org.springframework.stereotype.Repository;

import br.ifba.ads.workshop.core.domain.models.Event;
import br.ifba.ads.workshop.core.domain.repositories.contracts.EventRepository;
import br.ifba.ads.workshop.infra.mappers.EventEntityMapper;
import br.ifba.ads.workshop.infra.persistence.entities.event.EventEntity;
import br.ifba.ads.workshop.infra.persistence.repositories.JpaEventRepository;

@Repository
public class EventRepositoryAdapter extends CrudRepositoryAdapter<Event, EventEntity> implements EventRepository {

    public EventRepositoryAdapter(
            JpaEventRepository repository,
            EventEntityMapper mapper
    ) {
        super(repository, mapper);
    }

    @Override
    public java.util.List<Event> findByStatus(String status) {
        var list = ((JpaEventRepository) this.getJpaRepository()).findByStatusOrderByStartsAtDesc(status);
        return list.stream().map(this.getMapper()::toDomain).collect(java.util.stream.Collectors.toList());
    }
}

