package br.ifba.ads.workshop.core.domain.models;

import lombok.Getter;

import java.util.Objects;

@Getter
public abstract class ModelWithIdentifier {
    protected String id;

    public ModelWithIdentifier(String id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ModelWithIdentifier modelWithIdentifier = (ModelWithIdentifier) o;
        return Objects.equals(id, modelWithIdentifier.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    public boolean isNew() {
        return id == null;
    }
}
