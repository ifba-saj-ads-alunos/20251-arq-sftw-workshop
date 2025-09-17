package br.ifba.ads.workshop.core.application.usecases;

import br.ifba.ads.workshop.core.application.dtos.ApproveEventCommand;
import br.ifba.ads.workshop.core.application.dtos.EventOutput;
import br.ifba.ads.workshop.core.domain.models.Event;
import br.ifba.ads.workshop.core.domain.repositories.contracts.EventRepository;

public final class ApproveEventUseCaseImpl implements ApproveEventUseCase {

    private final EventRepository eventRepository;
    private final br.ifba.ads.workshop.core.application.gateways.NotificationGateway notificationGateway;

    public ApproveEventUseCaseImpl(EventRepository eventRepository, br.ifba.ads.workshop.core.application.gateways.NotificationGateway notificationGateway) {
        this.eventRepository = eventRepository;
        this.notificationGateway = notificationGateway;
    }

    @Override
    public EventOutput execute(ApproveEventCommand command) {
        var opt = eventRepository.findById(command.eventId());
        if (opt.isEmpty()) throw new IllegalArgumentException("Event not found");
        Event e = opt.get();
        e.approve();
        var saved = eventRepository.save(e);
        if (saved.getOrganizerId() != null) {
            notificationGateway.sendNotification(saved.getOrganizerId(), "Evento aprovado", "Seu evento '" + saved.getTitle() + "' foi aprovado.");
        }
        return EventOutput.fromDomain(saved);
    }
}
