package com.biswaljava.microservices.currencyexchangeservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@EnableDiscoveryClient
public class CurrencyExchangeServiceApplicationH2 {

	public static void main(String[] args) {
		SpringApplication.run(CurrencyExchangeServiceApplicationH2.class, args);
	}
}
