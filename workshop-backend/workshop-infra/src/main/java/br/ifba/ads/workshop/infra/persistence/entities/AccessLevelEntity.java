package br.ifba.ads.workshop.infra.persistence.entities;

import br.ifba.ads.workshop.core.domain.models.enums.AccessLevelType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "access_levels")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AccessLevelEntity extends BaseEntity {
    @Enumerated(EnumType.STRING)
    @Column(name = "name")
    private AccessLevelType type;
    private String description;
}
