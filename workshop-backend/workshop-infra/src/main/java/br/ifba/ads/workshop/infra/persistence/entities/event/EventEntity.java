package br.ifba.ads.workshop.infra.persistence.entities.event;

import br.ifba.ads.workshop.infra.persistence.entities.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.ZonedDateTime;

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

    public EventEntity() {
        super();
    }
}

