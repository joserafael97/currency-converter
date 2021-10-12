package br.com.jrafael.currencyconverter.domain.util.validation;

import br.com.jrafael.currencyconverter.domain.exception.BusinessValidationException;
import br.com.jrafael.currencyconverter.domain.model.CurrencyTransaction;

public abstract class CurrencyTransactionValidate {

    protected CurrencyTransactionValidate currencyTransactionValidate;

    public CurrencyTransactionValidate(CurrencyTransactionValidate currencyTransactionValidate){
        this.currencyTransactionValidate = currencyTransactionValidate;
    }

    public abstract void performValidation(CurrencyTransaction model) throws BusinessValidationException;

    public void validate(CurrencyTransaction model) throws BusinessValidationException {
        if (model != null) {
            performValidation(model);
            if (this.currencyTransactionValidate != null) {
                this.currencyTransactionValidate.performValidation(model);
            }
        }else {
            throw new BusinessValidationException("Information provided is invalid or incomplete.");
        }
    }

}
