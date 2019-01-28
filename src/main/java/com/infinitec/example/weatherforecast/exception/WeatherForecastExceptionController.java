package com.infinitec.example.weatherforecast.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * For now, return a nice body "City not found" of 404 status when the city is not found
 */
@ControllerAdvice
public class WeatherForecastExceptionController {
	@ExceptionHandler(value = CityNotFoundException.class)
	public ResponseEntity<?> exception(CityNotFoundException ex) {
		return new ResponseEntity<>("City not found", HttpStatus.NOT_FOUND);
	}
}
