package com.infinitec.example.weatherforecast.model;

public class AverageTemperatureAndPressure {

	private final double avrgDayTimeTemperature;
	private final double avrgNightTimeTemperature;
	private final double avrgPressure;

	public AverageTemperatureAndPressure(double avrgDayTimeTemperature, 
			double avrgNightTimeTemperature,
			double avrgPressure) {
		this.avrgDayTimeTemperature = avrgDayTimeTemperature;
		this.avrgNightTimeTemperature = avrgNightTimeTemperature;
		this.avrgPressure = avrgPressure;
	}
	
	public double getAvrgDayTimeTemperature() {
		return avrgDayTimeTemperature;
	}

	public double getAvrgNightTimeTemperature() {
		return avrgNightTimeTemperature;
	}

	public double getAvrgPressure() {
		return avrgPressure;
	}

	@Override
	public String toString() {
		return "AverageTemperatureAndPressure [avrgDayTimeTemperature=" + avrgDayTimeTemperature
				+ ", avrgNightTimeTemperature=" + avrgNightTimeTemperature + ", avrgPressure=" + avrgPressure + "]";
	}
}
