package br.com.jrafael.currencyconverter.api.controller;

import br.com.jrafael.currencyconverter.api.dto.CurrencyTransactionDto;
import br.com.jrafael.currencyconverter.api.dto.CurrencyTransactionFormDto;
import br.com.jrafael.currencyconverter.domain.exception.BusinessException;
import br.com.jrafael.currencyconverter.domain.model.CurrencyTransaction;
import br.com.jrafael.currencyconverter.domain.service.CurrencyTransactionService;
import br.com.jrafael.infrastructure.controller.ControllerBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
@RequestMapping(value = "/${api.version}/transactions")
public class CurrencyTransactionController extends ControllerBase {

    private final CurrencyTransactionService currencyTransactionService;

    @Value("${exchangeratesapi.key}")
    private String defaultExchangeratesapiKey;

    @Autowired
    public CurrencyTransactionController(final CurrencyTransactionService currencyTransactionService) {
        this.currencyTransactionService = currencyTransactionService;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<CurrencyTransactionDto> post(@RequestBody @Valid CurrencyTransactionFormDto form) throws BusinessException {
        this.setDefaultAccessKey(form);
        CurrencyTransaction entity = this.currencyTransactionService.create(form.convert());
        return ResponseEntity.ok(this.convert(entity));
    }

    @GetMapping
    public Page<CurrencyTransactionDto> getAll(@PageableDefault(direction = Sort.Direction.DESC, page = 0, size = 100) Pageable page, @Valid @RequestParam(value = "idUser", required = false) String idUser) {
        Page<CurrencyTransaction> entities = idUser != null ? this.currencyTransactionService.getByIdUser(idUser, page):
                this.currencyTransactionService.getAll(page);
        return entities.map(entity -> this.convert(entity));
    }

    private void setDefaultAccessKey(CurrencyTransactionFormDto form){
        if (form != null
                && (form.getUserId() == null || form.getUserId().length() < 1)
                && (this.defaultExchangeratesapiKey != null && this.defaultExchangeratesapiKey.length() > 0)) {
            form.setUserId(this.defaultExchangeratesapiKey);
        }
    }

    private CurrencyTransactionDto convert(CurrencyTransaction entity) {
        return new CurrencyTransactionDto(entity);
    }


}
