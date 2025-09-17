package br.ifba.ads.workshop.core.application.dtos;

import java.util.UUID;

public record RejectEventCommand(UUID eventId, UUID approverId, String justification) {}
