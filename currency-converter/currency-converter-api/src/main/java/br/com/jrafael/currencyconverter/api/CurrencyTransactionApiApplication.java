package br.com.jrafael.currencyconverter.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableFeignClients
@SpringBootApplication(scanBasePackages = {"br.com.jrafael.currencyconverter.api"})
@EnableJpaRepositories(basePackages = {"br.com.jrafael.currencyconverter.persistence"})
@EntityScan(basePackages = {"br.com.jrafael.currencyconverter.persistence"})
@ComponentScan(basePackages = {
        "br.com.jrafael.infrastructure",
        "br.com.jrafael.currencyconverter.persistence",
        "br.com.jrafael.currencyconverter.api"
})
public class CurrencyTransactionApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(CurrencyTransactionApiApplication.class, args);
    }

}
