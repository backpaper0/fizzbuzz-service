package com.example;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@ConditionalOnProperty(name = "fizzbuzz.fixed-value")
@RestController
public class FixedValueController {

	private static final Logger logger = LoggerFactory.getLogger(FixedValueController.class);

	private final FizzbuzzConfig config;

	public FixedValueController(FizzbuzzConfig config) {
		this.config = config;
	}

	@GetMapping("/value")
	public Object fizzbuzz() {
		String value = config.fixedValue();
		logger.info("Fixed value: {}", value);
		return Map.of("value", value);
	}
}
