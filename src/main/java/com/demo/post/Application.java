package com.demo.post;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	// To make the loading fail
	// @Bean
	// CommandLineRunner commandLineRunner() {
	// 	return args -> {
	// 		throw new RuntimeException("Application could not load");
	// 	};
	// }

}
