package br.ifba.edu.BancoDeDadosIfba.biblioteca_orm.controller;

import br.ifba.edu.BancoDeDadosIfba.biblioteca_orm.DTO.UsuarioCadastroDTO;
import br.ifba.edu.BancoDeDadosIfba.biblioteca_orm.DTO.UsuarioRespostaDTO;
import br.ifba.edu.BancoDeDadosIfba.biblioteca_orm.excecao.EmailJaExisteException;
import br.ifba.edu.BancoDeDadosIfba.biblioteca_orm.service.CadastroService;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cadastro")
public class CadastroController {

    private final CadastroService cadastroService;

    public CadastroController(CadastroService cadastroService){
        this.cadastroService = cadastroService;
    }

    @PostMapping
    public ResponseEntity<?> insert(@Valid @RequestBody UsuarioCadastroDTO dto){
        //Se o email não for igual
        try{
            UsuarioRespostaDTO usuarioResposta = cadastroService.insert(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(usuarioResposta);
        //Exceção se o email for igual -- erro 409 aparece no console
        }catch(EmailJaExisteException e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }

    }
}
