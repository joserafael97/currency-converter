package br.com.jrafael.currencyconverter.persistence.model;

import br.com.jrafael.currencyconverter.domain.constants.FinanceCoins;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Entity
@Table(name = "CURRENCYTRANSACTION")
@Data
public class CurrencyTransactionEntity {

    @Id
    @GenericGenerator(name = "UUIDGenerator", strategy = "uuid2")
    @GeneratedValue(generator = "UUIDGenerator")
    @Column(updatable = false, nullable = false, unique = true)
    private String id;

    private String userId;

    private FinanceCoins currencyOrigin;

    @Column(precision = 16, scale = 6)
    private BigDecimal sourceValue;

    private FinanceCoins destinationCurrency;

    @Column(precision = 16, scale = 6)
    private BigDecimal conversionRate;

    private LocalDateTime date;
}
