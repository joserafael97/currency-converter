package br.com.jrafael.currencyconverter.domain.service;

import br.com.jrafael.currencyconverter.domain.model.CurrencyTransaction;
import br.com.jrafael.currencyconverter.domain.port.service.FinanceCurrencyConverterServicePort;

import java.math.BigDecimal;
import java.util.List;

public class CurrencyTransactionService {

    private final CurrencyTransactionService currencyTransactionService;
    private final FinanceCurrencyConverterServicePort financeCurrencyConverterServicePort;

    CurrencyTransactionService(final CurrencyTransactionService currencyTransactionService,
                               final FinanceCurrencyConverterServicePort financeCurrencyConverterServicePort){
        this.currencyTransactionService = currencyTransactionService;
        this.financeCurrencyConverterServicePort = financeCurrencyConverterServicePort;
    }

    public CurrencyTransaction create(CurrencyTransaction model){
        //access api and save/return result
        BigDecimal rate = this.financeCurrencyConverterServicePort.getCurrencyTransactionRate(
                model.getCurrencyOrigin(),
                model.getDestinationCurrency(),
                model.getUserId());
        //conversion;;;
        model = this.currencyTransactionService.create(model);
        return model;
    }

    public List<CurrencyTransaction> getAll(){
        return this.currencyTransactionService.getAll();
    }
}
