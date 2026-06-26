package com.api_gateway2.api_gateway2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ApiGateway2Application {

	public static void main(String[] args) {
		SpringApplication.run(ApiGateway2Application.class, args);
	}

	@org.springframework.context.annotation.Bean
	public org.springframework.cloud.gateway.filter.GlobalFilter customGlobalFilter() {
	    return (exchange, chain) -> {
	        System.out.println("GATEWAY REQUEST: " + exchange.getRequest().getMethod() + " " + exchange.getRequest().getURI());
	        return chain.filter(exchange);
	    };
	}

}
