package br.ifba.ads.workshop.infra.persistence.entities.event;

import br.ifba.ads.workshop.infra.persistence.entities.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.ZonedDateTime;

@Entity
@Table(name = "event_sessions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class EventSessionEntity extends BaseEntity {
    @Column(name = "event_id")
    private java.util.UUID eventId;
    private ZonedDateTime startsAt;
    private ZonedDateTime endsAt;
    private Integer capacity;
    private String room;
}
