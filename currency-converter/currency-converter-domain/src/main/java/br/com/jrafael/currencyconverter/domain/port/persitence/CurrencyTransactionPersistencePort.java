package br.com.jrafael.currencyconverter.domain.port.persitence;

import br.com.jrafael.currencyconverter.domain.model.CurrencyTransaction;

import java.util.List;

public interface CurrencyTransactionPersistencePort {

    CurrencyTransaction create(CurrencyTransaction model);

    List<CurrencyTransaction> getAll();
}
