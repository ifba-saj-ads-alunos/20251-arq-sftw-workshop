package br.ifba.ads.workshop.core.domain.repositories.contracts;

import br.ifba.ads.workshop.core.application.dtos.CreateEventCommand;
import br.ifba.ads.workshop.core.domain.models.Event;

public class RegisterEventUseCase {
    private final EventRepository eventRepository;

    public RegisterEventUseCase(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public Event execute(CreateEventCommand command) {
        Event event = new Event(
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
        return eventRepository.save(event);
    }
}