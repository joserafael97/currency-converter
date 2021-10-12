package br.com.jrafael.currencyconverter.api.dto;

import br.com.jrafael.currencyconverter.domain.constants.FinanceCoins;
import br.com.jrafael.currencyconverter.domain.model.CurrencyTransaction;
import br.com.jrafael.infrastructure.dto.BaseDto;
import lombok.*;

import java.math.BigDecimal;

@Data
@Getter
@Builder
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class CurrencyTransactionFormDto extends BaseDto<CurrencyTransaction> {

    private String userId;
    private BigDecimal sourceValue;
    private FinanceCoins currencyDestination;

    public CurrencyTransactionFormDto(CurrencyTransaction currencyTransaction) {
        this.userId = currencyTransaction.getUserId();
        this.sourceValue = currencyTransaction.getSourceValue();
        this.currencyDestination = currencyTransaction.getCurrencyDestination();
    }

    @Override
    public CurrencyTransaction convert() {
        CurrencyTransaction obj = new CurrencyTransaction();
        obj.setUserId(this.userId);
        obj.setCurrencyDestination(this.currencyDestination);
        obj.setSourceValue(this.sourceValue);
        return obj;
    }
}
