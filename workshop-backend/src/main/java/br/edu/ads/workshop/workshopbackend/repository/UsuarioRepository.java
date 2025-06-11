package br.edu.ads.workshop.workshopbackend.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.ads.workshop.workshopbackend.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByEmail(String email);
    boolean existsByEmail(String email);
}
