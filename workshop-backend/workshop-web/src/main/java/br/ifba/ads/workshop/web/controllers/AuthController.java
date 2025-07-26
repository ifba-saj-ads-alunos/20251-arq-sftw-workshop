package br.ifba.ads.workshop.web.controllers;

import br.ifba.ads.workshop.core.application.dtos.LoginCommand;
import br.ifba.ads.workshop.core.application.dtos.LoginOutput;
import br.ifba.ads.workshop.core.application.dtos.LogoutCommand;
import br.ifba.ads.workshop.core.application.usecases.auth.LoginUseCase;
import br.ifba.ads.workshop.core.application.usecases.auth.LogoutUseCase;
import br.ifba.ads.workshop.core.domain.models.valueObjects.Email;
import br.ifba.ads.workshop.core.domain.models.valueObjects.Password;
import br.ifba.ads.workshop.web.configs.TokenMainInfo;
import br.ifba.ads.workshop.web.dtos.LoginRequestDto;
import br.ifba.ads.workshop.web.utils.HttpServletRequestUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
public final class AuthController extends BaseController {

    private final LoginUseCase loginUseCase;
    private final LogoutUseCase logoutUseCase;

    @PostMapping("/login")
    public ResponseEntity<LoginOutput> login(@RequestBody LoginRequestDto loginRequestDto, HttpServletRequest request) {
        final var command = new LoginCommand(
                new Email(loginRequestDto.email()),
                new Password(loginRequestDto.password()),
                request.getHeader("User-Agent"),
                HttpServletRequestUtils.getClientIp(request)
        );
        final var response = loginUseCase.execute(command);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletRequest request) {
        TokenMainInfo userInfo;
        try {
            userInfo = (TokenMainInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        log.info(Thread.currentThread().toString());
        final var command = new LogoutCommand(
                userInfo.userId(),
                HttpServletRequestUtils.extractToken(request)
        );
        logoutUseCase.execute(command);
        return ResponseEntity.ok().build();
    }

}
