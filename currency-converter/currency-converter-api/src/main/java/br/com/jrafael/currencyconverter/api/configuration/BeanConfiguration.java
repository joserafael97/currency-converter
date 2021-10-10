package br.com.jrafael.currencyconverter.api.configuration;

import br.com.jrafael.currencyconverter.api.service.ExchangeRateApi;
import br.com.jrafael.currencyconverter.domain.service.CurrencyTransactionService;
import br.com.jrafael.currencyconverter.persistence.adapter.CurrencyTransactionEntityRepositoryImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Bean
    CurrencyTransactionService currencyTransactionService(final CurrencyTransactionEntityRepositoryImpl repository, final ExchangeRateApi currenExchangeRateApi) {
        return new CurrencyTransactionService(repository, currenExchangeRateApi);
    }
}
