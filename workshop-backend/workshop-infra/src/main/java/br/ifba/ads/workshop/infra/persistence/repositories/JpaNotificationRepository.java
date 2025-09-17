package br.ifba.ads.workshop.infra.persistence.repositories;

import br.ifba.ads.workshop.infra.persistence.entities.notification.NotificationEntity;
import java.util.List;
import java.util.UUID;

public interface JpaNotificationRepository extends JpaBaseRepository<NotificationEntity> {
	List<NotificationEntity> findByUserIdOrderBySentAtDesc(UUID userId);
	Long countByUserIdAndReadIsFalse(UUID userId);
	java.util.Optional<NotificationEntity> findByIdAndUserId(java.util.UUID id, java.util.UUID userId);

	// Marks all unread notifications for the given user as read. Returns number of rows updated.
	@org.springframework.data.jpa.repository.Modifying
	@org.springframework.data.jpa.repository.Query("update NotificationEntity n set n.read = true where n.userId = :userId and (n.read is null or n.read = false)")
	int markAllReadForUser(java.util.UUID userId);
}
