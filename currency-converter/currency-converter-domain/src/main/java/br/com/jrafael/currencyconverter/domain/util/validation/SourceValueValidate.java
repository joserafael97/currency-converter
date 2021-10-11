package br.com.jrafael.currencyconverter.domain.util.validation;

import br.com.jrafael.currencyconverter.domain.exception.BusinessValidationException;
import br.com.jrafael.currencyconverter.domain.model.CurrencyTransaction;

import java.math.BigDecimal;

public class SourceValueValidate extends CurrencyTransactionValidate{

    public SourceValueValidate(CurrencyTransactionValidate currencyTransactionValidate) {
        super(currencyTransactionValidate);
    }

    @Override
    public void performValidation(CurrencyTransaction model) throws BusinessValidationException {
        if(model.getSourceValue() != null){
            int compare = model.getSourceValue().compareTo(BigDecimal.ZERO);
            if(compare <= 0){
                throw new BusinessValidationException("SourceValue must be greater than zero.");
            }
        }else {
            throw new BusinessValidationException("SourceValue value is required");
        }
    }
}
