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
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ContextConfiguration;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Import(RepositoryTestConfiguration.class)
@ContextConfiguration(classes = {CurrencyTransactionEntityRepository.class})
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class CurrencyTransactionRepositoryTest {

    @Autowired
    private CurrencyTransactionEntityRepository repository;

    private CurrencyTransactionEntity currencyTransactionEntity;
    private CurrencyTransactionEntity currencyTransactionEntity2;

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

        this.currencyTransactionEntity2 = CurrencyTransactionEntity.builder()
                .currencyOrigin(FinanceCoins.EUR)
                .destinationCurrency(FinanceCoins.JPY)
                .date(LocalDateTime.now())
                .conversionRate(BigDecimal.ONE)
                .userId("2849384938493894832dmsmdksjds2222")
                .sourceValue(new BigDecimal("10"))
                .id("djfkejfkejfkejfkejkfjek2")
                .build();

    }

    @Test
    public void saveTest() {
        this.currencyTransactionEntity = this.repository.save(this.currencyTransactionEntity);
        Optional<CurrencyTransactionEntity> result = this.repository.findById(this.currencyTransactionEntity.getId());
        assertTrue(result.isPresent());
        assertEquals(result.get().getCurrencyOrigin(), result.get().getCurrencyOrigin());
        assertEquals(result.get().getDestinationCurrency(), result.get().getDestinationCurrency());
        assertEquals(result.get().getSourceValue(), result.get().getSourceValue());
    }


    @Test
    public void getAllTest(){
        this.currencyTransactionEntity = this.repository.save(this.currencyTransactionEntity);
        this.currencyTransactionEntity2 = this.repository.save(this.currencyTransactionEntity2);
        Optional<CurrencyTransactionEntity> result = this.repository.findById(currencyTransactionEntity.getId());
        Optional<CurrencyTransactionEntity> result2 = this.repository.findById(currencyTransactionEntity2.getId());
        assertTrue(result.isPresent());
        assertTrue(result2.isPresent());
        Page<CurrencyTransactionEntity> entities = this.repository.findAll(PageRequest.of(0, 10));
        assertTrue(!entities.isEmpty());
        assertTrue(entities.getContent().size() == 2);
    }

    @Test
    public void getByUserIdTest(){
        this.currencyTransactionEntity = this.repository.save(this.currencyTransactionEntity);
        this.currencyTransactionEntity2 = this.repository.save(this.currencyTransactionEntity2);
        Optional<CurrencyTransactionEntity> result = this.repository.findById(currencyTransactionEntity.getId());
        Optional<CurrencyTransactionEntity> result2 = this.repository.findById(currencyTransactionEntity2.getId());
        assertTrue(result.isPresent());
        assertTrue(result2.isPresent());
        Page<CurrencyTransactionEntity> entities = this.repository.findByUserId("2849384938493894832dmsmdksjds2222",  PageRequest.of(0, 10));
        assertTrue(!entities.isEmpty());
        assertTrue(entities.getContent().size() == 1);
    }

}
