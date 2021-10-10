package br.com.jrafael.currencyconverter.api.configuration;


import br.com.jrafael.infrastructure.configuration.SwaggerConfiguration;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerAppConfiguration extends SwaggerConfiguration {

    public SwaggerAppConfiguration(){
        this.projectPackage = "br.com.jrafael.currencyconverter.api.controller";
        this.titleApplication = "Currency Converter";
    }
}
