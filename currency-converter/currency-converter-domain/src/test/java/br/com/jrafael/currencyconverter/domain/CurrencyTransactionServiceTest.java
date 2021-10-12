package br.com.jrafael.currencyconverter.domain;

import br.com.jrafael.currencyconverter.domain.constants.FinanceCoins;
import br.com.jrafael.currencyconverter.domain.dto.CurrencyTransactionRateDto;
import br.com.jrafael.currencyconverter.domain.model.CurrencyTransaction;
import br.com.jrafael.currencyconverter.domain.port.persistence.CurrencyTransactionPersistencePort;
import br.com.jrafael.currencyconverter.domain.port.service.FinanceCurrencyConverterServicePort;
import br.com.jrafael.currencyconverter.domain.service.CurrencyTransactionService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class CurrencyTransactionServiceTest {

    private CurrencyTransaction currencyTransaction;

    private CurrencyTransaction currencyTransactionSaved;

    private CurrencyTransactionRateDto currencyTransactionRateDto;

    @InjectMocks
    private CurrencyTransactionService service;

    @Mock
    private CurrencyTransactionPersistencePort currencyTransactionPersistencePort;

    @Mock
    private FinanceCurrencyConverterServicePort financeCurrencyConverterServicePort;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        this.service = new CurrencyTransactionService(this.currencyTransactionPersistencePort, this.financeCurrencyConverterServicePort);
        currencyTransaction = new CurrencyTransaction();
        currencyTransaction.setCurrencyDestination(FinanceCoins.JPY);
        currencyTransaction.setSourceValue(new BigDecimal("10.4"));
        currencyTransaction.setUserId("12345");

        currencyTransactionSaved = new CurrencyTransaction();
        currencyTransactionSaved.setCurrencyDestination(FinanceCoins.JPY);
        currencyTransactionSaved.setCurrencyOrigin(FinanceCoins.EUR);
        currencyTransactionSaved.setSourceValue(new BigDecimal("10"));
        currencyTransactionSaved.setUserId("12345");
        currencyTransactionSaved.setConversionRate(new BigDecimal(1));
        currencyTransactionSaved.setDate(LocalDateTime.now());

        currencyTransactionRateDto = new CurrencyTransactionRateDto();
        currencyTransactionRateDto.setDate(Timestamp.valueOf(currencyTransactionSaved.getDate()).toString());
        currencyTransactionRateDto.setTimestamp("1633973461");
        Map<String, BigDecimal> rates = new HashMap<>();
        rates.put(currencyTransactionSaved.getCurrencyDestination().getEnumAbbreviation(), new BigDecimal(1));
        currencyTransactionRateDto.setRates(rates);
        currencyTransactionRateDto.setBase(currencyTransactionSaved.getCurrencyOrigin().getEnumAbbreviation());
    }

    @Test
    public void createTransactionTest() throws Exception {
        when(this.financeCurrencyConverterServicePort.getCurrencyTransactionRate(
                this.currencyTransaction.getCurrencyOrigin().getEnumAbbreviation(),
                new String[]{this.currencyTransactionSaved.getCurrencyDestination().getEnumAbbreviation()},
                this.currencyTransaction.getUserId())
        ).thenReturn(new ResponseEntity<>(this.currencyTransactionRateDto, HttpStatus.OK));

        when(this.currencyTransactionPersistencePort.create(this.currencyTransaction))
                .thenReturn(this.currencyTransactionSaved);

        CurrencyTransaction result = service.create(this.currencyTransaction);
        assertEquals(this.currencyTransactionSaved.getConvertedValue(), result.getConvertedValue());
        assertEquals(this.currencyTransactionSaved.getConversionRate(), result.getConversionRate());
    }
}
