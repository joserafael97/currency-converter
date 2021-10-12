package br.com.jrafael.currencyconverter.domain;

import br.com.jrafael.currencyconverter.domain.constants.FinanceCoins;
import br.com.jrafael.currencyconverter.domain.dto.CurrencyTransactionRateDto;
import br.com.jrafael.currencyconverter.domain.exception.BusinessValidationException;
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
import static org.junit.jupiter.api.Assertions.assertThrows;
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

    @Test
    public void createTransactionWithSixDecimalsInSourceAndRateTest() throws Exception {
        BigDecimal decimalSource = new BigDecimal("2.391554");
        BigDecimal decimalRate = new BigDecimal("9.456715");
        this.currencyTransaction.setSourceValue(decimalSource);
        this.currencyTransaction.setConversionRate(decimalRate);
        this.currencyTransactionSaved.setSourceValue(decimalSource);
        this.currencyTransactionSaved.setConversionRate(decimalRate);

        when(this.financeCurrencyConverterServicePort.getCurrencyTransactionRate(
                this.currencyTransaction.getCurrencyOrigin().getEnumAbbreviation(),
                new String[]{this.currencyTransactionSaved.getCurrencyDestination().getEnumAbbreviation()},
                this.currencyTransaction.getUserId())
        ).thenReturn(new ResponseEntity<>(this.currencyTransactionRateDto, HttpStatus.OK));

        when(this.currencyTransactionPersistencePort.create(this.currencyTransaction))
                .thenReturn(this.currencyTransactionSaved);

        CurrencyTransaction result = service.create(this.currencyTransaction);
        assertEquals(new BigDecimal("22.62"), result.getConvertedValue());
        assertEquals(this.currencyTransactionSaved.getConversionRate(), result.getConversionRate());
    }

    @Test
    public void createTransactionWithFiveDecimalsInSourceAndRateTest() throws Exception {
        BigDecimal decimalSource = new BigDecimal("3.39155");
        BigDecimal decimalRate = new BigDecimal("11.45672");
        this.currencyTransaction.setSourceValue(decimalSource);
        this.currencyTransaction.setConversionRate(decimalRate);
        this.currencyTransactionSaved.setSourceValue(decimalSource);
        this.currencyTransactionSaved.setConversionRate(decimalRate);

        when(this.financeCurrencyConverterServicePort.getCurrencyTransactionRate(
                this.currencyTransaction.getCurrencyOrigin().getEnumAbbreviation(),
                new String[]{this.currencyTransactionSaved.getCurrencyDestination().getEnumAbbreviation()},
                this.currencyTransaction.getUserId())
        ).thenReturn(new ResponseEntity<>(this.currencyTransactionRateDto, HttpStatus.OK));

        when(this.currencyTransactionPersistencePort.create(this.currencyTransaction))
                .thenReturn(this.currencyTransactionSaved);

        CurrencyTransaction result = service.create(this.currencyTransaction);
        assertEquals(new BigDecimal("38.86"), result.getConvertedValue());
        assertEquals(this.currencyTransactionSaved.getConversionRate(), result.getConversionRate());
    }

    @Test
    public void createTransactionWithFourDecimalsInSourceAndRateTest() throws Exception {
        BigDecimal decimalSource = new BigDecimal("3.3915");
        BigDecimal decimalRate = new BigDecimal("11.4567");
        this.currencyTransaction.setSourceValue(decimalSource);
        this.currencyTransaction.setConversionRate(decimalRate);
        this.currencyTransactionSaved.setSourceValue(decimalSource);
        this.currencyTransactionSaved.setConversionRate(decimalRate);

        when(this.financeCurrencyConverterServicePort.getCurrencyTransactionRate(
                this.currencyTransaction.getCurrencyOrigin().getEnumAbbreviation(),
                new String[]{this.currencyTransactionSaved.getCurrencyDestination().getEnumAbbreviation()},
                this.currencyTransaction.getUserId())
        ).thenReturn(new ResponseEntity<>(this.currencyTransactionRateDto, HttpStatus.OK));

        when(this.currencyTransactionPersistencePort.create(this.currencyTransaction))
                .thenReturn(this.currencyTransactionSaved);

        CurrencyTransaction result = service.create(this.currencyTransaction);
        assertEquals(new BigDecimal("38.86"), result.getConvertedValue());
        assertEquals(this.currencyTransactionSaved.getConversionRate(), result.getConversionRate());
    }

    @Test
    public void createTransactionWithThreeDecimalsInSourceAndRateTest() throws Exception {
        BigDecimal decimalSource = new BigDecimal("3.391");
        BigDecimal decimalRate = new BigDecimal("11.456");
        this.currencyTransaction.setSourceValue(decimalSource);
        this.currencyTransaction.setConversionRate(decimalRate);
        this.currencyTransactionSaved.setSourceValue(decimalSource);
        this.currencyTransactionSaved.setConversionRate(decimalRate);

        when(this.financeCurrencyConverterServicePort.getCurrencyTransactionRate(
                this.currencyTransaction.getCurrencyOrigin().getEnumAbbreviation(),
                new String[]{this.currencyTransactionSaved.getCurrencyDestination().getEnumAbbreviation()},
                this.currencyTransaction.getUserId())
        ).thenReturn(new ResponseEntity<>(this.currencyTransactionRateDto, HttpStatus.OK));

        when(this.currencyTransactionPersistencePort.create(this.currencyTransaction))
                .thenReturn(this.currencyTransactionSaved);

        CurrencyTransaction result = service.create(this.currencyTransaction);
        assertEquals(new BigDecimal("38.85"), result.getConvertedValue());
        assertEquals(this.currencyTransactionSaved.getConversionRate(), result.getConversionRate());
    }

    @Test
    public void createTransactionWithTwoDecimalsInSourceAndRateTest() throws Exception {
        BigDecimal decimalSource = new BigDecimal("3.32");
        BigDecimal decimalRate = new BigDecimal("11.42");
        this.currencyTransaction.setSourceValue(decimalSource);
        this.currencyTransaction.setConversionRate(decimalRate);
        this.currencyTransactionSaved.setSourceValue(decimalSource);
        this.currencyTransactionSaved.setConversionRate(decimalRate);

        when(this.financeCurrencyConverterServicePort.getCurrencyTransactionRate(
                this.currencyTransaction.getCurrencyOrigin().getEnumAbbreviation(),
                new String[]{this.currencyTransactionSaved.getCurrencyDestination().getEnumAbbreviation()},
                this.currencyTransaction.getUserId())
        ).thenReturn(new ResponseEntity<>(this.currencyTransactionRateDto, HttpStatus.OK));

        when(this.currencyTransactionPersistencePort.create(this.currencyTransaction))
                .thenReturn(this.currencyTransactionSaved);

        CurrencyTransaction result = service.create(this.currencyTransaction);
        assertEquals(new BigDecimal("37.91"), result.getConvertedValue());
        assertEquals(this.currencyTransactionSaved.getConversionRate(), result.getConversionRate());
    }

    @Test
    public void createTransactionWithOneDecimalsInSourceAndRateTest() throws Exception {
        BigDecimal decimalSource = new BigDecimal("3.3");
        BigDecimal decimalRate = new BigDecimal("11.2");
        this.currencyTransaction.setSourceValue(decimalSource);
        this.currencyTransaction.setConversionRate(decimalRate);
        this.currencyTransactionSaved.setSourceValue(decimalSource);
        this.currencyTransactionSaved.setConversionRate(decimalRate);

        when(this.financeCurrencyConverterServicePort.getCurrencyTransactionRate(
                this.currencyTransaction.getCurrencyOrigin().getEnumAbbreviation(),
                new String[]{this.currencyTransactionSaved.getCurrencyDestination().getEnumAbbreviation()},
                this.currencyTransaction.getUserId())
        ).thenReturn(new ResponseEntity<>(this.currencyTransactionRateDto, HttpStatus.OK));

        when(this.currencyTransactionPersistencePort.create(this.currencyTransaction))
                .thenReturn(this.currencyTransactionSaved);

        CurrencyTransaction result = service.create(this.currencyTransaction);
        assertEquals(new BigDecimal("36.96"), result.getConvertedValue());
        assertEquals(this.currencyTransactionSaved.getConversionRate(), result.getConversionRate());
    }

    @Test
    public void createTransactionWithOneDecimalResultTest() throws Exception {
        BigDecimal decimalSource = new BigDecimal("3.3");
        BigDecimal decimalRate = new BigDecimal("11");
        this.currencyTransaction.setSourceValue(decimalSource);
        this.currencyTransaction.setConversionRate(decimalRate);
        this.currencyTransactionSaved.setSourceValue(decimalSource);
        this.currencyTransactionSaved.setConversionRate(decimalRate);

        when(this.financeCurrencyConverterServicePort.getCurrencyTransactionRate(
                this.currencyTransaction.getCurrencyOrigin().getEnumAbbreviation(),
                new String[]{this.currencyTransactionSaved.getCurrencyDestination().getEnumAbbreviation()},
                this.currencyTransaction.getUserId())
        ).thenReturn(new ResponseEntity<>(this.currencyTransactionRateDto, HttpStatus.OK));

        when(this.currencyTransactionPersistencePort.create(this.currencyTransaction))
                .thenReturn(this.currencyTransactionSaved);

        CurrencyTransaction result = service.create(this.currencyTransaction);
        assertEquals(new BigDecimal("36.30"), result.getConvertedValue());
        assertEquals(this.currencyTransactionSaved.getConversionRate(), result.getConversionRate());
    }

    @Test
    public void createTransactionWithNullCurrencyTransactionTest(){
        assertThrows(BusinessValidationException.class, () -> service.create(null));
    }

    @Test
    public void createTransactionWithNoRequiredFieldsTest() {
        assertThrows(BusinessValidationException.class, () -> {
            this.currencyTransaction.setCurrencyOrigin(null);
            service.create(this.currencyTransaction);
        });

        assertThrows(BusinessValidationException.class, () -> {
            this.currencyTransaction.setCurrencyDestination(null);
            service.create(this.currencyTransaction);
        });

        assertThrows(BusinessValidationException.class, () -> {
            this.currencyTransaction.setSourceValue(null);
            service.create(this.currencyTransaction);
        });

        assertThrows(BusinessValidationException.class, () -> {
            this.currencyTransaction.setUserId(null);
            service.create(this.currencyTransaction);
        });

        //all required null fields
        assertThrows(BusinessValidationException.class, () -> service.create(this.currencyTransaction));
    }

    @Test
    public void createTransactionWithSameCurrencyOriginAndDestionationTest() {
        assertThrows(BusinessValidationException.class, () -> {
            this.currencyTransaction.setCurrencyDestination(FinanceCoins.BRL);
            this.currencyTransaction.setCurrencyOrigin(FinanceCoins.BRL);
            service.create(this.currencyTransaction);
        });
    }

    @Test
    public void createTransactionWithZeroAndNegativeSourceValueTest() {
        assertThrows(BusinessValidationException.class, () -> {
            this.currencyTransaction.setSourceValue(BigDecimal.ZERO);
            service.create(this.currencyTransaction);
        });
        assertThrows(BusinessValidationException.class, () -> {
            this.currencyTransaction.setConversionRate(BigDecimal.ZERO);
            service.create(this.currencyTransaction);
        });
        assertThrows(BusinessValidationException.class, () -> {
            this.currencyTransaction.setSourceValue(new BigDecimal("-1"));
            service.create(this.currencyTransaction);
        });

        assertThrows(BusinessValidationException.class, () -> {
            this.currencyTransaction.setConversionRate(new BigDecimal("-1"));
            service.create(this.currencyTransaction);
        });
    }

    @Test
    public void createTransactionWithRateDtoNullTest() {
        this.currencyTransactionRateDto = null;
        when(this.financeCurrencyConverterServicePort.getCurrencyTransactionRate(
                this.currencyTransaction.getCurrencyOrigin().getEnumAbbreviation(),
                new String[]{this.currencyTransactionSaved.getCurrencyDestination().getEnumAbbreviation()},
                this.currencyTransaction.getUserId())
        ).thenReturn(new ResponseEntity<>(this.currencyTransactionRateDto, HttpStatus.OK));

        assertThrows(BusinessValidationException.class, () -> service.create(this.currencyTransaction));
    }

    @Test
    public void createTransactionWithRateDtoZeroValueTest()  {
        this.currencyTransactionRateDto.getRates().put(currencyTransactionSaved.getCurrencyDestination().getEnumAbbreviation(), BigDecimal.ZERO);
        when(this.financeCurrencyConverterServicePort.getCurrencyTransactionRate(
                this.currencyTransaction.getCurrencyOrigin().getEnumAbbreviation(),
                new String[]{this.currencyTransactionSaved.getCurrencyDestination().getEnumAbbreviation()},
                this.currencyTransaction.getUserId())
        ).thenReturn(new ResponseEntity<>(this.currencyTransactionRateDto, HttpStatus.OK));

        assertThrows(BusinessValidationException.class, () -> service.create(this.currencyTransaction));
    }

    @Test
    public void createTransactionWithRateDtoNegativeValueTest()  {
        this.currencyTransactionRateDto.getRates().put(currencyTransactionSaved.getCurrencyDestination().getEnumAbbreviation(), new BigDecimal("-2.201"));
        when(this.financeCurrencyConverterServicePort.getCurrencyTransactionRate(
                this.currencyTransaction.getCurrencyOrigin().getEnumAbbreviation(),
                new String[]{this.currencyTransactionSaved.getCurrencyDestination().getEnumAbbreviation()},
                this.currencyTransaction.getUserId())
        ).thenReturn(new ResponseEntity<>(this.currencyTransactionRateDto, HttpStatus.OK));

        assertThrows(BusinessValidationException.class, () -> service.create(this.currencyTransaction));
    }
}
