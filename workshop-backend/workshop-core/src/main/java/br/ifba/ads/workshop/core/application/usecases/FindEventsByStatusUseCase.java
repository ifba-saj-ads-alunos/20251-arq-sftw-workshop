package br.ifba.ads.workshop.core.application.usecases;

import br.ifba.ads.workshop.core.application.dtos.EventOutput;

import java.util.List;

public interface FindEventsByStatusUseCase {
    java.util.List<EventOutput> execute(String status);
}
