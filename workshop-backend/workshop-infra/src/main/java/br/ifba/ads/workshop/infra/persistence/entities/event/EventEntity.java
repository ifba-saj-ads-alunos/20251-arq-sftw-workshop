package br.ifba.ads.workshop.infra.persistence.entities.event;

import java.time.ZonedDateTime;

import br.ifba.ads.workshop.infra.persistence.entities.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "events")
@Getter
@Setter
@AllArgsConstructor
@SuperBuilder
public class EventEntity extends BaseEntity {
    private String title;
    private String description;
    private ZonedDateTime startsAt;
    private ZonedDateTime endsAt;
    private Integer vacancies;
    private String modality;  // ONLINE, PRESENCIAL, HIBRIDO
    private String status;    // DRAFT, PUBLISHED, CLOSED, CANCELLED
    private String location;  // room or place
    private String remoteLink; // url for online events
    private String category;   // free label
    private java.util.UUID organizerId;
    private String rejectionJustification;

    public EventEntity() {
        super();
    }
}

