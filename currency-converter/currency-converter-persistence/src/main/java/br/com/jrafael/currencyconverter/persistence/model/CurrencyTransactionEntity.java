package br.com.jrafael.currencyconverter.persistence.model;

import br.com.jrafael.currencyconverter.domain.constants.FinanceCoins;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import static javax.persistence.GenerationType.IDENTITY;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "CURRENCYTRANSACTION")
@Data
public class CurrencyTransactionEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String userId;
    private FinanceCoins currencyOrigin;
    private BigDecimal sourceValue;
    private FinanceCoins destinationCurrency;
//    private Map<String, BigDecimal> conversionRate;
    private LocalDateTime date;
}
