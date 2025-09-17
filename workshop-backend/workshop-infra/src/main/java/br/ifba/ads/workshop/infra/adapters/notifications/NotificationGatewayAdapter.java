package br.ifba.ads.workshop.infra.adapters.notifications;

import br.ifba.ads.workshop.core.application.gateways.NotificationGateway;
import br.ifba.ads.workshop.infra.persistence.entities.notification.NotificationEntity;
import br.ifba.ads.workshop.infra.persistence.repositories.JpaNotificationRepository;

import java.time.ZonedDateTime;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.List;
import br.ifba.ads.workshop.core.application.dtos.NotificationOutput;

public class NotificationGatewayAdapter implements NotificationGateway {

    private final JpaNotificationRepository repository;

    public NotificationGatewayAdapter(JpaNotificationRepository repository) {
        this.repository = repository;
    }

    @Override
    public void sendNotification(UUID userId, String title, String message) {
        var n = NotificationEntity.builder()
                .userId(userId)
                .title(title)
                .message(message)
                .sentAt(ZonedDateTime.now())
                .build();
        repository.save(n);
    }

    @Override
    public List<NotificationOutput> fetchNotificationsForUser(UUID userId) {
        var list = repository.findByUserIdOrderBySentAtDesc(userId);
    return list.stream().map(e -> new NotificationOutput(
        e.getId(),
        e.getUserId(),
        e.getTitle(),
        e.getMessage(),
        e.getSentAt(),
        e.getRead()
    )).collect(Collectors.toList());
    }

    @Override
    public void markAsRead(UUID userId, UUID notificationId) {
        var opt = repository.findByIdAndUserId(notificationId, userId);
        if (opt.isPresent()) {
            var e = opt.get();
            e.setRead(true);
            repository.save(e);
        }
    }

    @Override
    public void markAllAsRead(UUID userId) {
        // perform bulk update via repository
        try {
            repository.markAllReadForUser(userId);
        } catch (Exception ex) {
            // fallback to per-entity update
            var list = repository.findByUserIdOrderBySentAtDesc(userId);
            list.stream().filter(e -> e.getRead() == null || !e.getRead()).forEach(e -> {
                e.setRead(true);
                repository.save(e);
            });
        }
    }

    @Override
    public Long fetchUnreadCount(UUID userId) {
        return repository.countByUserIdAndReadIsFalse(userId);
    }
}
