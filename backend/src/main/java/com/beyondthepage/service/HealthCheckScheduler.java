package com.beyondthepage.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@ConditionalOnProperty(name = "healthcheck.enabled", havingValue = "true")
public class HealthCheckScheduler {

	private static final Logger log = LoggerFactory.getLogger(HealthCheckScheduler.class);

	@Value("${healthcheck.service-url}/actuator/health")
	private String serviceUrl;

	private final RestTemplate restTemplate = new RestTemplate();

	// Every 5 minutes — keeps the Render free-tier instance from spinning down
	@Scheduled(fixedRateString = "300000")
	public void ping() {
		try {
			restTemplate.getForObject(serviceUrl, String.class);
			log.debug("Health check ping succeeded: {}", serviceUrl);
		} catch (Exception e) {
			log.warn("Health check ping failed: {}", e.getMessage());
		}
	}
}
