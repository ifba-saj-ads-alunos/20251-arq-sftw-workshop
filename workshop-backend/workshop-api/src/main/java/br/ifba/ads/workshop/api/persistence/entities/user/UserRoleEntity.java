package br.ifba.ads.workshop.api.persistence.entities.user;

import br.ifba.ads.workshop.api.persistence.entities.BaseEntity;
import br.ifba.ads.workshop.core.domain.models.enums.UserRole;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.Getter;

@Entity
@Table(name = "user_roles")
@Getter
public class UserRoleEntity extends BaseEntity {
    @Enumerated(EnumType.STRING)
    private UserRole name;
    private String description;
}
