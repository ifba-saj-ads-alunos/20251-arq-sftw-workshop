package br.ifba.ads.workshop.api.web.controllers;

import org.springframework.http.HttpStatus;

public record RestErrorMessage(
        HttpStatus status,
        String message
) {
}
