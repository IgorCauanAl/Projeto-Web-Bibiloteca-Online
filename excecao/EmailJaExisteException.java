package br.ifba.edu.BancoDeDadosIfba.biblioteca_orm.excecao;

public class EmailJaExisteException extends RuntimeException {
    public EmailJaExisteException(String message) {
        super(message);
    }

}
