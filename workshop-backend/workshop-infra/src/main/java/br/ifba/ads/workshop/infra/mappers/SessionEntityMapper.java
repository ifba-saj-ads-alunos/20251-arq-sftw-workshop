package br.ifba.ads.workshop.infra.mappers;

import br.ifba.ads.workshop.core.domain.models.Session;
import br.ifba.ads.workshop.infra.persistence.entities.SessionsEntity;
import br.ifba.ads.workshop.infra.persistence.entities.user.UserEntity;
import org.springframework.stereotype.Component;

@Component
public final class SessionEntityMapper implements BaseEntityMapper<Session, SessionsEntity> {

    @Override
    public Session toDomain(SessionsEntity entity){
        return new Session(
                entity.getId(),
                entity.getCreatedAt(),
                entity.getUpdatedAt(),
                entity.isDeleted(),
                entity.getUser().getId(),
                entity.getToken(),
                entity.getIssuedAt(),
                entity.getExpiresAt(),
                entity.getUserAgent(),
                entity.getIpAddress(),
                entity.isActive()
        );
    }

    @Override
    public SessionsEntity toEntity(Session domain) {
      return SessionsEntity.builder()
              .id(domain.getId())
              .token(domain.getToken())
              .issuedAt(domain.getIssuedAt())
              .expiresAt(domain.getExpiresAt())
              .userAgent(domain.getUserAgent())
              .ipAddress(domain.getIpAddress())
              .active(domain.isActive())
              .user(new UserEntity(domain.getUserId()))
              .createdAt(domain.getCreatedAt())
              .updatedAt(domain.getUpdatedAt())
              .deleted(domain.getDeleted())
              .build();
    }
}
