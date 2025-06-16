package br.ifba.ads.workshop.core.application.usecases.user.dtos;

public record CreateUserCommand (
        String name,
        String email,
        String password,
        int userRoleId
)
{

}
