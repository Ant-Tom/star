package com.starbank.star;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition
public class StarApplication {

	public static void main(String[] args) {
		SpringApplication.run(StarApplication.class, args);
	}

}
