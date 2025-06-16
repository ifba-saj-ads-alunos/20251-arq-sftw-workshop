package br.ifba.ads.workshop.api.persistence.entities;

import br.ifba.ads.workshop.core.domain.models.enums.AccessLevel;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.Getter;

@Entity
@Table(name = "access_levels")
@Getter
public class AccessLevelEntity extends BaseEntity {
    @Enumerated(EnumType.STRING)
    private AccessLevel name;
    private String description;
}
