package br.edu.ads.workshop.workshopbackend.service;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import br.edu.ads.workshop.workshopbackend.dto.AuthResponse;
import br.edu.ads.workshop.workshopbackend.exception.EmailAlreadyUsedException;
import br.edu.ads.workshop.workshopbackend.exception.InvalidCredentialsException;
import br.edu.ads.workshop.workshopbackend.dto.AuthRequest;
import br.edu.ads.workshop.workshopbackend.model.Usuario;
import br.edu.ads.workshop.workshopbackend.repository.UsuarioRepository;

@Service
public class AuthService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public AuthResponse register(AuthRequest req) {
        if (usuarioRepository.existsByEmail(req.getEmail())) {
            throw new EmailAlreadyUsedException("Email já cadastrado");
        }

        String senhaHash = passwordEncoder.encode(req.getSenha());

        Usuario user = Usuario.builder()
                .nome(req.getNome())
                .perfil(req.getPerfil())
                .email(req.getEmail())
                .senha(senhaHash)
                .build();

        usuarioRepository.save(user);

        return new AuthResponse(
                "Usuário cadastrado com sucesso",
                user.getNome(),
                user.getEmail(),
                user.getPerfil().name());
    }

    public AuthResponse login(AuthRequest req) {
        Optional<Usuario> userOpt = usuarioRepository.findByEmail(req.getEmail());
        if (userOpt.isEmpty()) {
            throw new InvalidCredentialsException("Usuário não encontrado");
        }

        Usuario user = userOpt.get();
        if (!passwordEncoder.matches(req.getSenha(), user.getSenha())) {
            throw new InvalidCredentialsException("Senha incorreta");
        }
        return new AuthResponse(
                "Login realizado com sucesso",
                user.getNome(),
                user.getEmail(),
                user.getPerfil().name());
    }

}
