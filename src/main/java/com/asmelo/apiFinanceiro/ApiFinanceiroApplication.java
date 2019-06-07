package com.asmelo.apiFinanceiro;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = "com.asmelo.apiFinanceiro.repository")
@SpringBootApplication
public class ApiFinanceiroApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiFinanceiroApplication.class, args);
	}

}
