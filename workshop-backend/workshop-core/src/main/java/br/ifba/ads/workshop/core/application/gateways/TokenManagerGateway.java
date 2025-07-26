package br.ifba.ads.workshop.core.application.gateways;

import br.ifba.ads.workshop.core.domain.models.User;

import java.time.ZonedDateTime;

public interface TokenManagerGateway {
    String generateToken(User userAuth);
    String validateTokenAndGetSubject(String token);
    ZonedDateTime getExpirationDate(String token);
    ZonedDateTime getIssuedAt(String token);
}
