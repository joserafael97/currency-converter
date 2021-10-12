package br.com.jrafael.currencyconverter.domain.util.validation;

import br.com.jrafael.currencyconverter.domain.dto.CurrencyTransactionRateDto;
import br.com.jrafael.currencyconverter.domain.exception.BusinessValidationException;

import java.util.logging.Logger;

public abstract class RateCurrencyTransactionValidate {
    protected final Logger LOGGER = Logger.getLogger(this.getClass().getName());

    protected RateCurrencyTransactionValidate rateCurrencyTransactionValidate;

    public RateCurrencyTransactionValidate(RateCurrencyTransactionValidate rateCurrencyTransactionValidate) {
        this.rateCurrencyTransactionValidate = rateCurrencyTransactionValidate;
    }

    public abstract void performValidation(CurrencyTransactionRateDto model) throws BusinessValidationException;

    public void validate(CurrencyTransactionRateDto model) throws BusinessValidationException {
        if (model != null) {
            performValidation(model);
            if (this.rateCurrencyTransactionValidate != null) {
                this.rateCurrencyTransactionValidate.performValidation(model);
            }
        } else {
            LOGGER.info("Validation fail: Information provided is invalid or incomplete.");
            throw new BusinessValidationException("Information provided is invalid or incomplete.");
        }
    }
}
