package br.edu.ads.workshop.workshopbackend.dto;

import lombok.Data;
import jakarta.validation.constraints.*;
import jakarta.validation.constraints.NotNull;
import br.edu.ads.workshop.workshopbackend.model.Perfil;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

@Data
public class AuthRequest {
    @NotBlank
    private String nome;

    @NotNull
    private Perfil perfil;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Size(min = 6, message = "A senha deve ter no mínimo 6 caracteres")
    private String senha;
}
