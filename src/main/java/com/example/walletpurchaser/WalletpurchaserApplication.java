package com.example.walletpurchaser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@EnableAutoConfiguration
@EntityScan(basePackages = {"com.example.model"})
@SpringBootApplication(scanBasePackages = {"com.example"} )
public class WalletpurchaserApplication {

	public static void main(String[] args) {

		SpringApplication.run(WalletpurchaserApplication.class, args);

	}

}
