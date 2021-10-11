package br.com.jrafael.infrastructure.exception;

public class GenericBusinessException extends Exception {
    public GenericBusinessException(String mensagem) {
        super(mensagem);
    }
}
