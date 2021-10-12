package br.com.jrafael.currencyconverter.domain.util.validation;

import br.com.jrafael.currencyconverter.domain.dto.CurrencyTransactionRateDto;
import br.com.jrafael.currencyconverter.domain.exception.BusinessValidationException;

import java.math.BigDecimal;

public class RateValidate extends RateCurrencyTransactionValidate{

    public RateValidate(RateCurrencyTransactionValidate rateCurrencyTransactionValidate) {
        super(rateCurrencyTransactionValidate);
    }

    @Override
    public void performValidation(CurrencyTransactionRateDto model) throws BusinessValidationException {
        if(model.getRates() == null){
            throw new BusinessValidationException("Rates is required.");
        }else {
            for (BigDecimal rate : model.getRates().values()) {
                if (rate.compareTo(BigDecimal.ZERO) <= 0) {
                    throw new BusinessValidationException("ConversionRate must be greater than zero.");
                }
            }
        }
    }
}
