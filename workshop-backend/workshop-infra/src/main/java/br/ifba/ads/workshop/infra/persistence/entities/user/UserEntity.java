package br.ifba.ads.workshop.infra.persistence.entities.user;

import java.time.ZonedDateTime;
import java.util.UUID;

import br.ifba.ads.workshop.infra.persistence.entities.AccessLevelEntity;
import br.ifba.ads.workshop.infra.persistence.entities.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;


@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@SuperBuilder
public class UserEntity extends BaseEntity {
    private String name;
    private String cpf;
    private String email;

    @ManyToOne
    @JoinColumn(name = "user_role_id")
    private UserRoleEntity userRole;

    @ManyToOne
    @JoinColumn(name = "access_level_id")
    private AccessLevelEntity accessLevel;

    private String password;

    private ZonedDateTime lastAccess;

    public UserEntity() {
        super();
    }

    public UserEntity(UUID id) {
        super(id);
    }
}
