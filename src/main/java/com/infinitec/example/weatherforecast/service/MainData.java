package com.infinitec.example.weatherforecast.service;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown=true)
public class MainData {
	
	@JsonProperty("temp")
	private double temperatue;
	@JsonProperty("pressure")
	private double pressure;

	public MainData() {
		
	}

	public MainData(double temperature, double pressure) {
		this.temperatue = temperature;
		this.pressure = pressure;
	}

	public double getTemperature() {
		return temperatue;
	}

	public void setTemperatue(double temperatue) {
		this.temperatue = temperatue;
	}

	public void setPressure(double pressure) {
		this.pressure = pressure;
	}

	public double getPressure() {
		return pressure;
	}

	@Override
	public String toString() {
		return "MainData [temperatue=" + temperatue + ", pressure=" + pressure + "]";
	}

}
