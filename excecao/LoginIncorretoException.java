package br.ifba.edu.BancoDeDadosIfba.biblioteca_orm.excecao;

public class LoginIncorretoException extends RuntimeException{

    public LoginIncorretoException(){
        super("Login ou senha incorreto!");
    }

    public LoginIncorretoException(String mensagem){
        super(mensagem);
    }

}
