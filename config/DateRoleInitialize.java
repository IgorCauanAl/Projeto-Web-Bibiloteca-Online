package br.ifba.edu.BancoDeDadosIfba.biblioteca_orm.config;

import br.ifba.edu.BancoDeDadosIfba.biblioteca_orm.entities.Role;
import br.ifba.edu.BancoDeDadosIfba.biblioteca_orm.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration

//Inicializa USER E DATE automaticamente no h2 ao abrir pela primeira vez
public class DateRoleInitialize {
    @Bean
    public CommandLineRunner initializeRoles(RoleRepository roleRepository){
        return data -> {
            if(roleRepository.findByName("ADMIN").isEmpty()){
                roleRepository.save(new Role("ADMIN"));
            }

             if(roleRepository.findByName("USER").isEmpty()){
                roleRepository.save(new Role("USER"));
            }

        };
    }

}
