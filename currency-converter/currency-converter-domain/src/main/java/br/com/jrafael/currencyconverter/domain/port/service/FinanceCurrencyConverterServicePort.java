package br.com.jrafael.currencyconverter.domain.port.service;

import br.com.jrafael.currencyconverter.domain.constants.FinanceCoins;

import java.math.BigDecimal;

public interface FinanceCurrencyConverterServicePort {

    BigDecimal getCurrencyTransactionRate(FinanceCoins currencyOrigin, FinanceCoins destinationCurrency, String accessToken);
}
