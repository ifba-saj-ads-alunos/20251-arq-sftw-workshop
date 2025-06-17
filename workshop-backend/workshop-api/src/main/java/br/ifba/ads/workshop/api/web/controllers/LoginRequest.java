package br.ifba.ads.workshop.api.web.controllers;

public record LoginRequest(
    String email,
    String password
) {}