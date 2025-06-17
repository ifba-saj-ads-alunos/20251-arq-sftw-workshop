package br.ifba.ads.workshop.api.persistence.entities;

import br.ifba.ads.workshop.core.domain.models.enums.AccessLevel;
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
    private AccessLevel level;
    private String description;
}
