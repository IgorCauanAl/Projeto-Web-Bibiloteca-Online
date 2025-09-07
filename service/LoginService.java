package br.ifba.edu.BancoDeDadosIfba.biblioteca_orm.service;

import br.ifba.edu.BancoDeDadosIfba.biblioteca_orm.DTO.LoginRequest;
import br.ifba.edu.BancoDeDadosIfba.biblioteca_orm.entities.Usuario;
import br.ifba.edu.BancoDeDadosIfba.biblioteca_orm.excecao.LoginIncorretoException;
import br.ifba.edu.BancoDeDadosIfba.biblioteca_orm.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    @Autowired
    private UsuarioRepository usuarioRepository;




    //Autenticação do usuário
    public Usuario authenticate (LoginRequest request){

        Usuario usuario = usuarioRepository.findByEmail(request.getEmail())
                .filter(u -> u.getSenha().equals(request.getSenha()))
                .orElseThrow(LoginIncorretoException::new);


        //Verificar na autenticação se usuário é admin ou user.
        boolean isAdmin = usuario.getRoles().stream()
                .anyMatch(r -> r.getName().equals("ADMIN"));

        if(isAdmin){
            System.out.println("Admin logado!");
        }else{
            System.out.println("User logado!");
        }

        return usuario;

    }




}
