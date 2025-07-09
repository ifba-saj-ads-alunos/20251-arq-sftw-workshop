package br.ifba.ads.workshop.core.domain.models;

import br.ifba.ads.workshop.core.domain.exception.InternalServerException;

import java.time.ZonedDateTime;
import java.util.UUID;

public abstract class AuditableModel extends BaseModel {
    private final ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;
    private Boolean deleted;

    public AuditableModel(
            UUID id,
            ZonedDateTime createdAt,
            ZonedDateTime updatedAt,
            Boolean deleted
    ) {
        super(id);
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deleted = deleted;
    }

    public AuditableModel() {
        super();
        this.createdAt = ZonedDateTime.now();
        this.updatedAt = ZonedDateTime.now();
        this.deleted = false;
    }

    public void markAsDeleted() {
        this.deleted = true;
    }

    public void markAsUpdated() {
        this.updatedAt = ZonedDateTime.now();
    }

    public void markAsRestored() {
       if(!deleted){
           throw new InternalServerException("Cannot restore from a non-deleted entity");
       }
       deleted = false;
       markAsUpdated();
    }


    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public ZonedDateTime getUpdatedAt() {
        return updatedAt;
    }

    public Boolean getDeleted() {
        return deleted;
    }
}
