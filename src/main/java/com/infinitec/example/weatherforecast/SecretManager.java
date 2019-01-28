package com.infinitec.example.weatherforecast;

public class SecretManager {
	public static String getAPIKey() {
		final String key = System.getenv("OWM_API_KEY");
		return key;
	}
}
