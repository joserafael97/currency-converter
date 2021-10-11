package br.com.jrafael.currencyconverter.domain.util.validation.init;

import br.com.jrafael.currencyconverter.domain.exception.BusinessValidationException;
import br.com.jrafael.currencyconverter.domain.model.CurrencyTransaction;
import br.com.jrafael.currencyconverter.domain.util.validation.*;

public class CurrencyTransactionAtributesValidation {

    public static void validate(CurrencyTransaction model) throws BusinessValidationException {
        (new IdUserValidate(
                new SourceValueValidate(
                new CurrencyOriginValidate(
                        new DestinationCurrencyValidate(
                                new SameOriginAndDestionationCurrencyValidate(null))))))
                .validate(model);
    }
}
