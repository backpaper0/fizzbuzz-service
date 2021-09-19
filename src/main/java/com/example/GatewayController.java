package com.example;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.annotation.JsonProperty;

@ConditionalOnMissingBean(FixedValueController.class)
@RestController
public class GatewayController {

	private static final Logger logger = LoggerFactory.getLogger(GatewayController.class);

	private final FizzbuzzConfig config;
	private final RestTemplate http;
	private final ParameterizedTypeReference<Map<String, String>> responseType = new ParameterizedTypeReference<>() {
	};

	public GatewayController(FizzbuzzConfig config, RestTemplate http) {
		this.config = config;
		this.http = http;
	}

	@GetMapping("/{parameter}")
	public Object gateway(@PathVariable int parameter) {
		logger.info("Parameter: {}", parameter);
		Map<String, String> returnValue;
		if (parameter % 15 == 0) {
			FixedValue fizz = fizz();
			FixedValue buzz = buzz();
			returnValue = Map.of("value", fizz.value + buzz.value,
					fizz.value, fizz.id,
					buzz.value, buzz.id,
					"gateway", config.id());
		} else if (parameter % 3 == 0) {
			FixedValue fizz = fizz();
			returnValue = Map.of("value", fizz.value,
					fizz.value, fizz.id,
					"gateway", config.id());
		} else if (parameter % 5 == 0) {
			FixedValue buzz = buzz();
			returnValue = Map.of("value", buzz.value,
					buzz.value, buzz.id,
					"gateway", config.id());
		} else {
			returnValue = Map.of("value", String.valueOf(parameter),
					"gateway", config.id());
		}
		logger.info("Return value: {}", returnValue.get("value"));
		return returnValue;
	}

	private FixedValue fizz() {
		return get(config.fizzUri());
	}

	private FixedValue buzz() {
		return get(config.buzzUri());
	}

	private FixedValue get(String uri) {
		return http.getForObject(uri, FixedValue.class);
	}

	public static class FixedValue {

		final String id;
		final String value;

		public FixedValue(@JsonProperty("id") String id, @JsonProperty("value") String value) {
			this.id = id;
			this.value = value;
		}
	}

}
