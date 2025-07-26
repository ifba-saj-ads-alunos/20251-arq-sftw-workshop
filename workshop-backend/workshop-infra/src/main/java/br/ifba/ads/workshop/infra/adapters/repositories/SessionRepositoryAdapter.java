package br.ifba.ads.workshop.infra.adapters.repositories;

import br.ifba.ads.workshop.core.domain.models.Session;
import br.ifba.ads.workshop.core.domain.repositories.SessionRepository;
import br.ifba.ads.workshop.infra.mappers.SessionEntityMapper;
import br.ifba.ads.workshop.infra.persistence.entities.SessionsEntity;
import br.ifba.ads.workshop.infra.persistence.repositories.JpaSessionRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class SessionRepositoryAdapter extends CrudRepositoryAdapter<Session, SessionsEntity>
        implements SessionRepository {

    private final JpaSessionRepository jpaSessionRepository;
    private final SessionEntityMapper sessionsEntityMapper;

    public SessionRepositoryAdapter(
            JpaSessionRepository jpaSessionRepository,
            SessionEntityMapper sessionsEntityMapper
    ) {
        super(jpaSessionRepository, sessionsEntityMapper);
        this.jpaSessionRepository = jpaSessionRepository;
        this.sessionsEntityMapper = sessionsEntityMapper;
    }


    @Override
    public Optional<Session> findByToken(String token) {
        return jpaSessionRepository.findByToken(token)
                .map(sessionsEntityMapper::toDomain);
    }
}
