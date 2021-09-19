package com.example;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@ConditionalOnMissingBean(FixedValueController.class)
@RestController
public class GatewayController {

	private static final Logger logger = LoggerFactory.getLogger(GatewayController.class);

	private final FizzbuzzConfig config;
	private final RestTemplate http;

	public GatewayController(FizzbuzzConfig config, RestTemplate http) {
		this.config = config;
		this.http = http;
	}

	@GetMapping("/{parameter}")
	public Object gateway(@PathVariable int parameter) {
		logger.info("Parameter: {}", parameter);
		String returnValue;
		if (parameter % 15 == 0) {
			returnValue = fizz() + buzz();
		} else if (parameter % 3 == 0) {
			returnValue = fizz();
		} else if (parameter % 5 == 0) {
			returnValue = buzz();
		} else {
			returnValue = String.valueOf(parameter);
		}
		logger.info("Return value: {}", returnValue);
		return Map.of("value", returnValue);
	}

	private String fizz() {
		return get(config.fizzUri());
	}

	private String buzz() {
		return get(config.buzzUri());
	}

	private String get(String uri) {
		return http.getForObject(uri, Map.class).get("value").toString();
	}
}
