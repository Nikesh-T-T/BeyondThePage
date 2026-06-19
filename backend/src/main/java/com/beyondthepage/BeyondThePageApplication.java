package com.beyondthepage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BeyondThePageApplication {

	public static void main(String[] args) {
		SpringApplication.run(BeyondThePageApplication.class, args);
	}
}
