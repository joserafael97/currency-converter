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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = CurrencyTransactionApiApplication.class)
@AutoConfigureMockMvc
class CurrencyTransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CurrencyTransactionService currencyTransactionService;

    @Value("/${api.version}/transactions")
    private String uri;

    private CurrencyTransactionFormDto dtoForm;

    private CurrencyTransactionDto dto;
    private CurrencyTransactionDto dto2;

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

        dto2 = CurrencyTransactionDto
                .builder()
                .id("20939320kslkwslw203290322211111teste")
                .userId("12345novo")
                .conversionRate(new BigDecimal("1"))
                .currencyOrigin(FinanceCoins.EUR)
                .currencyDestination(FinanceCoins.JPY)
                .sourceValue(new BigDecimal("30"))
                .date(LocalDateTime.now())
                .build();
    }

    @Test
    void postTest() throws Exception {
        given(this.currencyTransactionService.create(ArgumentMatchers.any(CurrencyTransaction.class))).willReturn(this.dto.convert());
        this.mockMvc.perform(post(this.uri)
                .content(this.objectMapper.writeValueAsString(this.dtoForm))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andExpect(jsonPath("$.id", is(this.dto.getId())));
    }

    @Test
    void postWithoutIdUserTest() throws Exception {
        given(this.currencyTransactionService.create(ArgumentMatchers.any(CurrencyTransaction.class))).willReturn(this.dto.convert());
        this.dtoForm.setUserId("");
        this.mockMvc.perform(post(this.uri)
                .content(this.objectMapper.writeValueAsString(this.dtoForm))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andExpect(jsonPath("$.id", is(this.dto.getId())));
    }

    @Test
    void postWithNoRequiredFieldsTest() throws Exception {
        given(this.currencyTransactionService.create(ArgumentMatchers.any(CurrencyTransaction.class))).willReturn(this.dto.convert());
        this.dtoForm.setSourceValue(null);
        this.mockMvc.perform(post(this.uri)
                .content(this.objectMapper.writeValueAsString(this.dtoForm))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("status_code", is(HttpStatus.BAD_REQUEST.value())))
                .andExpect(content().string(containsString("message")));

        this.dtoForm.setCurrencyDestination(null);
        this.mockMvc.perform(post(this.uri)
                .content(this.objectMapper.writeValueAsString(this.dtoForm))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("status_code", is(HttpStatus.BAD_REQUEST.value())))
                .andExpect(content().string(containsString("message")));

    }

    @Test
    void postWithZeroAndNegativeSourceValueTest() throws Exception {
        given(this.currencyTransactionService.create(ArgumentMatchers.any(CurrencyTransaction.class))).willReturn(this.dto.convert());
        this.dtoForm.setSourceValue(BigDecimal.ZERO);
        this.mockMvc.perform(post(this.uri)
                .content(this.objectMapper.writeValueAsString(this.dtoForm))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("status_code", is(HttpStatus.BAD_REQUEST.value())))
                .andExpect(content().string(containsString("message")));


        this.dtoForm.setSourceValue(new BigDecimal("-2.3"));
        this.mockMvc.perform(post(this.uri)
                .content(this.objectMapper.writeValueAsString(this.dtoForm))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("status_code", is(HttpStatus.BAD_REQUEST.value())))
                .andExpect(content().string(containsString("message")));

    }

    @Test
    void getAllTransactionsTest() throws Exception {
        List<CurrencyTransaction> transactions = new ArrayList<>();
        transactions.add(this.dto.convert());
        transactions.add(this.dto.convert());
        Page<CurrencyTransaction> pagedResponse = new PageImpl<>(transactions);
        given(this.currencyTransactionService.getAll(any(Pageable.class))).willReturn(pagedResponse);
        this.mockMvc.perform(get(this.uri)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()", is(2)));
    }

    @Test
    void getAllTransactionsByIdUserTest() throws Exception {
        List<CurrencyTransaction> transactions = new ArrayList<>();
        transactions.add(this.dto2.convert());
        Page<CurrencyTransaction> pagedResponse = new PageImpl<>(transactions);
        given(this.currencyTransactionService.getByIdUser(any(String.class), any(Pageable.class))).willReturn(pagedResponse);
        this.mockMvc.perform(get(this.uri)
                .contentType(MediaType.APPLICATION_JSON)
                .param("idUser", "12345novo"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()", is(1)));
    }
}
