package br.ifba.edu.BancoDeDadosIfba.biblioteca_orm.repository;

import br.ifba.edu.BancoDeDadosIfba.biblioteca_orm.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario,Long> {


    Optional<Usuario> findByEmail(String email);

    boolean existsByEmail(String email);
}
