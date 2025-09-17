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
    private final br.ifba.ads.workshop.core.application.usecases.ApproveEventUseCase approveEventUseCase;
    private final br.ifba.ads.workshop.core.application.usecases.RejectEventUseCase rejectEventUseCase;
    private final br.ifba.ads.workshop.core.application.gateways.NotificationGateway notificationGateway;
    private final br.ifba.ads.workshop.core.application.usecases.FindEventsByStatusUseCase findEventsByStatusUseCase;
    private final br.ifba.ads.workshop.core.domain.repositories.contracts.EventSessionRepository eventSessionRepository;

    public EventController(CreateEventUseCase createEventUseCase,
                           br.ifba.ads.workshop.core.application.usecases.ApproveEventUseCase approveEventUseCase,
                           br.ifba.ads.workshop.core.application.usecases.RejectEventUseCase rejectEventUseCase,
                           br.ifba.ads.workshop.core.application.gateways.NotificationGateway notificationGateway,
                           br.ifba.ads.workshop.core.application.usecases.FindEventsByStatusUseCase findEventsByStatusUseCase,
                           br.ifba.ads.workshop.core.domain.repositories.contracts.EventSessionRepository eventSessionRepository) {
        this.createEventUseCase = createEventUseCase;
        this.approveEventUseCase = approveEventUseCase;
        this.rejectEventUseCase = rejectEventUseCase;
        this.notificationGateway = notificationGateway;
        this.findEventsByStatusUseCase = findEventsByStatusUseCase;
        this.eventSessionRepository = eventSessionRepository;
    }

    @PostMapping
    public ResponseEntity<EventOutput> create(@Valid @RequestBody CreateEventRequestDto dto,
                                              @AuthenticationPrincipal TokenMainInfo principal) {
    // Allow authenticated users to create events; the controller will set organizerId
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
        , principal == null ? null : principal.userId()
    );
        final var created = createEventUseCase.execute(command);
        // Persist sessions if any were provided
        try {
            var sessionInputs = dto.sessions();
            if (sessionInputs != null && !sessionInputs.isEmpty()) {
                var sessions = sessionInputs.stream().map(s -> new br.ifba.ads.workshop.core.domain.models.EventSession(
                        java.time.ZonedDateTime.parse(s.startsAt()),
                        java.time.ZonedDateTime.parse(s.endsAt()),
                        s.capacity(),
                        s.room()
                )).toList();
                eventSessionRepository.saveAllForEvent(created.id(), sessions);
            }
        } catch (Exception ex) {
            // do not fail event creation if sessions persistence fails; just log
            ex.printStackTrace();
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PostMapping("/{id}/approve")
    public ResponseEntity<EventOutput> approve(@PathVariable("id") java.util.UUID id,
                                               @AuthenticationPrincipal TokenMainInfo principal) {
        verifyIfUserIsAdmin(principal);
        var out = approveEventUseCase.execute(new br.ifba.ads.workshop.core.application.dtos.ApproveEventCommand(id, principal.userId()));
        return ResponseEntity.ok(out);
    }

    @PostMapping("/{id}/reject")
    public ResponseEntity<EventOutput> reject(@PathVariable("id") java.util.UUID id,
                                              @RequestBody(required = false) java.util.Map<String, String> body,
                                              @AuthenticationPrincipal TokenMainInfo principal) {
        verifyIfUserIsAdmin(principal);
        final var justification = (body == null) ? "" : body.getOrDefault("justification", "");
        var out = rejectEventUseCase.execute(new br.ifba.ads.workshop.core.application.dtos.RejectEventCommand(id, principal.userId(), justification));
        return ResponseEntity.ok(out);
    }

    @GetMapping("/notifications")
    public ResponseEntity<java.util.List<br.ifba.ads.workshop.core.application.dtos.NotificationOutput>> notifications(@AuthenticationPrincipal TokenMainInfo principal) {
        if (principal == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        var list = notificationGateway.fetchNotificationsForUser(principal.userId());
        return ResponseEntity.ok(list);
    }

    @GetMapping("/notifications/unread-count")
    public ResponseEntity<java.util.Map<String, Long>> unreadCount(@AuthenticationPrincipal TokenMainInfo principal) {
        if (principal == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        var count = notificationGateway.fetchUnreadCount(principal.userId());
        return ResponseEntity.ok(java.util.Collections.singletonMap("unread", count));
    }

    @PostMapping("/notifications/{id}/read")
    public ResponseEntity<Void> markNotificationRead(@PathVariable("id") java.util.UUID id,
                                                     @AuthenticationPrincipal TokenMainInfo principal) {
        if (principal == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        notificationGateway.markAsRead(principal.userId(), id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/notifications/mark-all-read")
    public ResponseEntity<Void> markAllRead(@AuthenticationPrincipal TokenMainInfo principal) {
        if (principal == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        notificationGateway.markAllAsRead(principal.userId());
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/pending")
    public ResponseEntity<java.util.List<EventOutput>> pending() {
        var list = findEventsByStatusUseCase.execute("DRAFT");
        return ResponseEntity.ok(list);
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
