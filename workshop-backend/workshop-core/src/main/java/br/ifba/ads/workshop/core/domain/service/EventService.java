package br.ifba.ads.workshop.core.domain.service;

import br.ifba.ads.workshop.core.application.dtos.EventDTO;
import br.ifba.ads.workshop.core.domain.models.Event;
import br.ifba.ads.workshop.core.domain.repositories.contracts.EventRepository;

@Service
public class EventService {
    @Autowired
    private EventRepository eventRepository;
    public Event register(EventDTO eventDto)
    {
        Event event = new Event();
        event.setName(eventDto.getName());
        event.setDescription(eventDto.getDescription());
        event.setDate(eventDto.getDate());
        event.setLocation(eventDto.getLocation());
        return eventRepository.save(event);
    }
}
