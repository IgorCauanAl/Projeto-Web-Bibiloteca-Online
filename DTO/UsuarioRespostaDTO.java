package br.ifba.edu.BancoDeDadosIfba.biblioteca_orm.DTO;

import br.ifba.edu.BancoDeDadosIfba.biblioteca_orm.entities.Usuario;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UsuarioRespostaDTO {
    private String nome;
    private String email;



}
