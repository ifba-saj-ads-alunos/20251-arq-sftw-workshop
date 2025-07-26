package br.ifba.ads.workshop.core.application.dtos;

public record LoginOutput(
        UserOutput user,
        String token
) {
}
