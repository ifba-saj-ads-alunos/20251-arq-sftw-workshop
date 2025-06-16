package br.ifba.ads.workshop.core.domain.exception;

public class InvalidDataException extends BusinessException {
  public InvalidDataException(String message) {
    super(message);
  }

    public InvalidDataException(String message, Throwable cause) {
        super(message, cause);
    }
}
