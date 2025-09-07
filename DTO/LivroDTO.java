package br.ifba.edu.BancoDeDadosIfba.biblioteca_orm.DTO;

import br.ifba.edu.BancoDeDadosIfba.biblioteca_orm.model.GeneroEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LivroDTO{
        Long id;

        @NotBlank(message = "O título é obrigatório")
        String nome;

        @NotBlank(message = "O autor é obrigatório")
        String autor;

        @NotNull(message = "Campo ano de Publicação obrigatorio!")
        Integer anoPublicacao;

        String capaUrl;

        String pdfUrl;

        @Size(max = 2000)
        @NotBlank(message = "Campo Sinopse obrigatorio!")
        String sinopse;

        GeneroEnum genero;

        @Size(max = 1000)
        @NotBlank(message = "Campo descrição do autor obrigatorio!")
        String descricaoAutor;


 }

