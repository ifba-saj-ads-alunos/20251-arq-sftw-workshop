package br.ifba.ads.workshop.web.controllers;

import br.ifba.ads.workshop.core.application.dtos.UserOutput;
import br.ifba.ads.workshop.core.application.usecases.CreateUserUseCase;
import br.ifba.ads.workshop.core.application.usecases.FindUserByIdUseCase;
import br.ifba.ads.workshop.web.configs.TokenMainInfo;
import br.ifba.ads.workshop.web.dtos.CreateUserRequestDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
public final class UserController extends BaseController {

    private final CreateUserUseCase createUserUseCase;
    private final FindUserByIdUseCase findUserByIdUseCase;

    @PostMapping
    public ResponseEntity<UserOutput> createUser(@Valid @RequestBody CreateUserRequestDto requestDto) {
        var response = createUserUseCase.execute(requestDto.toCreateUserCommand());
        URI location = URI.create("/api/v1/users/%s/".formatted(response.id()));
        return ResponseEntity.created(location).body(response);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserOutput> getUser(@PathVariable UUID userId, @AuthenticationPrincipal TokenMainInfo principal) {
        validateUser(principal, userId);
        var response = findUserByIdUseCase.execute(userId);
        return ResponseEntity.ok(response);
    }
}
