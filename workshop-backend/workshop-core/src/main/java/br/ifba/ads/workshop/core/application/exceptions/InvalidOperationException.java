package br.ifba.ads.workshop.core.application.exceptions;

import br.ifba.ads.workshop.core.domain.exception.BusinessException;

public class InvalidOperationException extends BusinessException {
    public InvalidOperationException(String message) {
        super(message);
    }

    public InvalidOperationException(String message, Throwable cause) {
        super(message, cause);
    }
}
