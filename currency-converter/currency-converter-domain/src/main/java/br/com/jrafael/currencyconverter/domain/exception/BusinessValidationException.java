package br.com.jrafael.currencyconverter.domain.exception;

public class BusinessValidationException extends BusinessException{
    public BusinessValidationException(String mensagem) {
        super(mensagem);
    }
}
