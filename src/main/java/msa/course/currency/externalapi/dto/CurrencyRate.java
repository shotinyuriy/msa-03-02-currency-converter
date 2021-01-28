package msa.course.currency.externalapi.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class CurrencyRate {
    private Long id;
    private String currency;
    private LocalDateTime timestamp;
    private BigDecimal rate;
}
