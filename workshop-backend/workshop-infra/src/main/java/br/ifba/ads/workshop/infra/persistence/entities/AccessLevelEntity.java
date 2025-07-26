package br.ifba.ads.workshop.infra.persistence.entities;

import br.ifba.ads.workshop.core.domain.models.enums.AccessLevelType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "access_levels")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccessLevelEntity extends BaseEntity {
    @Enumerated(EnumType.STRING)
    @Column(name = "name")
    private AccessLevelType type;
    private String description;

    public AccessLevelEntity(UUID id, AccessLevelType type){
        super(id);
        this.type = type;
    }
}
