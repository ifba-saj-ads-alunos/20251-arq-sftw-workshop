package br.ifba.ads.workshop.infra.adapters;

import br.ifba.ads.workshop.core.application.gateways.PasswordEncoderGateway;
import br.ifba.ads.workshop.core.domain.models.valueObjects.EncryptedPassword;
import br.ifba.ads.workshop.core.domain.models.valueObjects.Password;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BCryptPasswordEncoderAdapter implements PasswordEncoderGateway {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public EncryptedPassword encode(Password password) {
        return new EncryptedPassword(bCryptPasswordEncoder.encode(password.value()));
    }

    @Override
    public boolean matches(Password password, EncryptedPassword encryptedPassword) {
        return bCryptPasswordEncoder.matches(password.value(), encryptedPassword.value());
    }
}
