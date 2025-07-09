package br.ifba.ads.workshop.core.domain.validators;

import br.ifba.ads.workshop.core.domain.exception.InvalidDataException;

import java.util.Arrays;

public class EnumValidator {
    public static <E extends Enum<E>> void validateName(String name, Class<E> enumClass) {
        boolean isValid = Arrays.stream(enumClass.getEnumConstants())
                .anyMatch(e -> e.name().equalsIgnoreCase(name));
        if (!isValid) {
            throw new InvalidDataException(name + " is not a valid enum name, the valid names are: " + Arrays.toString(enumClass.getEnumConstants()));
        }
    }
}
