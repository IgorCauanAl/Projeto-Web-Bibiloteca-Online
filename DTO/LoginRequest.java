package br.ifba.edu.BancoDeDadosIfba.biblioteca_orm.DTO;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {
    private String email;
    private String senha;



}
