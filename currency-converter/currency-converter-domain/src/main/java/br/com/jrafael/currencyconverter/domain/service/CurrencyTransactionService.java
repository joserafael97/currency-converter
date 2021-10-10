package br.com.jrafael.currencyconverter.domain.service;

import br.com.jrafael.currencyconverter.domain.model.CurrencyTransaction;
import br.com.jrafael.currencyconverter.domain.port.persistence.CurrencyTransactionPersistencePort;
import br.com.jrafael.currencyconverter.domain.port.service.FinanceCurrencyConverterServicePort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;

public class CurrencyTransactionService {

    private final CurrencyTransactionPersistencePort currencyTransactionPersistencePort;
    private final FinanceCurrencyConverterServicePort financeCurrencyConverterServicePort;

    public CurrencyTransactionService(final CurrencyTransactionPersistencePort currencyTransactionPersistencePort,
                               final FinanceCurrencyConverterServicePort financeCurrencyConverterServicePort){
        this.currencyTransactionPersistencePort = currencyTransactionPersistencePort;
        this.financeCurrencyConverterServicePort = financeCurrencyConverterServicePort;
    }

    public CurrencyTransaction create(CurrencyTransaction model){
        //access api and save/return result
        BigDecimal rate = this.financeCurrencyConverterServicePort.getCurrencyTransactionRate(
                model.getCurrencyOrigin(),
                model.getDestinationCurrency(),
                model.getUserId());
        //conversion;;;
        model = this.currencyTransactionPersistencePort.create(model);
        return model;
    }

    public Page<CurrencyTransaction> getAll(Pageable page){
        return this.currencyTransactionPersistencePort.getAll(page);
    }
}
