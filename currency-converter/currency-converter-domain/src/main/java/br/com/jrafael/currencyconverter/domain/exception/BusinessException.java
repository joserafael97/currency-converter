package br.com.jrafael.currencyconverter.domain.exception;

import br.com.jrafael.infrastructure.exception.GenericBusinessException;

public class BusinessException extends GenericBusinessException {
    public BusinessException(String mensagem) {
        super(mensagem);
    }
}
