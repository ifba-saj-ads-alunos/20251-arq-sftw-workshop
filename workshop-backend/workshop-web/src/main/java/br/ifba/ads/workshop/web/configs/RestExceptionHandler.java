package br.ifba.ads.workshop.web.configs;

import br.ifba.ads.workshop.core.domain.exception.InvalidDataException;
import br.ifba.ads.workshop.core.domain.exception.ResourceAlreadyExistsException;
import br.ifba.ads.workshop.core.domain.exception.InternalServerException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@Slf4j
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(InvalidDataException.class)
    private ResponseEntity<RestErrorMessage> handleInvalidDataException(InvalidDataException e) {
        var response = new RestErrorMessage(HttpStatus.BAD_REQUEST, e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(ResourceAlreadyExistsException.class)
    private ResponseEntity<RestErrorMessage> handleResourceAlreadyExistsException(ResourceAlreadyExistsException e) {
        var response = new RestErrorMessage(HttpStatus.CONFLICT, e.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    @ExceptionHandler({InternalServerException.class, RuntimeException.class})
    private ResponseEntity<RestErrorMessage> handleInternalExceptions(RuntimeException e) {
        var response = new RestErrorMessage(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Alguns problemas estão ocorrendo, nossa equipe já está resolvendo, tente novamente mais tarde"
        );
        if(e instanceof InternalServerException){
            log.error("Error: {}", e.getMessage(), e);
        }else{
            log.error("Error inesperado: {}", e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

}
