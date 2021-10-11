package br.com.jrafael.currencyconverter.domain.port.service;

import br.com.jrafael.currencyconverter.domain.dto.CurrencyTransactionRateDto;
import org.springframework.web.bind.annotation.PathVariable;

public interface FinanceCurrencyConverterServicePort {

    CurrencyTransactionRateDto getCurrencyTransactionRate(@PathVariable("base") String base, @PathVariable("coins") String[] coins, @PathVariable("access_key") String accessKey);
}
