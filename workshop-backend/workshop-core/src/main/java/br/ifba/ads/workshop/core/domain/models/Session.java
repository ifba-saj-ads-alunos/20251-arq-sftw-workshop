package br.ifba.ads.workshop.core.domain.models;

import java.time.ZonedDateTime;
import java.util.UUID;

public class Session extends AuditableModel {
    private final UUID userId;
    private final String token;
    private final ZonedDateTime issuedAt;
    private final ZonedDateTime expiresAt;
    private final String userAgent;
    private final String ipAddress;
    private boolean active;

    public Session(
            UUID id,
            ZonedDateTime createdAt,
            ZonedDateTime updatedAt,
            Boolean deleted,
            UUID userId,
            String token,
            ZonedDateTime issuedAt,
            ZonedDateTime expiresAt,
            String userAgent,
            String ipAddress,
            boolean active
    ) {
        super(id, createdAt, updatedAt, deleted);
        this.userId = userId;
        this.token = token;
        this.issuedAt = issuedAt;
        this.expiresAt = expiresAt;
        this.userAgent = userAgent;
        this.ipAddress = ipAddress;
        this.active = active;
    }

    public Session(
            UUID userId,
            String token,
            ZonedDateTime issuedAt,
            ZonedDateTime expiresAt,
            String userAgent,
            String ipAddress,
            boolean active
    ) {
        this.userId = userId;
        this.token = token;
        this.issuedAt = issuedAt;
        this.expiresAt = expiresAt;
        this.userAgent = userAgent;
        this.ipAddress = ipAddress;
        this.active = active;
    }

    public void desactive() {
        this.active = false;
    }

    public UUID getUserId() {
        return userId;
    }

    public String getToken() {
        return token;
    }

    public ZonedDateTime getIssuedAt() {
        return issuedAt;
    }

    public ZonedDateTime getExpiresAt() {
        return expiresAt;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public boolean isActive() {
        return active;
    }
}
