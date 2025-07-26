package br.ifba.ads.workshop.infra.adapters.security;

import br.ifba.ads.workshop.core.domain.models.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;


@RequiredArgsConstructor
public final class UserAuthenticated implements UserDetails {
    private final User user;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.user.isAdmin()
                ? List.of(createAuthority("ROLE_ADMIN"), createAuthority("ROLE_USER"))
                : List.of(createAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        return user.getPassword().value();
    }

    @Override
    public String getUsername() {
        return user.getEmail().value();
    }

    private SimpleGrantedAuthority createAuthority(String role) {
        return new SimpleGrantedAuthority(role);
    }
}
