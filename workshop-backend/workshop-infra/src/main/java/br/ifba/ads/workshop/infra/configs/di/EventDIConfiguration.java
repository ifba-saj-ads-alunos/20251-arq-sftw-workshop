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
}

