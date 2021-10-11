package br.com.jrafael.currencyconverter.api.dto;

import br.com.jrafael.currencyconverter.domain.constants.FinanceCoins;
import br.com.jrafael.currencyconverter.domain.model.CurrencyTransaction;
import br.com.jrafael.infrastructure.dto.BaseDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Getter
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class CurrencyTransactionDto extends BaseDto<CurrencyTransaction> {

    private String id;
    private String userId;
    private FinanceCoins currencyOrigin;
    private BigDecimal sourceValue;
    private FinanceCoins destinationCurrency;
    private LocalDateTime date;
    private BigDecimal conversionRate;
    private BigDecimal convertedValue;

    public CurrencyTransactionDto(CurrencyTransaction currencyTransaction){
        this.id = currencyTransaction.getId();
        this.userId = currencyTransaction.getUserId();
        this.currencyOrigin = currencyTransaction.getCurrencyOrigin();
        this.sourceValue = currencyTransaction.getSourceValue();
        this.destinationCurrency = currencyTransaction.getDestinationCurrency();
        this.conversionRate = currencyTransaction.getConversionRate();
        this.date = currencyTransaction.getDate();
        this.convertedValue = currencyTransaction.getConvertedValue();
    }

    @Override
    public CurrencyTransaction convert() {
        CurrencyTransaction obj = new CurrencyTransaction();
        obj.setId(this.id);
        obj.setUserId(this.userId);
        obj.setCurrencyOrigin(this.currencyOrigin);
        obj.setDestinationCurrency(this.destinationCurrency);
        obj.setDate(this.date);
        obj.setSourceValue(this.sourceValue);
        obj.setConversionRate(this.conversionRate);
        return obj;
    }

}
