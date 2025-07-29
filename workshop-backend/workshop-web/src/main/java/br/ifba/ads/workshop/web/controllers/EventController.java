package br.ifba.ads.workshop.web.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.ifba.ads.workshop.core.application.dtos.EventDTO;
import br.ifba.ads.workshop.core.domain.models.Event;
import br.ifba.ads.workshop.core.domain.repositories.contracts.RegisterEventUseCase;
import br.ifba.ads.workshop.core.domain.service.Autowired;

@RestController
@RequestMapping("/api/v1/events")
public class EventController {
    private final RegisterEventUseCase registerEventUseCase;

    @Autowired
    public EventController(RegisterEventUseCase registerEventUseCase) {
        this.registerEventUseCase = registerEventUseCase;
    }

    @PostMapping
    public ResponseEntity<Event> registerEvent(@RequestBody EventDTO eventDTO) {
        Event event = registerEventUseCase.execute(eventDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(event);
    }
}
