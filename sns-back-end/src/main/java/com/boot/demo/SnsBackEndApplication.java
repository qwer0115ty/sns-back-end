package com.boot.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = { "com.boot" })
@SpringBootApplication
public class SnsBackEndApplication {
	public static void main(String[] args) {
		SpringApplication.run(SnsBackEndApplication.class, args);
	}
}
