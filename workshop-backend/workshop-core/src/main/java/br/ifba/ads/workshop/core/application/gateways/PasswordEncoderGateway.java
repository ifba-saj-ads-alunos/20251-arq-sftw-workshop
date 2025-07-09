package br.ifba.ads.workshop.core.application.gateways;

import br.ifba.ads.workshop.core.domain.models.valueObjects.EncryptedPassword;
import br.ifba.ads.workshop.core.domain.models.valueObjects.Password;

public interface PasswordEncoderGateway {
    EncryptedPassword encode(Password password);
    boolean matches(Password password, EncryptedPassword encryptedPassword);
}
