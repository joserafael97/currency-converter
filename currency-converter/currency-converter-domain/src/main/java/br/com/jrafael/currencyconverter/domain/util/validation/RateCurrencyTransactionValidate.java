package br.com.jrafael.currencyconverter.domain.util.validation;

import br.com.jrafael.currencyconverter.domain.dto.CurrencyTransactionRateDto;
import br.com.jrafael.currencyconverter.domain.exception.BusinessValidationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class RateCurrencyTransactionValidate {
    protected final Logger LOGGER = LogManager.getLogger(RateCurrencyTransactionValidate.class);

    protected RateCurrencyTransactionValidate nextRateCurrencyTransactionValidate;

    protected RateCurrencyTransactionValidate(RateCurrencyTransactionValidate nextRateCurrencyTransactionValidate) {
        this.nextRateCurrencyTransactionValidate = nextRateCurrencyTransactionValidate;
    }

    public abstract void performValidation(CurrencyTransactionRateDto model) throws BusinessValidationException;

    public void validate(CurrencyTransactionRateDto model) throws BusinessValidationException {
        if (model != null) {
            performValidation(model);
            if (this.nextRateCurrencyTransactionValidate != null) {
                this.nextRateCurrencyTransactionValidate.performValidation(model);
            }
        } else {
            LOGGER.info("Validation fail: Information provided is invalid or incomplete.");
            throw new BusinessValidationException("Information provided is invalid or incomplete.");
        }
    }
}
