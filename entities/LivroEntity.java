package br.ifba.edu.BancoDeDadosIfba.biblioteca_orm.entities;

import br.ifba.edu.BancoDeDadosIfba.biblioteca_orm.model.GeneroEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "livro")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LivroEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "autor", nullable = false)
    private String autor;

    @Column(name = "anoPublicacao", nullable = false)
    private Integer anoPublicacao;

    @Column(name = "capa_url")
    private String capaUrl;

    @Column(name = "pdf_url")
    private String pdfUrl;

    @Column(name = "sinopse" , length = 2000)
    private String sinopse;

    @Enumerated(EnumType.STRING)
    @Column(name = "genero", nullable = false)
    private GeneroEnum genero;

    @Column(length = 1000)
    private String descricaoAutor;





}
