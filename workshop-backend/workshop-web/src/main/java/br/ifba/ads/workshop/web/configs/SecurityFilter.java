package br.ifba.ads.workshop.web.configs;

import br.ifba.ads.workshop.core.domain.exception.UnauthorizedException;
import br.ifba.ads.workshop.core.domain.repositories.UserRepository;
import br.ifba.ads.workshop.infra.adapters.security.UserAuthenticated;
import br.ifba.ads.workshop.core.application.gateways.TokenManagerGateway;
import br.ifba.ads.workshop.web.utils.HttpServletRequestUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.JwtValidationException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class SecurityFilter extends OncePerRequestFilter {
    private final UserRepository userRepository;
    private final TokenManagerGateway tokenManagerGateway;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException
    {
        final String token = HttpServletRequestUtils.extractToken(request);
        if (!token.isEmpty()) {
            try {
                final var tokenSubject = tokenManagerGateway.validateTokenAndGetSubject(token);
                final UUID userId = UUID.fromString(tokenSubject);
                final var optionalUser = userRepository.findById(userId);
                if (optionalUser.isPresent()) {
                    final var user = optionalUser.get();
                    final var userAuthenticated = new UserAuthenticated(user);
                    final Authentication authentication = new UsernamePasswordAuthenticationToken(new TokenMainInfo(
                            user.getId(),
                            user.getUserRole().getType(),
                            user.getAccessLevel().getType()
                    ), null, userAuthenticated.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }catch (JwtValidationException | UnauthorizedException e){
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType("application/json");
                response.getWriter().write("{\"error\": \"Credentials not allowed\"}");
                return;
            }
        }
        filterChain.doFilter(request, response);
    }
}
