package br.ifba.ads.workshop.core.application.usecases;

import br.ifba.ads.workshop.core.application.dtos.CreateEventCommand;
import br.ifba.ads.workshop.core.application.dtos.EventOutput;
import br.ifba.ads.workshop.core.domain.models.Event;
import br.ifba.ads.workshop.core.domain.repositories.contracts.EventRepository;

public final class CreateEventUseCaseImpl implements CreateEventUseCase {

    private final EventRepository eventRepository;

    public CreateEventUseCaseImpl(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Override
    public EventOutput execute(CreateEventCommand command) {
        final var event = new Event(
                command.title(),
                command.description(),
                command.startsAt(),
                command.endsAt(),
                command.vacancies(),
                command.modality(),
                command.location(),
                command.remoteLink(),
                command.category()
        );

        var saved = eventRepository.save(event);
        return EventOutput.fromDomain(saved);
    }
}

