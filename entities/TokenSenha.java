package br.ifba.edu.BancoDeDadosIfba.biblioteca_orm.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
@Entity
public class TokenSenha {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String codigo;

    private LocalDateTime expiracao;

    @OneToOne
    private Usuario usuario;

}
