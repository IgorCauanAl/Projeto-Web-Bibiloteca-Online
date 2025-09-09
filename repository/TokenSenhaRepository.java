package br.ifba.edu.BancoDeDadosIfba.biblioteca_orm.repository;


import br.ifba.edu.BancoDeDadosIfba.biblioteca_orm.entities.TokenSenha;
import br.ifba.edu.BancoDeDadosIfba.biblioteca_orm.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TokenSenhaRepository extends JpaRepository<TokenSenha, Long> {

    Optional<TokenSenha> buscarUsuarioECodigo (Usuario usuario, String codigo);
}
