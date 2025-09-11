package br.ifba.ads.workshop.core.domain.repositories.contracts;

import br.ifba.ads.workshop.core.application.dtos.EventDTO;
import br.ifba.ads.workshop.core.domain.models.Event;

public class RegisterEventUseCase {
    private final EventRepository eventRepository;

    public RegisterEventUseCase(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public Event execute(EventDTO eventDto) {
        Event event = new Event(
            eventDto.getDate(),
            eventDto.getDescription(),
            eventDto.getLocation(),
            eventDto.getName()
        );
        return eventRepository.save(event);
    }
}