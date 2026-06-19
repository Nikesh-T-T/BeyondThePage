package com.beyondthepage.config;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.time.YearMonth;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;

@Configuration
public class JacksonConfig {

	@Bean
	public Jackson2ObjectMapperBuilderCustomizer jacksonCustomizer() {
		return builder -> {
			builder.modules(new JavaTimeModule());
			builder.featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		};
	}

	@Bean
	public Converter<String, YearMonth> stringToYearMonthConverter() {
		return new Converter<String, YearMonth>() {
			@Override
			public YearMonth convert(String source) {
				return YearMonth.parse(source);
			}
		};
	}
}
