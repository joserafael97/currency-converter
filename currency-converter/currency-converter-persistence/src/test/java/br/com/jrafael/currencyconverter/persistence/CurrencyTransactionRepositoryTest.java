package br.com.jrafael.currencyconverter.persistence;

import br.com.jrafael.currencyconverter.domain.constants.FinanceCoins;
import br.com.jrafael.currencyconverter.persistence.configuration.RepositoryTestConfiguration;
import br.com.jrafael.currencyconverter.persistence.model.CurrencyTransactionEntity;
import br.com.jrafael.currencyconverter.persistence.repository.CurrencyTransactionEntityRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Import(RepositoryTestConfiguration.class)
@ContextConfiguration(classes = {CurrencyTransactionEntityRepository.class})
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class CurrencyTransactionRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CurrencyTransactionEntityRepository repository;

    private CurrencyTransactionEntity currencyTransactionEntity;

    @BeforeEach
    public void setUp() {
        this.currencyTransactionEntity = CurrencyTransactionEntity.builder()
                .currencyOrigin(FinanceCoins.EUR)
                .destinationCurrency(FinanceCoins.BRL)
                .date(LocalDateTime.now())
                .conversionRate(BigDecimal.ONE)
                .userId("2849384938493894832dmsmdksjds")
                .sourceValue(new BigDecimal("9"))
                .id("djfkejfkejfkejfkejkfjek")
                .build();
    }

    @Test
    public void saveTest() {
        this.currencyTransactionEntity = this.repository.save(this.currencyTransactionEntity);
        Optional<CurrencyTransactionEntity> result = this.repository.findById(this.currencyTransactionEntity.getId());
        assertTrue(result.isPresent());
        assertTrue(result.get().getCurrencyOrigin().equals(result.get().getCurrencyOrigin()));
        assertTrue(result.get().getDestinationCurrency().equals(result.get().getDestinationCurrency()));
        assertTrue(result.get().getSourceValue().equals(result.get().getSourceValue()));
    }

}
