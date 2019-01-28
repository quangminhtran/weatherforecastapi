package com.infinitec.example.weatherforecast.service;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown=true)
public class OpenWeatherData {
	@JsonProperty("list")
	private List<ForecastEntry> forecastEntries;
	
	public OpenWeatherData() {
		forecastEntries = new ArrayList<ForecastEntry>();
	}
	
	public List<ForecastEntry> getForecastEntries() {
		return forecastEntries;
	}

	public void setForecastEntries(List<ForecastEntry> forecastEntries) {
		this.forecastEntries = forecastEntries;
	}

	@Override
	public String toString() {
		return "OpenWeatherPayload [forecastEntries=" + forecastEntries + "]";
	}
	
}
