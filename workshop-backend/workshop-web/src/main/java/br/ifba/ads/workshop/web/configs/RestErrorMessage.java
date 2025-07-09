package br.ifba.ads.workshop.web.configs;

import org.springframework.http.HttpStatus;

public record RestErrorMessage(
        HttpStatus status,
        String message
) {
}
