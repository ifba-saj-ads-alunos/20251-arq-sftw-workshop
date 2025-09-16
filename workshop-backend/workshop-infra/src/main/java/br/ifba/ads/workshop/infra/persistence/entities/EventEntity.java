package br.ifba.ads.workshop.infra.persistence.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity(name = "EventEntityLegacy")
@Table(name = "events")
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@AllArgsConstructor
public class EventEntity extends BaseEntity {
    private String name;
    private String description;
    private String date;
    private String location;
}