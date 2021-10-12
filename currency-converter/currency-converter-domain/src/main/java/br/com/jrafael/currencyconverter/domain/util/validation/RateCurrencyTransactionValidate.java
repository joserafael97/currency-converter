package br.com.jrafael.currencyconverter.domain.util.validation;

import br.com.jrafael.currencyconverter.domain.dto.CurrencyTransactionRateDto;
import br.com.jrafael.currencyconverter.domain.exception.BusinessValidationException;

public abstract class RateCurrencyTransactionValidate {

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
            throw new BusinessValidationException("Information provided is invalid or incomplete.");
        }
    }
}
