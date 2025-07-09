package br.ifba.ads.workshop.web.controllers;

import br.ifba.ads.workshop.core.application.dtos.UserOutput;
import br.ifba.ads.workshop.core.application.usecases.CreateUserUseCase;
import br.ifba.ads.workshop.web.dtos.CreateUserRequestDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final CreateUserUseCase createUserUseCase;

    @PostMapping
    public ResponseEntity<UserOutput> createUser(@Valid @RequestBody CreateUserRequestDto requestDto) {
        var response = createUserUseCase.execute(requestDto.toCreateUserCommand());
        return ResponseEntity.ok(response);
    }
}
