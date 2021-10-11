package br.com.jrafael.currencyconverter.domain.dto;

import java.math.BigDecimal;
import java.util.Map;

public class CurrencyTransactionRateDto {

    private String timestamp;
    private String base;
    private String date;
    private Map<String, BigDecimal> rates;

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Map<String, BigDecimal> getRates() {
        return rates;
    }

    public void setRates(Map<String, BigDecimal> rates) {
        this.rates = rates;
    }

    @Override
    public String toString() {
        return "CurrencyTransactionDto{" +
                "timestamp='" + timestamp + '\'' +
                ", base='" + base + '\'' +
                ", date='" + date + '\'' +
                ", conversionRate=" + rates +
                '}';
    }
}
