package msa.course.currency;

import msa.course.currency.api.CurrencyConverterRestController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackageClasses = {CurrencyConverterRestController.class})
public class Msa0302CurrencyConverterApplication {

	public static void main(String[] args) {
		SpringApplication.run(Msa0302CurrencyConverterApplication.class, args);
	}

}
