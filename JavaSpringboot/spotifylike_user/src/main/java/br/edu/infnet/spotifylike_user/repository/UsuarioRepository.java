package br.edu.infnet.spotifylike_user.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.infnet.spotifylike_user.domain.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {
}
