package br.ifba.ads.workshop.core.application.usecases;

import br.ifba.ads.workshop.core.application.dtos.RejectEventCommand;
import br.ifba.ads.workshop.core.application.dtos.EventOutput;

public interface RejectEventUseCase {
    EventOutput execute(RejectEventCommand command);
}
