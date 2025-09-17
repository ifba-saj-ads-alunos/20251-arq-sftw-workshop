package br.ifba.ads.workshop.core.domain.repositories.contracts;

import br.ifba.ads.workshop.core.domain.models.Event;

import java.util.List;

public interface EventRepository extends CrudRepository<Event> {
	java.util.List<Event> findByStatus(String status);
}
