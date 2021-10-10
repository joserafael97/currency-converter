package br.com.jrafael.currencyconverter.domain.exception;

public class BusinessException extends Exception {
    public BusinessException(String mensagem) {
        super(mensagem);
    }
}
