package br.ifba.ads.workshop.core.domain.repositories.contracts;

import br.ifba.ads.workshop.core.domain.models.Event;

public interface EventRepository{
    Event save (Event event);
}
