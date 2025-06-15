package br.edu.ads.workshop.workshopbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponse {
    private String message;
    private String nome;
    private String email;
    private String perfil;
}
