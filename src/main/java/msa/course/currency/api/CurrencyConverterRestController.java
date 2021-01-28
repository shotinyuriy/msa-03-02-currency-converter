package msa.course.currency.api;

import msa.course.currency.api.dto.CurrencyConversionRequest;
import msa.course.currency.api.dto.CurrencyConversionResponse;
import msa.course.currency.externalapi.dto.CurrencyRate;
import msa.course.currency.externalapi.dto.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.math.RoundingMode;


@RestController
@RequestMapping("/currency-conversion")
public class CurrencyConverterRestController {

    @PostConstruct
    public void startingLogs() {
        System.out.println(" CONTROLLER STARTED ");
    }

    @PostMapping("/simple")
    public ResponseEntity<CurrencyConversionResponse> convert(@RequestBody CurrencyConversionRequest request) {
        System.out.println(" REQUEST RECEIVED ");
        ResponseEntity<ResponseDto> response = new RestTemplate()
                .getForEntity("http://localhost:8090/currency-rates/actual", ResponseDto.class);
        if (response.getStatusCode().is2xxSuccessful()) {
            System.out.println(" RESPONSE OK ");
            CurrencyRate[] rates = new CurrencyRate[2];
            response.getBody().getCurrencyRates().stream()
                    .forEach(currencyRate -> {
                        System.out.println(" currencyRate = "+currencyRate);
                        if (currencyRate.getCurrency().equals(request.getCurrencyFrom())) {
                            rates[0] = currencyRate;
                        }
                        if (currencyRate.getCurrency().equals(request.getCurrencyTo())) {
                            rates[1] = currencyRate;
                        }
                    });
            if (rates[0] != null && rates[1] != null) {
                BigDecimal result = request.getAmount()
                        .multiply(rates[0].getRate())
                        .divide(rates[1].getRate(), RoundingMode.HALF_DOWN);
                CurrencyConversionResponse myResponse = new CurrencyConversionResponse();
                myResponse.setCurrencyFrom(request.getCurrencyFrom());
                myResponse.setCurrencyTo(request.getCurrencyTo());
                myResponse.setAmount(request.getAmount());
                myResponse.setResult(result);
                return ResponseEntity.ok(myResponse);
            } else {
                return ResponseEntity.notFound().build();
            }
        } else {
            System.out.println(" RESPONSE FAILED ");
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build();
        }
    }
}
