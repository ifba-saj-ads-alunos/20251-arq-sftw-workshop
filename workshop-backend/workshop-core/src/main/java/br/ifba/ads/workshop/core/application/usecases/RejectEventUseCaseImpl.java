package br.ifba.ads.workshop.core.application.usecases;

import br.ifba.ads.workshop.core.application.dtos.RejectEventCommand;
import br.ifba.ads.workshop.core.application.dtos.EventOutput;
import br.ifba.ads.workshop.core.domain.models.Event;
import br.ifba.ads.workshop.core.domain.repositories.contracts.EventRepository;

public final class RejectEventUseCaseImpl implements RejectEventUseCase {

    private final EventRepository eventRepository;
    private final br.ifba.ads.workshop.core.application.gateways.NotificationGateway notificationGateway;

    public RejectEventUseCaseImpl(EventRepository eventRepository, br.ifba.ads.workshop.core.application.gateways.NotificationGateway notificationGateway) {
        this.eventRepository = eventRepository;
        this.notificationGateway = notificationGateway;
    }

    @Override
    public EventOutput execute(RejectEventCommand command) {
        var opt = eventRepository.findById(command.eventId());
        if (opt.isEmpty()) throw new IllegalArgumentException("Event not found");
        Event e = opt.get();
        e.reject(command.justification());
        var saved = eventRepository.save(e);
        if (saved.getOrganizerId() != null) {
            notificationGateway.sendNotification(saved.getOrganizerId(), "Evento reprovado", "Seu evento '" + saved.getTitle() + "' foi reprovado. Justificativa: " + command.justification());
        }
        return EventOutput.fromDomain(saved);
    }
}
