package br.com.jrafael.currencyconverter.domain.service;

import br.com.jrafael.currencyconverter.domain.dto.CurrencyTransactionRateDto;
import br.com.jrafael.currencyconverter.domain.exception.BusinessValidationException;
import br.com.jrafael.currencyconverter.domain.model.CurrencyTransaction;
import br.com.jrafael.currencyconverter.domain.port.persistence.CurrencyTransactionPersistencePort;
import br.com.jrafael.currencyconverter.domain.port.service.FinanceCurrencyConverterServicePort;
import br.com.jrafael.currencyconverter.domain.util.validation.init.CurrencyTransactionAtributesValidation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class CurrencyTransactionService {

    private final CurrencyTransactionPersistencePort currencyTransactionPersistencePort;
    private final FinanceCurrencyConverterServicePort financeCurrencyConverterServicePort;

    public CurrencyTransactionService(final CurrencyTransactionPersistencePort currencyTransactionPersistencePort,
                               final FinanceCurrencyConverterServicePort financeCurrencyConverterServicePort){
        this.currencyTransactionPersistencePort = currencyTransactionPersistencePort;
        this.financeCurrencyConverterServicePort = financeCurrencyConverterServicePort;
    }

    public CurrencyTransaction create(CurrencyTransaction model) throws BusinessValidationException {
        CurrencyTransactionAtributesValidation.validate(model);
        String[] coins = new String[]{model.getCurrencyDestination().getEnumAbbreviation()};
        CurrencyTransactionRateDto rateDto = this.financeCurrencyConverterServicePort
                .getCurrencyTransactionRate(
                        model.getCurrencyOrigin().getEnumAbbreviation(),
                        coins,
                        model.getUserId()).getBody();
        model.setConversionRate(rateDto.getRates().get(model.getCurrencyDestination().getEnumAbbreviation()));
        model.setDate(rateDto.getTimestamp());
        model = this.currencyTransactionPersistencePort.create(model);
        return model;
    }

    public Page<CurrencyTransaction> getAll(Pageable page){
        return this.currencyTransactionPersistencePort.getAll(page);
    }
}
