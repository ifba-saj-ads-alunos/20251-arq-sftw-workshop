package br.ifba.ads.workshop.core.domain.models;

import br.ifba.ads.workshop.core.domain.exception.InternalServerException;

import java.util.Objects;
import java.util.UUID;

public abstract class BaseModel {
    private final UUID id;

    public BaseModel(UUID id) {
        if (id == null) {
            throw new InternalServerException("Id cannot be null");
        }
        this.id = id;
    }

    public BaseModel(){
        this.id = UUID.randomUUID();
    }

    public UUID getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        BaseModel baseModel = (BaseModel) o;
        return Objects.equals(id, baseModel.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
