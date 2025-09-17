package br.ifba.ads.workshop.infra.configs.di;

import br.ifba.ads.workshop.core.application.usecases.CreateEventUseCase;
import br.ifba.ads.workshop.core.application.usecases.CreateEventUseCaseImpl;
import br.ifba.ads.workshop.core.domain.repositories.contracts.EventRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EventDIConfiguration {

    @Bean
    public CreateEventUseCase createEventUseCase(EventRepository eventRepository) {
        return new CreateEventUseCaseImpl(eventRepository);
    }

    @Bean
    public br.ifba.ads.workshop.core.application.usecases.ApproveEventUseCase approveEventUseCase(EventRepository eventRepository, br.ifba.ads.workshop.core.application.gateways.NotificationGateway notificationGateway) {
        return new br.ifba.ads.workshop.core.application.usecases.ApproveEventUseCaseImpl(eventRepository, notificationGateway);
    }

    @Bean
    public br.ifba.ads.workshop.core.application.usecases.RejectEventUseCase rejectEventUseCase(EventRepository eventRepository, br.ifba.ads.workshop.core.application.gateways.NotificationGateway notificationGateway) {
        return new br.ifba.ads.workshop.core.application.usecases.RejectEventUseCaseImpl(eventRepository, notificationGateway);
    }

    @Bean
    public br.ifba.ads.workshop.core.application.usecases.FindEventsByStatusUseCase findEventsByStatusUseCase(EventRepository eventRepository) {
        return new br.ifba.ads.workshop.core.application.usecases.impl.FindEventsByStatusUseCaseImpl(eventRepository);
    }

    @Bean
    public br.ifba.ads.workshop.core.application.gateways.NotificationGateway notificationGateway(br.ifba.ads.workshop.infra.persistence.repositories.JpaNotificationRepository repo) {
        return new br.ifba.ads.workshop.infra.adapters.notifications.NotificationGatewayAdapter(repo);
    }

    @Bean
    public br.ifba.ads.workshop.core.domain.repositories.contracts.EventSessionRepository eventSessionRepository(br.ifba.ads.workshop.infra.persistence.repositories.JpaEventSessionRepository repo,
                                                                                                                    br.ifba.ads.workshop.infra.mappers.EventSessionEntityMapper mapper) {
        return new br.ifba.ads.workshop.infra.adapters.repositories.EventSessionRepositoryAdapter(repo, mapper);
    }
}

