package com.example.kel31;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.example.*")
public class Kel31Application {

	public static void main(String[] args) {
		SpringApplication.run(Kel31Application.class, args);
	}

}
