package br.com.jrafael.currencyconverter.persistence.adapter;

import br.com.jrafael.currencyconverter.domain.model.CurrencyTransaction;
import br.com.jrafael.currencyconverter.domain.port.persistence.CurrencyTransactionPersistencePort;
import br.com.jrafael.currencyconverter.persistence.repository.CurrencyTransactionEntityRepository;
import br.com.jrafael.currencyconverter.persistence.model.CurrencyTransactionEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CurrencyTransactionEntityRepositoryImpl implements CurrencyTransactionPersistencePort {

    @Autowired
    private CurrencyTransactionEntityRepository repository ;

    @Override
    public CurrencyTransaction create(CurrencyTransaction model) {
        CurrencyTransactionEntity entityJpa = this.getCurrencyTransactionEntity(model);
        entityJpa = this.repository.save(entityJpa);
        return this.getCurrencyTransaction(entityJpa);
    }

    @Override
    public Page<CurrencyTransaction> getAll(Pageable page) {
       return this.repository
                .findAll(page)
                .map(this::getCurrencyTransaction);
    }

    private CurrencyTransactionEntity getCurrencyTransactionEntity(CurrencyTransaction domainModel) {
        return CurrencyTransactionEntity.builder()
                .currencyOrigin(domainModel.getCurrencyOrigin())
                .destinationCurrency(domainModel.getCurrencyDestination())
                .date(domainModel.getDate())
                .conversionRate(domainModel.getConversionRate())
                .userId(domainModel.getUserId())
                .sourceValue(domainModel.getSourceValue())
                .build();
    }

    private CurrencyTransaction getCurrencyTransaction(CurrencyTransactionEntity entity) {
        CurrencyTransaction obj = new CurrencyTransaction();
        obj.setUserId(entity.getUserId());
        obj.setCurrencyOrigin(entity.getCurrencyOrigin());
        obj.setCurrencyDestination(entity.getDestinationCurrency());
        obj.setDate(entity.getDate());
        obj.setSourceValue(entity.getSourceValue());
        obj.setId(entity.getId());
        obj.setConversionRate(entity.getConversionRate());
        return obj;
    }

}
