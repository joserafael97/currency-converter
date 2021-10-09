package br.com.jrafael.currencyconverter.domain.model;

import br.com.jrafael.currencyconverter.domain.constants.FinanceCoins;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

public class CurrencyTransaction {
    private String userId;
    private FinanceCoins currencyOrigin;
    private BigDecimal sourceValue;
    private FinanceCoins destinationCurrency;
    private Map<String, BigDecimal> conversionRate;
    private LocalDateTime date;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public FinanceCoins getCurrencyOrigin() {
        return currencyOrigin;
    }

    public void setCurrencyOrigin(FinanceCoins currencyOrigin) {
        this.currencyOrigin = currencyOrigin;
    }

    public BigDecimal getSourceValue() {
        return sourceValue;
    }

    public void setSourceValue(BigDecimal sourceValue) {
        this.sourceValue = sourceValue;
    }

    public FinanceCoins getDestinationCurrency() {
        return destinationCurrency;
    }

    public void setDestinationCurrency(FinanceCoins destinationCurrency) {
        this.destinationCurrency = destinationCurrency;
    }

    public Map<String, BigDecimal> getConversionRate() {
        return conversionRate;
    }

    public void setConversionRate(Map<String, BigDecimal> conversionRate) {
        this.conversionRate = conversionRate;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}
