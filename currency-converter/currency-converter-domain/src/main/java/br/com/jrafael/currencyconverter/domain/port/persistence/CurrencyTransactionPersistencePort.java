package br.com.jrafael.currencyconverter.domain.port.persistence;

import br.com.jrafael.currencyconverter.domain.model.CurrencyTransaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CurrencyTransactionPersistencePort{

    CurrencyTransaction create(CurrencyTransaction model);

    Page<CurrencyTransaction> getAll(Pageable pageable);
}
