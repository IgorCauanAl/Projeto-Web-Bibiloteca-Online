package br.ifba.edu.BancoDeDadosIfba.biblioteca_orm.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@NoArgsConstructor
@Entity
@Table(name = "TB_CATEGORIA")
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "categoria_id")
    private int id;

    @NotBlank
    @Size(max = 100)
    @Column(name = "nome_categoria", nullable = false)
    private String nomeCategoria;

    @ManyToMany(mappedBy = "categorias")
   private List<LivroEntity> livros = new ArrayList<>();

}
