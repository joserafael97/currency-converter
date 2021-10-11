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
        this.validate(model);
        if (model.getCurrencyOrigin() == null){
            throw new BusinessValidationException("currencyOrigin value is required");
        }else{
            FinanceCoins.getByCode(model.getCurrencyOrigin().getEnumAbbreviation().toString());
        }
    }
}
