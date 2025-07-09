package br.ifba.ads.workshop.core.domain.models.valueObjects;

import br.ifba.ads.workshop.core.domain.exception.InvalidDataException;

import java.util.regex.Pattern;

public record Email(String value) {
    private static final String IFBA_DOMAIN = "@ifba.edu.br";
    private static final Pattern STUDENT_PATTERN = Pattern.compile(
            "^\\d{4}\\d{1,2}t[a-z]+saj\\d+@ifba\\.edu\\.br$"
    );
    private static final Pattern EMAIL_FORMAT = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");

    public Email(String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new InvalidDataException("Email não pode ser vazio");
        }
        if (!EMAIL_FORMAT.matcher(value).matches()) {
            throw new InvalidDataException("Formato de email inválido");
        }
        this.value = value.toLowerCase();
    }

    public boolean isIfbaEmail() {
        return value.endsWith(IFBA_DOMAIN);
    }

    public boolean isStudentEmail() {
        return isIfbaEmail() && STUDENT_PATTERN.matcher(value).matches();
    }
}