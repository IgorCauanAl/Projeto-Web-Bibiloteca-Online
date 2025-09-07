package br.ifba.edu.BancoDeDadosIfba.biblioteca_orm.repository;

import java.util.List;

import br.ifba.edu.BancoDeDadosIfba.biblioteca_orm.entities.LivroEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LivroRepository extends JpaRepository<LivroEntity, Long> {

    List<LivroEntity> findByNomeContainingIgnoreCase(String nome);//para pesquisar o nome no html
    boolean existsByNome(String nome);
    boolean existsByNomeAndIdNot(String nome, Long id);
}
