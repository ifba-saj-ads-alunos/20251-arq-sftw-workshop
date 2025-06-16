package br.ifba.ads.workshop.core.application.exceptions;

import br.ifba.ads.workshop.core.domain.exception.BusinessException;

public class ResourceAlreadyExistsException extends BusinessException {
    public ResourceAlreadyExistsException(String message) {
        super(message);
    }

    public ResourceAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}
