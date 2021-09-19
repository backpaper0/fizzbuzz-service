package com.example;

import java.util.Objects;
import java.util.UUID;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConstructorBinding
@ConfigurationProperties(prefix = "fizzbuzz")
public class FizzbuzzConfig {

	private final String id = UUID.randomUUID().toString().substring(0, 8);
	private final String fixedValue;
	private final String fizzUri;
	private final String buzzUri;

	public FizzbuzzConfig(String fixedValue, String fizzUri, String buzzUri) {
		this.fixedValue = fixedValue;
		this.fizzUri = fizzUri;
		this.buzzUri = buzzUri;

		if (fixedValue == null) {
			Objects.requireNonNull(fizzUri);
			Objects.requireNonNull(buzzUri);
		}
	}

	public String id() {
		return id;
	}

	public String fixedValue() {
		return fixedValue;
	}

	public String fizzUri() {
		return fizzUri;
	}

	public String buzzUri() {
		return buzzUri;
	}
}
