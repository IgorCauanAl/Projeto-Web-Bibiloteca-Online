package br.ifba.edu.BancoDeDadosIfba.biblioteca_orm.excecao;

public class LivroDuplicadoException extends RuntimeException {
    public LivroDuplicadoException(String message){
        super(message);
    }

}