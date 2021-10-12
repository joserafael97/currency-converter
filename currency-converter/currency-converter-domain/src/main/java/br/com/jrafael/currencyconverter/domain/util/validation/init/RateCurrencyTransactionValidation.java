package br.com.jrafael.currencyconverter.domain.util.validation.init;

import br.com.jrafael.currencyconverter.domain.dto.CurrencyTransactionRateDto;
import br.com.jrafael.currencyconverter.domain.exception.BusinessValidationException;
import br.com.jrafael.currencyconverter.domain.util.validation.RateValidate;

public class RateCurrencyTransactionValidation {
    public static void validate(CurrencyTransactionRateDto model) throws BusinessValidationException {
        (new RateValidate(null))
                .validate(model);
    }
}
