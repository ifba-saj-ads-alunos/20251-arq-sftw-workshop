package br.ifba.ads.workshop.core.application.usecases.impl;

import br.ifba.ads.workshop.core.application.dtos.EventOutput;
import br.ifba.ads.workshop.core.application.usecases.FindEventsByStatusUseCase;
import br.ifba.ads.workshop.core.domain.models.Event;
import br.ifba.ads.workshop.core.domain.repositories.contracts.EventRepository;

import java.util.List;
import java.util.stream.Collectors;

public class FindEventsByStatusUseCaseImpl implements FindEventsByStatusUseCase {

    private final EventRepository eventRepository;

    public FindEventsByStatusUseCaseImpl(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Override
    public java.util.List<EventOutput> execute(String status) {
        var domains = eventRepository.findByStatus(status);
        return domains.stream().map(EventOutput::fromDomain).collect(Collectors.toList());
    }
}
