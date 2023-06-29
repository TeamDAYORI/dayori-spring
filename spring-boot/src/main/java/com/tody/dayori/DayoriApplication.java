package com.tody.dayori;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class DayoriApplication {

	public static void main(String[] args) {
		SpringApplication.run(DayoriApplication.class, args);
	}

}
