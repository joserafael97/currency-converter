package br.com.jrafael.currencyconverter.domain.constants;

import br.com.jrafael.currencyconverter.domain.exception.BusinessValidationException;

public enum FinanceCoins {

    BRL("Reais", "BRL"),
    USD("Dólar", "USD"),
    JPY("Iene japonês", "JPY"),
    EUR("Euro", "EUR");

    private String description;
    private String enumAbbreviation;

    private FinanceCoins(String description, String enumAbbreviation) {
        this.description = description;
        this.enumAbbreviation = enumAbbreviation;
    }


    public static FinanceCoins getByCode(String code) throws BusinessValidationException {
        for (FinanceCoins enumTipo : FinanceCoins.values()) {
            if (enumTipo.getEnumAbbreviation().equals(code)) {
                return enumTipo;
            }
        }
        throw new BusinessValidationException("Invalid finance coin.");
    }
    
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEnumAbbreviation() {
        return enumAbbreviation;
    }

    public void setEnumAbbreviation(String enumAbbreviation) {
        this.enumAbbreviation = enumAbbreviation;
    }
}
