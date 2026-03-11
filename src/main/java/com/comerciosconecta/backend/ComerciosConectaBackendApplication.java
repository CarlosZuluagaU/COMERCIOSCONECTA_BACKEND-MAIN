package com.comerciosconecta.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.comerciosconecta.backend")
@EntityScan(basePackages = "com.comerciosconecta.backend.entity")
@EnableJpaRepositories(basePackages = "com.comerciosconecta.backend.repository")
public class ComerciosConectaBackendApplication {
	public static void main(String[] args) {
		SpringApplication.run(ComerciosConectaBackendApplication.class, args);
	}
}

