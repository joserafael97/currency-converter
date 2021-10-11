package br.com.jrafael.currencyconverter.domain.util.validation;

import br.com.jrafael.currencyconverter.domain.exception.BusinessValidationException;
import br.com.jrafael.currencyconverter.domain.model.CurrencyTransaction;

public class IdUserValidate extends CurrencyTransactionValidate{

    public IdUserValidate(CurrencyTransactionValidate currencyTransactionValidate) {
        super(currencyTransactionValidate);
    }

    @Override
    public void performValidation(CurrencyTransaction model) throws BusinessValidationException {
        if(model.getUserId() == null){
            throw new BusinessValidationException("UserId value is required");
        }
    }
}
