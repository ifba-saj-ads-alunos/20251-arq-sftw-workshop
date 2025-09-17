package br.ifba.ads.workshop.core.application.usecases;

import br.ifba.ads.workshop.core.application.dtos.ApproveEventCommand;
import br.ifba.ads.workshop.core.application.dtos.EventOutput;

public interface ApproveEventUseCase {
    EventOutput execute(ApproveEventCommand command);
}
