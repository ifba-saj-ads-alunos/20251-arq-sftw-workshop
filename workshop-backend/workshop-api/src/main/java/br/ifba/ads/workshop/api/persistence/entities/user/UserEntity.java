package br.ifba.ads.workshop.api.persistence.entities.user;

import br.ifba.ads.workshop.api.persistence.entities.AccessLevelEntity;
import br.ifba.ads.workshop.api.persistence.entities.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Entity
@Getter
@Table(name = "users")
@AllArgsConstructor
public class UserEntity extends BaseEntity {
    private String name;
    private String email;

    @ManyToOne
    @JoinColumn(name = "user_role_id")
    private UserRoleEntity userRole;

    @ManyToOne
    @JoinColumn(name = "access_level_id")
    private AccessLevelEntity accessLevel;

    private String password;

    private Boolean isActive;

    public UserEntity(UUID id) {
        super(id);
    }

    public UserEntity() {
        super();
    }
}
