package br.ifba.ads.workshop.infra.persistence.entities;

import br.ifba.ads.workshop.infra.persistence.entities.user.UserEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.ZonedDateTime;

@Entity
@Table(name = "sessions")
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@AllArgsConstructor
public class SessionsEntity extends BaseEntity{

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;
    @Column(length = 1000) // Increase token column length for JWT tokens
    private String token;
    private ZonedDateTime issuedAt;
    private ZonedDateTime expiresAt;
    private String userAgent;
    private String ipAddress;
    private boolean active;
}
