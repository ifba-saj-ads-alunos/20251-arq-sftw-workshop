package br.ifba.ads.workshop.infra.persistence.repositories;

import br.ifba.ads.workshop.infra.persistence.entities.event.EventEntity;
import java.util.List;

public interface JpaEventRepository extends JpaBaseRepository<EventEntity> {
	List<EventEntity> findByStatusOrderByStartsAtDesc(String status);
}

