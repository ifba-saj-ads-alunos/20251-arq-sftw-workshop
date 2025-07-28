package br.ifba.ads.workshop.infra.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.ifba.ads.workshop.core.domain.models.Event;

public interface JpaEventRepository extends JpaRepository<Event, Long> {
    
}
