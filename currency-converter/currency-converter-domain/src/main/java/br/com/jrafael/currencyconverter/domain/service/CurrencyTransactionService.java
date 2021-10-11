package br.com.jrafael.currencyconverter.domain.service;

import br.com.jrafael.currencyconverter.domain.dto.CurrencyTransactionRateDto;
import br.com.jrafael.currencyconverter.domain.model.CurrencyTransaction;
import br.com.jrafael.currencyconverter.domain.port.persistence.CurrencyTransactionPersistencePort;
import br.com.jrafael.currencyconverter.domain.port.service.FinanceCurrencyConverterServicePort;
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

    public CurrencyTransaction create(CurrencyTransaction model){
        String[] coins = new String[]{model.getDestinationCurrency().getEnumAbbreviation()};
        model.setUserId("b28899a2fb24d70952c5d8ee2aa2006e");
        CurrencyTransactionRateDto rateDto = this.financeCurrencyConverterServicePort
                .getCurrencyTransactionRate(
                        "EUR",
                        coins,
                        model.getUserId());
        model.setConversionRate(rateDto.getRates().get(model.getDestinationCurrency().getEnumAbbreviation()));
        model.setDate(rateDto.getTimestamp());
        if(model != null) {
            model = this.currencyTransactionPersistencePort.create(model);
        }
        return model;
    }

    public Page<CurrencyTransaction> getAll(Pageable page){
        return this.currencyTransactionPersistencePort.getAll(page);
    }
}
