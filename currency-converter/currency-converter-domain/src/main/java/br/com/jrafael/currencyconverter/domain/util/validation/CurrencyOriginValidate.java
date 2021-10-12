package br.com.jrafael.currencyconverter.domain.util.validation;

import br.com.jrafael.currencyconverter.domain.constants.FinanceCoins;
import br.com.jrafael.currencyconverter.domain.exception.BusinessValidationException;
import br.com.jrafael.currencyconverter.domain.model.CurrencyTransaction;

public class CurrencyOriginValidate extends CurrencyTransactionValidate {

    public CurrencyOriginValidate(CurrencyTransactionValidate currencyTransactionValidate) {
        super(currencyTransactionValidate);
    }

    @Override
    public void performValidation(CurrencyTransaction model) throws BusinessValidationException {
        if (model.getCurrencyOrigin() == null){
            LOGGER.info("Validation fail: currencyOrigin value is required.");
            throw new BusinessValidationException("currencyOrigin value is required");
        }else{
            FinanceCoins.getByCode(model.getCurrencyOrigin().getEnumAbbreviation().toString());
            if (this.currencyTransactionValidate != null) {
                this.currencyTransactionValidate.performValidation(model);
            }
        }
    }
}
