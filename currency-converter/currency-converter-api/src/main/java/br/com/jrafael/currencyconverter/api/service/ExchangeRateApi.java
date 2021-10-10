package br.com.jrafael.currencyconverter.api.service;

import br.com.jrafael.currencyconverter.domain.constants.FinanceCoins;
import br.com.jrafael.currencyconverter.domain.port.service.FinanceCurrencyConverterServicePort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class ExchangeRateApi implements FinanceCurrencyConverterServicePort {

    @Override
    public BigDecimal getCurrencyTransactionRate(FinanceCoins currencyOrigin, FinanceCoins destinationCurrency, String accessToken) {
        return null;
    }
}
