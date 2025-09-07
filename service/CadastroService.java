package br.ifba.edu.BancoDeDadosIfba.biblioteca_orm.service;

import br.ifba.edu.BancoDeDadosIfba.biblioteca_orm.DTO.UsuarioCadastroDTO;
import br.ifba.edu.BancoDeDadosIfba.biblioteca_orm.DTO.UsuarioRespostaDTO;
import br.ifba.edu.BancoDeDadosIfba.biblioteca_orm.entities.Role;
import br.ifba.edu.BancoDeDadosIfba.biblioteca_orm.entities.Usuario;
import br.ifba.edu.BancoDeDadosIfba.biblioteca_orm.excecao.EmailJaExisteException;
import br.ifba.edu.BancoDeDadosIfba.biblioteca_orm.repository.RoleRepository;
import br.ifba.edu.BancoDeDadosIfba.biblioteca_orm.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CadastroService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private RoleRepository roleRepository;

    private static final String ADMIN_EMAIL = "admin@hotmail.com";
    private static final String ADMIN_SENHA = "admin12345";


    //Recebe as informações do cadastro do DTO , insere e enviar para o repository
    public UsuarioRespostaDTO insert (UsuarioCadastroDTO dto){

        //Verificar se email já existe
        if(usuarioRepository.existsByEmail(dto.getEmail())){
            throw new EmailJaExisteException("Email já cadastrado");
        }

        //Verificar se a conta é ADMIN
        String roleName;
        if(dto.getEmail().equals(ADMIN_EMAIL) && dto.getSenha().equals(ADMIN_SENHA)){
            roleName = "ADMIN";
        }else{
            roleName = "USER";
        }

        //Buscar a role no banco
        Role role = roleRepository.findByName(roleName)
                .orElseThrow(() -> new RuntimeException("Role" +roleName + " não foi encontrada"));

        Usuario entity = new Usuario();
        entity.setNome(dto.getNome());
        entity.setEmail((dto.getEmail()));
        entity.setSenha(dto.getSenha());
        entity.getRoles().add(role);



        //Os dados do DTO enviados pelo usuario são salvos no banco
        entity = usuarioRepository.save(entity);

        //Retornar os dados cadastrados do usuário
        return new UsuarioRespostaDTO(entity.getNome(), entity.getEmail());
    }

}
