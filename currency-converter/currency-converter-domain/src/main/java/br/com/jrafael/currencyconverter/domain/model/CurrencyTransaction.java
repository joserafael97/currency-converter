package br.com.jrafael.currencyconverter.domain.model;

import br.com.jrafael.currencyconverter.domain.constants.FinanceCoins;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class CurrencyTransaction {

    private String id;
    private String userId;
    private FinanceCoins currencyOrigin = FinanceCoins.EUR;
    private BigDecimal sourceValue;
    private BigDecimal convertedValue;
    private FinanceCoins currencyDestination;
    private BigDecimal conversionRate;
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

    public FinanceCoins getCurrencyDestination() {
        return currencyDestination;
    }

    public void setCurrencyDestination(FinanceCoins currencyDestination) {
        this.currencyDestination = currencyDestination;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public void setDate(String timestamp) {
        this.date = LocalDateTime.ofInstant(Instant.ofEpochMilli(Long.parseLong(timestamp) * 1000), ZoneId.of("UTC"));
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public BigDecimal getConversionRate() {
        return conversionRate;
    }

    public void setConversionRate(BigDecimal conversionRate) {
        this.conversionRate = conversionRate;
        this.getConvertedValue();
    }

    public BigDecimal getConvertedValue() {
        this.convertedValue = BigDecimal.ZERO;
        if (this.getSourceValue() != null && this.getConversionRate() != null) {
            this.convertedValue = (this.getSourceValue().multiply(this.getConversionRate()))
                    .setScale(2, RoundingMode.HALF_EVEN);
        }
        return this.convertedValue;
    }

    @Override
    public String toString() {
        return "CurrencyTransaction{" +
                "id=" + id +
                ", userId='" + userId + '\'' +
                ", currencyOrigin=" + currencyOrigin +
                ", sourceValue=" + sourceValue +
                ", convertedValue=" + convertedValue +
                ", destinationCurrency=" + currencyDestination +
                ", conversionRate=" + conversionRate +
                ", date=" + date +
                '}';
    }
}
