package com.hsob.payment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
@ComponentScan(basePackages = {"com.hsob.documentdb", "com.hsob.payment"})
public class PeymentServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PeymentServiceApplication.class, args);
	}

}
