package br.ifba.ads.workshop.api.web.controllers;

import br.ifba.ads.workshop.core.application.exceptions.ResourceAlreadyExistsException;
import br.ifba.ads.workshop.core.application.ports.in.CreateUserUseCase;
import br.ifba.ads.workshop.core.application.usecases.user.dtos.CreateUserCommand;
import br.ifba.ads.workshop.core.domain.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final CreateUserUseCase createUserUseCase;

    @PostMapping(name = "/register")
    ResponseEntity<?> register(@RequestBody CreateUserCommand createUserCommand) {
        try {
            return new ResponseEntity(createUserUseCase.execute(createUserCommand), HttpStatus.CREATED);
        }catch (ResourceAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (BusinessException e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new RestErrorMessage(
                    HttpStatus.BAD_REQUEST, e.getMessage()));
        } catch (Exception e) {
            log.error("Erro ao registrar usuário: {}", e.getMessage());
            log.error(e.getStackTrace().toString());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new RestErrorMessage(
                    HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao registrar usuário"));
        }
    }

}
