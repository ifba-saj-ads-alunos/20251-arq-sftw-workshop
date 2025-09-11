package br.ifba.ads.workshop.infra.adapters.repositories;

import br.ifba.ads.workshop.core.domain.models.Event;
import br.ifba.ads.workshop.core.domain.repositories.contracts.EventRepository;
import br.ifba.ads.workshop.infra.mappers.EventEntityMapper;
import br.ifba.ads.workshop.infra.persistence.entities.event.EventEntity;
import br.ifba.ads.workshop.infra.persistence.repositories.JpaEventRepository;
import org.springframework.stereotype.Repository;

@Repository
public final class EventRepositoryAdapter extends CrudRepositoryAdapter<Event, EventEntity> implements EventRepository {

    public EventRepositoryAdapter(
            JpaEventRepository repository,
            EventEntityMapper mapper
    ) {
        super(repository, mapper);
    }
}

