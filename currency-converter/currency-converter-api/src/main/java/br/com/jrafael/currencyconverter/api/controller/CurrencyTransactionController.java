package br.com.jrafael.currencyconverter.api.controller;

import br.com.jrafael.currencyconverter.api.dto.CurrencyTransactionDto;
import br.com.jrafael.currencyconverter.api.dto.CurrencyTransactionFormDto;
import br.com.jrafael.currencyconverter.domain.model.CurrencyTransaction;
import br.com.jrafael.currencyconverter.domain.service.CurrencyTransactionService;
import br.com.jrafael.infrastructure.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController("transactions")
public class CurrencyTransactionController {

    protected static final String PAGE_NUMBER_PARAM = "page";
    protected static final String PAGE_SIZE_PARAM = "size";

    private final CurrencyTransactionService currencyTransactionService;

    @Autowired
    public CurrencyTransactionController(final CurrencyTransactionService currencyTransactionService) {
        this.currencyTransactionService = currencyTransactionService;
    }


    @PostMapping
    @Transactional
    public ResponseEntity<CurrencyTransactionDto> post(@RequestBody @Valid CurrencyTransactionFormDto form) throws BusinessException {
        CurrencyTransaction entity = this.currencyTransactionService.create(form.convert());
        return ResponseEntity.ok(this.convert(entity));
    }

    @GetMapping(params = { PAGE_NUMBER_PARAM, PAGE_SIZE_PARAM })
    public Page<CurrencyTransactionDto> getPaginated(@PageableDefault(direction = Sort.Direction.DESC, page = 0, size = 10) Pageable page) {
        Page<CurrencyTransaction> entities = this.currencyTransactionService.getAll(page);
        return entities.map(entity -> this.convert(entity));
    }

    private CurrencyTransactionDto convert(CurrencyTransaction entity) {
        return new CurrencyTransactionDto(entity);
    }


}
