package com.infinitec.example.weatherforecast.controller;

import java.time.LocalDate;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.infinitec.example.weatherforecast.model.AverageTemperatureAndPressure;
import com.infinitec.example.weatherforecast.service.WeatherForecastService;

@RestController
public class WeatherForecastController {
	@Autowired
	private WeatherForecastService forecastService;
	
	@RequestMapping(value = "/data", params="city", method = RequestMethod.GET)
	public AverageTemperatureAndPressure getData(@RequestParam("city") String city) {
		LocalDate todayDate = LocalDate.now(ZoneOffset.UTC);
		final AverageTemperatureAndPressure data = forecastService.getAverageTemperatureAndPressure(city, todayDate, 3);			
		return data;
	}
}
