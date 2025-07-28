package br.ifba.ads.workshop.web.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.ifba.ads.workshop.core.application.dtos.EventDTO;
import br.ifba.ads.workshop.core.domain.models.Event;
import br.ifba.ads.workshop.core.domain.service.Autowired;
import br.ifba.ads.workshop.core.domain.service.EventService;

@RestController 
public class EventController {
    @Autowired
    private EventService eventService;

    @PostMapping
    public ResponseEntity<Event> registerEvent(@RequestBody EventDTO eventDTO)
    {
        Event event = eventService.register(eventDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(event);
    }
}
