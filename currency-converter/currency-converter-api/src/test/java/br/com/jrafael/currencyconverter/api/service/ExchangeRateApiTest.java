package br.com.jrafael.currencyconverter.api.service;

import br.com.jrafael.currencyconverter.api.CurrencyTransactionApiApplication;
import br.com.jrafael.currencyconverter.domain.constants.FinanceCoins;
import br.com.jrafael.currencyconverter.domain.dto.CurrencyTransactionRateDto;
import feign.FeignException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class to verify error capture by external api system
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = CurrencyTransactionApiApplication.class)
@AutoConfigureMockMvc
public class ExchangeRateApiTest {

    @Autowired
    private ExchangeRateApi exchangeRateApi;

    @Value("${exchangeratesapi.key}")
    private String defaultExchangeratesapiKey;

    @BeforeEach
    public void setUp() {

    }

    @Test
    public void getRatesWithBaseEurTest() throws Exception {
        ResponseEntity<CurrencyTransactionRateDto> result=  this.exchangeRateApi
                .getCurrencyTransactionRate(FinanceCoins.EUR.getEnumAbbreviation(),
                        new String[]{FinanceCoins.BRL.getEnumAbbreviation()},
                        this.defaultExchangeratesapiKey);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(FinanceCoins.EUR.getEnumAbbreviation(), result.getBody().getBase());
        assertTrue(result.getBody().getRates().size() == 1);
    }

    @Test
    public void getRatesWitInvalidAccessKeyTest() throws Exception {
        assertThrows(FeignException.class, () -> this.exchangeRateApi
                .getCurrencyTransactionRate(FinanceCoins.EUR.getEnumAbbreviation(),
                        new String[]{FinanceCoins.BRL.getEnumAbbreviation()},
                        "testeinvalidacess"));
    }

    @Test
    public void getRatesWitInvalidBaseTest() throws Exception {
        assertThrows(FeignException.class, () -> this.exchangeRateApi
                .getCurrencyTransactionRate(FinanceCoins.JPY.getEnumAbbreviation(),
                        new String[]{FinanceCoins.BRL.getEnumAbbreviation()},
                        this.defaultExchangeratesapiKey));
    }
}
