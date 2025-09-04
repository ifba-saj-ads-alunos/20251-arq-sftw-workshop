package br.ifba.ads.workshop.web.controllers;

import br.ifba.ads.workshop.core.application.dtos.CreateEventCommand;
import br.ifba.ads.workshop.core.application.dtos.EventOutput;
import br.ifba.ads.workshop.core.application.usecases.CreateEventUseCase;
import br.ifba.ads.workshop.core.domain.models.enums.EventModality;
import br.ifba.ads.workshop.web.configs.TokenMainInfo;
import br.ifba.ads.workshop.web.dtos.CreateEventRequestDto;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

@RestController
@RequestMapping("api/v1/events")
public class EventController extends BaseController {

    private final CreateEventUseCase createEventUseCase;

    public EventController(CreateEventUseCase createEventUseCase) {
        this.createEventUseCase = createEventUseCase;
    }

    @PostMapping
    public ResponseEntity<EventOutput> create(@Valid @RequestBody CreateEventRequestDto dto,
                                              @AuthenticationPrincipal TokenMainInfo principal) {
        verifyIfUserIsAdmin(principal);
        final var command = new CreateEventCommand(
                dto.titulo(),
                dto.descricao(),
                toStartZdt(dto.dataInicio()),
                toEndZdt(dto.dataFim()),
                parseVacancies(dto.vagas()),
                mapModality(dto.localidade()),
                dto.sala(),
                dto.link(),
                dto.categoria()
        );
        final var created = createEventUseCase.execute(command);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    private Integer parseVacancies(String vagas) {
        try {
            return (vagas == null || vagas.isBlank()) ? null : Integer.parseInt(vagas);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private EventModality mapModality(String localidade) {
        if (localidade == null) return EventModality.PRESENCIAL;
        return switch (localidade.toLowerCase()) {
            case "remota" -> EventModality.ONLINE;
            case "presencial" -> EventModality.PRESENCIAL;
            default -> EventModality.PRESENCIAL;
        };
    }

    private ZonedDateTime toStartZdt(String yyyyMMdd) {
        final var d = LocalDate.parse(yyyyMMdd);
        return d.atStartOfDay(ZoneOffset.UTC);
    }

    private ZonedDateTime toEndZdt(String yyyyMMdd) {
        try {
            final var d = LocalDate.parse(yyyyMMdd);
            return d.atTime(23, 59, 59).atZone(ZoneOffset.UTC);
        } catch (java.time.format.DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid date format for 'dataFim': '" + yyyyMMdd + "'. Expected format: yyyy-MM-dd.", e);
        }
    }
}
