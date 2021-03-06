package br.com.jrafael.currencyconverter.domain.util.validation;

import br.com.jrafael.currencyconverter.domain.constants.FinanceCoins;
import br.com.jrafael.currencyconverter.domain.exception.BusinessValidationException;
import br.com.jrafael.currencyconverter.domain.model.CurrencyTransaction;

public class DestinationCurrencyValidate extends CurrencyTransactionValidate{

    public DestinationCurrencyValidate(CurrencyTransactionValidate currencyTransactionValidate) {
        super(currencyTransactionValidate);
    }

    @Override
    public void performValidation(CurrencyTransaction model) throws BusinessValidationException {
        if (model.getCurrencyOrigin() == null){
            LOGGER.info("Validation fail: DestinationCurrency value is required.");
            throw new BusinessValidationException("DestinationCurrency value is required");
        }else{
            FinanceCoins.getByCode(model.getCurrencyOrigin().getEnumAbbreviation());
            if (this.nextCurrencyTransactionValidate != null) {
                this.nextCurrencyTransactionValidate.performValidation(model);
            }
        }
    }
}
