package br.ifba.ads.workshop.core.domain.models;

import br.ifba.ads.workshop.core.domain.exception.InternalServerException;
import br.ifba.ads.workshop.core.domain.models.enums.AccessLevelType;

import java.time.ZonedDateTime;
import java.util.UUID;

public class AccessLevel extends BaseModel {
    private final AccessLevelType type;

    public AccessLevel(
            UUID id,
            AccessLevelType type
    ) throws InternalServerException {
        super(id);
        this.type = type;
    }

    public AccessLevelType getType() {
        return type;
    }
}
