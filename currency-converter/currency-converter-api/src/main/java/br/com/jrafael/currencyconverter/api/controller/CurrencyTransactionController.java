package br.com.jrafael.currencyconverter.api.controller;

import br.com.jrafael.currencyconverter.api.dto.CurrencyTransactionDto;
import br.com.jrafael.currencyconverter.api.dto.CurrencyTransactionFormDto;
import br.com.jrafael.currencyconverter.domain.exception.BusinessException;
import br.com.jrafael.currencyconverter.domain.model.CurrencyTransaction;
import br.com.jrafael.currencyconverter.domain.service.CurrencyTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;

@Validated
@RestController
@RequestMapping(value = "/transactions")
public class CurrencyTransactionController {

    protected static final String PAGE_NUMBER_PARAM = "page";
    protected static final String PAGE_SIZE_PARAM = "size";
    protected static final Integer INIT_SIZE_PAGE = 10;
    protected static final Integer INIT_NUMBER_PAGE = 0;

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

    @GetMapping(value = "",params = { PAGE_NUMBER_PARAM, PAGE_SIZE_PARAM })
    public Page<CurrencyTransactionDto> getAll(@Valid @RequestParam(value = "idUser", required = false) String idUser,
                                               @PageableDefault(direction = Sort.Direction.DESC, page = this.INIT_NUMBER_PAGE, size = this.INIT_SIZE_PAGE)  Pageable page) {
        Page<CurrencyTransaction> entities = idUser != null ? this.currencyTransactionService.getByIdUser(idUser, page):
                this.currencyTransactionService.getAll(page);
        return entities.map(entity -> this.convert(entity));
    }

    private CurrencyTransactionDto convert(CurrencyTransaction entity) {
        return new CurrencyTransactionDto(entity);
    }


}
