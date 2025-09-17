package br.ifba.ads.workshop.core.application.gateways;

import br.ifba.ads.workshop.core.application.dtos.NotificationOutput;

import java.util.List;
import java.util.UUID;

public interface NotificationGateway {
    void sendNotification(UUID userId, String title, String message);

    java.util.List<NotificationOutput> fetchNotificationsForUser(UUID userId);

    void markAsRead(UUID userId, UUID notificationId);

    Long fetchUnreadCount(UUID userId);

    void markAllAsRead(UUID userId);
}
