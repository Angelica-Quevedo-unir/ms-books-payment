package es.unir.relatosdepapel.payments;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "es.unir.relatosdepapel.payments.external")
public class MsBooksPaymentsApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsBooksPaymentsApplication.class, args);
	}

}
