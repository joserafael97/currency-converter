package br.com.jrafael.currencyconverter.domain.util.validation;

import br.com.jrafael.currencyconverter.domain.exception.BusinessValidationException;
import br.com.jrafael.currencyconverter.domain.model.CurrencyTransaction;

public class SameOriginAndDestionationCurrencyValidate extends CurrencyTransactionValidate{

    public SameOriginAndDestionationCurrencyValidate(CurrencyTransactionValidate currencyTransactionValidate) {
        super(currencyTransactionValidate);
    }

    @Override
    public void performValidation(CurrencyTransaction model) throws BusinessValidationException {
        if(model.getCurrencyOrigin().equals(model.getDestinationCurrency())){
            throw new BusinessValidationException("DestinationCurrency and CurrencyOrigin cannot be the same.");
        }
    }
}