package br.ifba.ads.workshop.infra.persistence.entities.notification;

import br.ifba.ads.workshop.infra.persistence.entities.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.ZonedDateTime;
import java.util.UUID;

@Entity
@Table(name = "notifications")
@Getter
@Setter
@AllArgsConstructor
@SuperBuilder
public class NotificationEntity extends BaseEntity {
    private UUID userId;
    private String title;
    private String message;
    private ZonedDateTime sentAt;
    private Boolean read;

    public NotificationEntity() { super(); }
}
