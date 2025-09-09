package br.ifba.edu.BancoDeDadosIfba.biblioteca_orm.DTO;

import br.ifba.edu.BancoDeDadosIfba.biblioteca_orm.model.GeneroEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LivroDTO{
        private Long id;

        @NotBlank(message = "O título é obrigatório")
        private String nome;

        @NotBlank(message = "O autor é obrigatório")
        private String autor;

        @NotNull(message = "Campo ano de Publicação obrigatorio!")
        private Integer anoPublicacao;

        private String capaUrl;

        private String pdfUrl;

        @Size(max = 2000)
        @NotBlank(message = "Campo Sinopse obrigatorio!")
        private String sinopse;

        private GeneroEnum genero;

        @Size(max = 1000)
        @NotBlank(message = "Campo descrição do autor obrigatorio!")
        private String descricaoAutor;


 }

