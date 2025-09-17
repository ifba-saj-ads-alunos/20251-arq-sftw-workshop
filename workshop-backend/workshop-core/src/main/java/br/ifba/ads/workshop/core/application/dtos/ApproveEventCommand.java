package br.ifba.ads.workshop.core.application.dtos;

import java.util.UUID;

public record ApproveEventCommand(UUID eventId, UUID approverId) {}
