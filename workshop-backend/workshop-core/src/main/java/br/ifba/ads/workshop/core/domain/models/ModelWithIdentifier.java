package br.ifba.ads.workshop.core.domain.models;
import java.util.Objects;
import java.util.UUID;

public abstract class ModelWithIdentifier {
    protected UUID id;

    public ModelWithIdentifier(UUID id) {
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

    public UUID getId() {
        return id;
    }
}
