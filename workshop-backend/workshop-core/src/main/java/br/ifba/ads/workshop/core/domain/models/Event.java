package br.ifba.ads.workshop.core.domain.models;

import br.ifba.ads.workshop.core.domain.exception.InvalidDataException;
import br.ifba.ads.workshop.core.domain.models.enums.EventModality;
import br.ifba.ads.workshop.core.domain.models.enums.EventStatus;

import java.time.ZonedDateTime;

public final class Event extends AuditableModel {
    private String title;
    private String description;
    private ZonedDateTime startsAt;
    private ZonedDateTime endsAt;
    private Integer vacancies;
    private EventModality modality;
    private EventStatus status;
    private String location;      // e.g., room name for PRESENCIAL
    private String remoteLink;    // e.g., meet/zoom link for ONLINE
    private String category;      // free text from UI

    public Event(
            String title,
            String description,
            ZonedDateTime startsAt,
            ZonedDateTime endsAt,
            Integer vacancies,
            EventModality modality,
            String location,
            String remoteLink,
            String category
    ) {
        super();
        this.title = title;
        this.description = description;
        this.startsAt = startsAt;
        this.endsAt = endsAt;
        this.vacancies = vacancies;
        this.modality = modality;
        this.status = EventStatus.DRAFT;
        this.location = location;
        this.remoteLink = remoteLink;
        this.category = category;
        validate();
    }

    public Event(
            java.util.UUID id,
            java.time.ZonedDateTime createdAt,
            java.time.ZonedDateTime updatedAt,
            boolean deleted,
            String title,
            String description,
            ZonedDateTime startsAt,
            ZonedDateTime endsAt,
            Integer vacancies,
            EventModality modality,
            EventStatus status,
            String location,
            String remoteLink,
            String category
    ) {
        super(id, createdAt, updatedAt, deleted);
        this.title = title;
        this.description = description;
        this.startsAt = startsAt;
        this.endsAt = endsAt;
        this.vacancies = vacancies;
        this.modality = modality;
        this.status = status;
        this.location = location;
        this.remoteLink = remoteLink;
        this.category = category;
        validate();
    }

    private void validate() {
        if (title == null || title.isBlank()) {
            throw new InvalidDataException("Título do evento não pode ser vazio");
        }
        if (title.length() > 100) {
            throw new InvalidDataException("Título não pode exceder 100 caracteres");
        }
        if (startsAt == null || endsAt == null) {
            throw new InvalidDataException("Datas de início e fim são obrigatórias");
        }
        if (!startsAt.isBefore(endsAt)) {
            throw new InvalidDataException("Data de início deve ser anterior à data de fim");
        }
        if (vacancies != null && vacancies < 0) {
            throw new InvalidDataException("Vagas não pode ser negativo");
        }
        if (modality == null) {
            throw new InvalidDataException("Modalidade é obrigatória");
        }
        if (modality == EventModality.ONLINE && (remoteLink == null || remoteLink.isBlank())) {
            throw new InvalidDataException("Link é obrigatório para eventos online");
        }
        if (modality == EventModality.PRESENCIAL && (location == null || location.isBlank())) {
            throw new InvalidDataException("Local é obrigatório para eventos presenciais");
        }
    }

    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public ZonedDateTime getStartsAt() { return startsAt; }
    public ZonedDateTime getEndsAt() { return endsAt; }
    public Integer getVacancies() { return vacancies; }
    public EventModality getModality() { return modality; }
    public EventStatus getStatus() { return status; }
    public String getLocation() { return location; }
    public String getRemoteLink() { return remoteLink; }
    public String getCategory() { return category; }

    public void setStatus(EventStatus status) { this.status = status; }
}
