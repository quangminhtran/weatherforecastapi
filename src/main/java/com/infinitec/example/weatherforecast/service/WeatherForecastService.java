package com.infinitec.example.weatherforecast.service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.infinitec.example.weatherforecast.SecretManager;
import com.infinitec.example.weatherforecast.model.AverageTemperatureAndPressure;
import com.infinitec.example.weatherforecast.utils.DateTimeUtils;

/**
 * This service provides information about weather forecast.
 */
@Service
public class WeatherForecastService {
	private final RestTemplate restTemplate;
	
	public WeatherForecastService(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}
	
	/**
	 * Returns the average day time temperature, night time temperature and pressure
	 * Note: here the current date is passed as argument so that we can unit test 
	 * this method more easily by fixing the current date. Otherwise, the current date would vary
	 * depending on when the test is executed.
	 * 
	 * @param city the city of interest
	 * @param currentDate the current date 
	 * @param nextDays the next days relative to the current date
	 */
	public AverageTemperatureAndPressure getAverageTemperatureAndPressure(String city, LocalDate currentDate, int nextDays) {
		final String rootEndpoint = "http://api.openweathermap.org/data/2.5/forecast?APPID=" + SecretManager.getAPIKey() + "&mode=json&units=metric";
		final String endpoint = rootEndpoint + "&q=" + city;
		// Retrieve weather forecast of the next days
		OpenWeatherData owpl = restTemplate.getForObject(endpoint, OpenWeatherData.class);

		// Only keep the forecasts of the next days and filter out the remaining entries
		List<ForecastEntry> forecastEntriesOfNextDays = owpl.getForecastEntries().stream()
				.filter((forecastEntry) -> DateTimeUtils.dateTimeIsPartOfTheNextDays(currentDate, forecastEntry.getDateTime(), nextDays)).collect(Collectors.toList());

		// Average pressure 
		double averagePressure = forecastEntriesOfNextDays.stream()
			.mapToDouble((forecastEntry) -> forecastEntry.getMain().getPressure()).average().orElse(0.0);

		double averageDayTimeTemperature = forecastEntriesOfNextDays.stream()
			.filter((forecastEntry) -> DateTimeUtils.isDayTime(forecastEntry.getDateTime()))
			.mapToDouble((forecastEntry) -> forecastEntry.getMain().getTemperature())
			.average().orElse(0.0);

		double averageNightTimeTemperature = forecastEntriesOfNextDays.stream()
				.filter((forecastEntry) -> !DateTimeUtils.isDayTime(forecastEntry.getDateTime()))
				.mapToDouble((forecastEntry) -> forecastEntry.getMain().getTemperature())
				.average().orElse(0.0);
		
		return new AverageTemperatureAndPressure(averageDayTimeTemperature, averageNightTimeTemperature, averagePressure);
	}
}
