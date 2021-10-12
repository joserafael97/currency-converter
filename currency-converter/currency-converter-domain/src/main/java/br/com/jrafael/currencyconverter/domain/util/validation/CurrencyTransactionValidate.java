package br.com.jrafael.currencyconverter.domain.util.validation;

import br.com.jrafael.currencyconverter.domain.exception.BusinessValidationException;
import br.com.jrafael.currencyconverter.domain.model.CurrencyTransaction;

import java.util.logging.Logger;

public abstract class CurrencyTransactionValidate {

    protected final Logger LOGGER = Logger.getLogger(this.getClass().getName());


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
            LOGGER.info("Validation fail: Information provided is invalid or incomplete.");
            throw new BusinessValidationException("Information provided is invalid or incomplete.");
        }
    }

}
