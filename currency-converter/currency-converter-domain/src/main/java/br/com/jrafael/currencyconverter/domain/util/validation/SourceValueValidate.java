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
                LOGGER.info("Validation fail: SourceValue must be greater than zero.");
                throw new BusinessValidationException("SourceValue must be greater than zero.");
            } else if (this.nextCurrencyTransactionValidate != null) {
                this.nextCurrencyTransactionValidate.performValidation(model);
            }
        }else {
            LOGGER.info("Validation fail: SourceValue value is required.");
            throw new BusinessValidationException("SourceValue value is required");
        }
    }
}
