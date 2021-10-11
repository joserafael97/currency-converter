package br.com.jrafael.currencyconverter.api.dto;

import br.com.jrafael.currencyconverter.domain.constants.FinanceCoins;
import br.com.jrafael.currencyconverter.domain.model.CurrencyTransaction;
import br.com.jrafael.infrastructure.dto.BaseDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Getter
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class CurrencyTransactionFormDto extends BaseDto<CurrencyTransaction> {

    private String userId;
    private FinanceCoins currencyOrigin;
    private BigDecimal sourceValue;
    private FinanceCoins destinationCurrency;

    public CurrencyTransactionFormDto(CurrencyTransaction currencyTransaction) {
        this.userId = currencyTransaction.getUserId();
        this.currencyOrigin = currencyTransaction.getCurrencyOrigin();
        this.sourceValue = currencyTransaction.getSourceValue();
        this.destinationCurrency = currencyTransaction.getDestinationCurrency();
    }

    @Override
    public CurrencyTransaction convert() {
        CurrencyTransaction obj = new CurrencyTransaction();
        obj.setUserId(this.userId);
        obj.setCurrencyOrigin(this.currencyOrigin);
        obj.setDestinationCurrency(this.destinationCurrency);
        obj.setSourceValue(this.sourceValue);
        return obj;
    }
}
