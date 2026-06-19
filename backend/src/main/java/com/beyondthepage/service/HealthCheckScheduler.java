package com.beyondthepage.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class HealthCheckScheduler {

	private static final Logger log = LoggerFactory.getLogger(HealthCheckScheduler.class);

	@Value("${server.port:8080}")
	private int serverPort;

	private final RestTemplate restTemplate = new RestTemplate();

	// Every 14 minutes — keeps the Render free-tier instance from spinning down
	@Scheduled(fixedRateString = "840000")
	public void ping() {
		String url = "http://localhost:" + serverPort + "/actuator/health";
		try {
			restTemplate.getForObject(url, String.class);
			log.debug("Health check ping succeeded");
		} catch (Exception e) {
			log.warn("Health check ping failed: {}", e.getMessage());
		}
	}
}
