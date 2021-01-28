package msa.course.currency.externalapi.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Collection;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseDto {
    private Collection<CurrencyRate> currencyRates;
    private String link;
    private String errorMessage;
}