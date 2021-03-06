package br.com.jrafael.currencyconverter.api.service;

import br.com.jrafael.currencyconverter.domain.dto.CurrencyTransactionRateDto;
import br.com.jrafael.currencyconverter.domain.port.service.FinanceCurrencyConverterServicePort;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(url = "http://api.exchangeratesapi.io/v1", name = "rates")
public interface ExchangeRateApi extends FinanceCurrencyConverterServicePort {

    @GetMapping("/latest?access_key={access_key}&base={base}&symbols={coins}")
    ResponseEntity<CurrencyTransactionRateDto> getCurrencyTransactionRate(
            @PathVariable("base") String base,
            @PathVariable("coins") String[] coins,
            @PathVariable("access_key") String accessKey);

}
