package com.envio.envio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EnvioApplication {

	public static void main(String[] args) {
		SpringApplication.run(EnvioApplication.class, args);
	}

	@org.springframework.context.annotation.Bean
	public org.springframework.web.filter.CommonsRequestLoggingFilter requestLoggingFilter() {
	    org.springframework.web.filter.CommonsRequestLoggingFilter loggingFilter = new org.springframework.web.filter.CommonsRequestLoggingFilter();
	    loggingFilter.setIncludeClientInfo(true);
	    loggingFilter.setIncludeQueryString(true);
	    loggingFilter.setIncludePayload(true);
	    loggingFilter.setMaxPayloadLength(10000);
	    return loggingFilter;
	}

	@org.springframework.context.annotation.Bean
	public org.springframework.web.client.RestTemplate restTemplate() {
	    return new org.springframework.web.client.RestTemplate();
	}

}
