package msa.course.currency.api.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CurrencyConversionResponse {
    private String currencyFrom;
    private String currencyTo;
    private BigDecimal amount;
    private BigDecimal result;
}
