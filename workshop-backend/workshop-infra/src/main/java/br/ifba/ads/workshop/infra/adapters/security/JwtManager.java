package br.ifba.ads.workshop.infra.adapters.security;

import br.ifba.ads.workshop.core.application.gateways.TokenManagerGateway;
import br.ifba.ads.workshop.core.domain.exception.UnauthorizedException;
import br.ifba.ads.workshop.core.domain.models.User;
import br.ifba.ads.workshop.infra.persistence.repositories.JpaSessionRepository;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Component;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public final class JwtManager implements TokenManagerGateway {
    private final JpaSessionRepository sessionRepository;
    private final RSAPrivateKey privateKey;
    private final RSAPublicKey publicKey;
    private final int expirationSeconds;
    private final String issuer;

    public JwtManager(
            JpaSessionRepository sessionRepository,
            @Value("${security.jwt.private}") RSAPrivateKey privateKey,
            @Value("${security.jwt.public}") RSAPublicKey publicKey,
            @Value("${security.jwt.expiration}") int expirationSeconds,
            @Value("${security.jwt.issuer}") String issuer
    ) {
        this.sessionRepository = sessionRepository;
        this.privateKey = privateKey;
        this.publicKey = publicKey;
        this.expirationSeconds = expirationSeconds;
        this.issuer = issuer;
    }

    @Override
    public String generateToken(User userAuth) {
        final Instant now = ZonedDateTime.now(ZoneOffset.UTC).toInstant();
        final Instant expiration = now.plusSeconds(expirationSeconds);
        final UserAuthenticated userAuthenticated = new UserAuthenticated(userAuth);
        final String scopes = userAuthenticated.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
        final JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer(issuer)
                .issuedAt(now)
                .expiresAt(expiration)
                .subject(userAuth.getId().toString())
                .claim("scope", scopes)
                .build();
        return encoder().encode(JwtEncoderParameters.from(claims)).getTokenValue();

    }

    @Override
    public String validateTokenAndGetSubject(String token) throws UnauthorizedException {
        return sessionRepository.findByToken(token)
                .filter(session -> !session.isDeleted() && session.isActive())
                .map(session -> {
                    if (getExpirationDate(token).isBefore(ZonedDateTime.now())) {
                        session.setActive(false);
                        sessionRepository.delete(session);
                        throw new UnauthorizedException("expired.token");
                    }
                    return decoder().decode(token).getSubject();
                })
                .orElseThrow(() -> new UnauthorizedException("invalid.token"));
    }


    @Override
    public ZonedDateTime getExpirationDate(String token) {
        return tokenIsnstantToZonedDateTime(Objects.requireNonNull(decoder().decode(token).getExpiresAt()));
    }

    @Override
    public ZonedDateTime getIssuedAt(String token) {
        return tokenIsnstantToZonedDateTime(Objects.requireNonNull(decoder().decode(token).getIssuedAt()));
    }

    private ZonedDateTime tokenIsnstantToZonedDateTime(Instant instant){
        return instant.atZone(ZoneId.of("UTC"));
    }

    private JwtDecoder decoder() {
        return NimbusJwtDecoder.withPublicKey(this.publicKey).build();
    }

    private JwtEncoder encoder() {
        JWK jwk = new RSAKey.Builder(this.publicKey).privateKey(this.privateKey).build();
        JWKSource<SecurityContext> jwks = new ImmutableJWKSet<>(new JWKSet(jwk));
        return new NimbusJwtEncoder(jwks);
    }
}
