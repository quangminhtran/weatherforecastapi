package com.infinitec.example.weatherforecast.service;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonIgnoreProperties(ignoreUnknown=true)
public class ForecastEntry {
	@JsonProperty("dt")
	@JsonSerialize(using = MyDateSerializer.class)
	@JsonDeserialize(using = MyDateDeserializer.class)
	private LocalDateTime dateTime;
	
	@JsonProperty("main")
	private MainData main;

	public ForecastEntry() {
		
	}
	
	public ForecastEntry(LocalDateTime dateTime, MainData main) {
		this.dateTime = dateTime;
		this.main = main;
	}
	
	public LocalDateTime getDateTime() {
		return dateTime;
	}

	public void setDateTime(LocalDateTime dateTime) {
		this.dateTime = dateTime;
	}

	public MainData getMain() {
		return main;
	}

	public void setMain(MainData main) {
		this.main = main;
	}

	@Override
	public String toString() {
		return "ForecastEntry [dateTime=" + dateTime + ", main=" + main + "]";
	}
}
