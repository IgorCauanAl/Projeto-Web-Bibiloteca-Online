package br.ifba.edu.BancoDeDadosIfba.biblioteca_orm.service;

import br.ifba.edu.BancoDeDadosIfba.biblioteca_orm.DTO.EmailRecuperacaoDTO;
import br.ifba.edu.BancoDeDadosIfba.biblioteca_orm.DTO.SenhaNovaDTO;
import br.ifba.edu.BancoDeDadosIfba.biblioteca_orm.entities.TokenSenha;
import br.ifba.edu.BancoDeDadosIfba.biblioteca_orm.entities.Usuario;
import br.ifba.edu.BancoDeDadosIfba.biblioteca_orm.repository.TokenSenhaRepository;
import br.ifba.edu.BancoDeDadosIfba.biblioteca_orm.repository.UsuarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class RecuperarSenhaService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TokenSenhaRepository tokenSenhaRepository;

    private void enviarCodigo (EmailRecuperacaoDTO emailDTO){
        //Verificar se o email existe
        Optional<Usuario> usuario = usuarioRepository.findByEmail(emailDTO.getEmail());

        //Extraindo usuario de optional para passar no set usuario
        Usuario u = usuario.get();

        //Criar o código de 6 digitos para ser enviado
        String codigo = String.format("%06d", new Random().nextInt(100000));

        //Atribuir o codigo ao atributo codigo e para o usuario da tabela token
        TokenSenha tokenSenha = new TokenSenha();
        tokenSenha.setCodigo(codigo);
        tokenSenha.setUsuario(u);
        tokenSenha.setExpiracao(LocalDateTime.now().plusMinutes(15));

        tokenSenhaRepository.save(tokenSenha);



        }



    private void recuperarSenha(SenhaNovaDTO senhaNovaDTO){

    }

}
