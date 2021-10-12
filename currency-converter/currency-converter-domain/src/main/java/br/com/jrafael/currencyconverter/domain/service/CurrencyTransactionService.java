package br.com.jrafael.currencyconverter.domain.service;

import br.com.jrafael.currencyconverter.domain.dto.CurrencyTransactionRateDto;
import br.com.jrafael.currencyconverter.domain.exception.BusinessValidationException;
import br.com.jrafael.currencyconverter.domain.model.CurrencyTransaction;
import br.com.jrafael.currencyconverter.domain.port.persistence.CurrencyTransactionPersistencePort;
import br.com.jrafael.currencyconverter.domain.port.service.FinanceCurrencyConverterServicePort;
import br.com.jrafael.currencyconverter.domain.util.validation.init.CurrencyTransactionAtributesValidation;
import br.com.jrafael.currencyconverter.domain.util.validation.init.RateCurrencyTransactionValidation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.logging.Logger;

public class CurrencyTransactionService {

    protected final Logger LOGGER = Logger.getLogger(CurrencyTransactionService.class.getName());

    private final CurrencyTransactionPersistencePort currencyTransactionPersistencePort;
    private final FinanceCurrencyConverterServicePort financeCurrencyConverterServicePort;

    public CurrencyTransactionService(final CurrencyTransactionPersistencePort currencyTransactionPersistencePort,
                               final FinanceCurrencyConverterServicePort financeCurrencyConverterServicePort){
        this.currencyTransactionPersistencePort = currencyTransactionPersistencePort;
        this.financeCurrencyConverterServicePort = financeCurrencyConverterServicePort;
    }

    public CurrencyTransaction create(CurrencyTransaction model) throws BusinessValidationException {
        LOGGER.info("starting attribute validation to create currency transaction.");
        CurrencyTransactionAtributesValidation.validate(model);
        LOGGER.info("Attribute validation for finished currency conversion.");

        String[] coins = new String[]{model.getCurrencyDestination().getEnumAbbreviation()};
        LOGGER.info("Query conversion rate from"+ model.getCurrencyOrigin().getEnumAbbreviation()+ " to "+ model.getCurrencyDestination().getEnumAbbreviation());
        CurrencyTransactionRateDto rateDto = this.financeCurrencyConverterServicePort
                .getCurrencyTransactionRate(
                        model.getCurrencyOrigin().getEnumAbbreviation(),
                        coins,
                        model.getUserId()).getBody();
        LOGGER.info("starting rate validation");
        RateCurrencyTransactionValidation.validate(rateDto);
        LOGGER.info("rate validation finished");
        model.setConversionRate(rateDto.getRates().get(model.getCurrencyDestination().getEnumAbbreviation()));
        model.setDate(rateDto.getTimestamp());
        model = this.currencyTransactionPersistencePort.create(model);
        LOGGER.info("CurrencyTransaction saved in database: " + model);
        return model;
    }

    public Page<CurrencyTransaction> getAll(Pageable page){
        LOGGER.info("Query all CurrencyTransaction with page="+ page.getPageNumber() + " size="+ page.getPageSize() );
        return this.currencyTransactionPersistencePort.getAll(page);
    }

    public Page<CurrencyTransaction>  getByIdUser(String idUserId, Pageable page){
        LOGGER.info("Query all CurrencyTransaction with page="+ page.getPageNumber() + " size="+ page.getPageSize() +" and idUserId=" + idUserId);
        return this.currencyTransactionPersistencePort.getByUserId(idUserId, page);
    }
}
