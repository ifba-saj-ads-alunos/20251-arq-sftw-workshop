package br.ifba.ads.workshop.api.persistence.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.UUID;

@Inheritance
@MappedSuperclass
@Getter
@Setter
public abstract class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private boolean deleted;

    private ZonedDateTime dateCreate;

    private ZonedDateTime dateUpdate;

    public BaseEntity() {
        this.dateCreate = ZonedDateTime.now();
        this.dateUpdate = ZonedDateTime.now();
    }

    public BaseEntity(UUID id) {
        this();
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        BaseEntity that = (BaseEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
