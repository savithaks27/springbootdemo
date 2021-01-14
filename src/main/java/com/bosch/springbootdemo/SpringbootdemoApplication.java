package com.bosch.springbootdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class SpringbootdemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootdemoApplication.class, args);
	}

	@Profile("dev")
	@Bean
	public String devBean() {
		return "dev";
	}

	@Profile("test")
	@Bean
	public String testBean() {
		return "test";
	}

	@Profile("prod")
	@Bean
	public String prodBean() {
		return "prod";
	}
}
