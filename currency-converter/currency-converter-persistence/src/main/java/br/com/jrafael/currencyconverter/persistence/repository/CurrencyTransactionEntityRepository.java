package br.com.jrafael.currencyconverter.persistence.repository;

import br.com.jrafael.currencyconverter.persistence.model.CurrencyTransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CurrencyTransactionEntityRepository extends JpaRepository<CurrencyTransactionEntity, Long> {
}
