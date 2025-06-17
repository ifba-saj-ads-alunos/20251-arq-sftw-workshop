package br.ifba.ads.workshop.api.persistence.entities.user;

import br.ifba.ads.workshop.api.persistence.entities.BaseEntity;
import br.ifba.ads.workshop.core.domain.models.enums.UserRole;
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
    private UserRole role;
    private String description;


}
