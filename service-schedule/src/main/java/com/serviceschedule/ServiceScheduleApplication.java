package com.serviceschedule;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class ServiceScheduleApplication {

	public static void main(String[] args) {

		SpringApplication.run(ServiceScheduleApplication.class, args);
	}

}
