package br.com.jrafael.currencyconverter.domain.util.validation;

import br.com.jrafael.currencyconverter.domain.exception.BusinessValidationException;
import br.com.jrafael.currencyconverter.domain.model.CurrencyTransaction;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class CurrencyTransactionValidate {

    protected final Logger LOGGER = LogManager.getLogger(CurrencyTransactionValidate.class);

    protected CurrencyTransactionValidate nextCurrencyTransactionValidate;

    protected CurrencyTransactionValidate(CurrencyTransactionValidate nextCurrencyTransactionValidate){
        this.nextCurrencyTransactionValidate = nextCurrencyTransactionValidate;
    }

    public abstract void performValidation(CurrencyTransaction model) throws BusinessValidationException;

    public void validate(CurrencyTransaction model) throws BusinessValidationException {
        if (model != null) {
            performValidation(model);
            if (this.nextCurrencyTransactionValidate != null) {
                this.nextCurrencyTransactionValidate.performValidation(model);
            }
        }else {
            LOGGER.info("Validation fail: Information provided is invalid or incomplete.");
            throw new BusinessValidationException("Information provided is invalid or incomplete.");
        }
    }

}
