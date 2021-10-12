package br.com.jrafael.currencyconverter.api.controller;

import br.com.jrafael.currencyconverter.api.CurrencyTransactionApiApplication;
import br.com.jrafael.currencyconverter.api.dto.CurrencyTransactionDto;
import br.com.jrafael.currencyconverter.api.dto.CurrencyTransactionFormDto;
import br.com.jrafael.currencyconverter.domain.constants.FinanceCoins;
import br.com.jrafael.currencyconverter.domain.model.CurrencyTransaction;
import br.com.jrafael.currencyconverter.domain.service.CurrencyTransactionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = CurrencyTransactionApiApplication.class)
@AutoConfigureMockMvc
public class CurrencyTransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CurrencyTransactionService currencyTransactionService;

    private CurrencyTransactionFormDto dtoForm;

    private CurrencyTransactionDto dto;

    @BeforeEach
    public void setUp() {
        dtoForm = CurrencyTransactionFormDto
                .builder()
                .currencyDestination(FinanceCoins.BRL)
                .sourceValue(new BigDecimal("30"))
                .userId("12345")
                .build();

        dto = CurrencyTransactionDto
                .builder()
                .id("20939320kslkwslw2032903")
                .userId("12345")
                .conversionRate(new BigDecimal("1"))
                .currencyOrigin(FinanceCoins.EUR)
                .currencyDestination(FinanceCoins.BRL)
                .sourceValue(new BigDecimal("30"))
                .date(LocalDateTime.now())
                .build();
    }

    @Test
    public void postTest() throws Exception {
        given(this.currencyTransactionService.create(ArgumentMatchers.any(CurrencyTransaction.class))).willReturn(this.dto.convert());
        this.mockMvc.perform(post("/transactions")
                .content(this.objectMapper.writeValueAsString(this.dtoForm))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andExpect(jsonPath("$.id", is(this.dto.getId())));
    }
}
