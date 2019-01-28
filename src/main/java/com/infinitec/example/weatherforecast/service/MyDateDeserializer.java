package com.infinitec.example.weatherforecast.service;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.concurrent.TimeUnit;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

public class MyDateDeserializer extends StdDeserializer<LocalDateTime> {
	private static final long serialVersionUID = 7913999124114942264L;

	public MyDateDeserializer() {
		this(null);
	}
	
	public MyDateDeserializer(Class<?> vc) {
		super(vc);
	}

	@Override
	public LocalDateTime deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
		long seconds = Long.parseLong(p.getText());
		return LocalDateTime.ofInstant(Instant.ofEpochMilli(TimeUnit.SECONDS.toMillis(seconds)), ZoneOffset.UTC);
	}
}
