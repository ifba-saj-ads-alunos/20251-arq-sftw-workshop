package br.ifba.ads.workshop.infra.persistence.entities.user;

import br.ifba.ads.workshop.core.domain.models.enums.UserRoleType;
import br.ifba.ads.workshop.infra.persistence.entities.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_roles")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserRoleEntity extends BaseEntity {
    @Enumerated(EnumType.STRING)
    @Column(name = "name")
    private UserRoleType type;
    private String description;


}
