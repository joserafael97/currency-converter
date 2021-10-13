package br.com.jrafael.currencyconverter.domain.service;

import br.com.jrafael.currencyconverter.domain.dto.CurrencyTransactionRateDto;
import br.com.jrafael.currencyconverter.domain.exception.BusinessValidationException;
import br.com.jrafael.currencyconverter.domain.model.CurrencyTransaction;
import br.com.jrafael.currencyconverter.domain.port.persistence.CurrencyTransactionPersistencePort;
import br.com.jrafael.currencyconverter.domain.port.service.FinanceCurrencyConverterServicePort;
import br.com.jrafael.currencyconverter.domain.util.validation.init.CurrencyTransactionAtributesValidation;
import br.com.jrafael.currencyconverter.domain.util.validation.init.RateCurrencyTransactionValidation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class CurrencyTransactionService {

    protected final Logger LOGGER = LogManager.getLogger(CurrencyTransactionService.class);

    private final CurrencyTransactionPersistencePort currencyTransactionPersistencePort;
    private final FinanceCurrencyConverterServicePort financeCurrencyConverterServicePort;

    public CurrencyTransactionService(final CurrencyTransactionPersistencePort currencyTransactionPersistencePort,
                               final FinanceCurrencyConverterServicePort financeCurrencyConverterServicePort){
        this.currencyTransactionPersistencePort = currencyTransactionPersistencePort;
        this.financeCurrencyConverterServicePort = financeCurrencyConverterServicePort;
    }

    public CurrencyTransaction create(CurrencyTransaction model) throws BusinessValidationException {
        LOGGER.trace("starting attribute validation to create currency transaction.");
        CurrencyTransactionAtributesValidation.validate(model);
        LOGGER.trace("Attribute validation for finished currency conversion.");

        String[] coins = new String[]{model.getCurrencyDestination().getEnumAbbreviation()};
        LOGGER.info(String.format("Query conversion rate from %s to %s", model.getCurrencyOrigin().getEnumAbbreviation(), model.getCurrencyDestination().getEnumAbbreviation()));
        CurrencyTransactionRateDto rateDto = this.financeCurrencyConverterServicePort
                .getCurrencyTransactionRate(
                        model.getCurrencyOrigin().getEnumAbbreviation(),
                        coins,
                        model.getUserId()).getBody();
        LOGGER.trace("starting rate validation");
        RateCurrencyTransactionValidation.validate(rateDto);
        LOGGER.trace("rate validation finished");
        model.setConversionRate(rateDto.getRates().get(model.getCurrencyDestination().getEnumAbbreviation()));
        model.setDate(rateDto.getTimestamp());
        model = this.currencyTransactionPersistencePort.create(model);
        LOGGER.trace(String.format("CurrencyTransaction saved in database: %s", model));
        return model;
    }

    public Page<CurrencyTransaction> getAll(Pageable page){
        LOGGER.info(String.format("Query all CurrencyTransaction with page=%d size=%d", page.getPageNumber(), page.getPageSize()));
        return this.currencyTransactionPersistencePort.getAll(page);
    }

    public Page<CurrencyTransaction>  getByIdUser(String idUserId, Pageable page){
        LOGGER.info(String.format("Query all CurrencyTransaction with page=%d size=%d and idUserId=%s", page.getPageNumber(), page.getPageSize(), idUserId));
        return this.currencyTransactionPersistencePort.getByUserId(idUserId, page);
    }
}
