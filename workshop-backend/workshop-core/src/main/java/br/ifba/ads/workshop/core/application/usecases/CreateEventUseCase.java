package br.ifba.ads.workshop.core.application.usecases;

import br.ifba.ads.workshop.core.application.dtos.CreateEventCommand;
import br.ifba.ads.workshop.core.application.dtos.EventOutput;

public interface CreateEventUseCase {
    EventOutput execute(CreateEventCommand command);
}

