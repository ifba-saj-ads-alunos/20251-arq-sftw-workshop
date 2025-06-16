package br.ifba.ads.workshop.core.application.exceptions;

import br.ifba.ads.workshop.core.domain.exception.BusinessException;

public class ResourceNotFoundException extends BusinessException {
    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
