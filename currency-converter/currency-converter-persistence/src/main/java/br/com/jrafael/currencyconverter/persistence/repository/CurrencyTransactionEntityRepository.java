package br.com.jrafael.currencyconverter.persistence.repository;

import br.com.jrafael.currencyconverter.persistence.model.CurrencyTransactionEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CurrencyTransactionEntityRepository extends JpaRepository<CurrencyTransactionEntity, String> {

    Page<CurrencyTransactionEntity> findByUserId(String idUser, Pageable page);
}
