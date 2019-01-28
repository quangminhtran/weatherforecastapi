package com.infinitec.example.weatherforecast.service;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.infinitec.example.weatherforecast.SecretManager;
import com.infinitec.example.weatherforecast.model.AverageTemperatureAndPressure;
import com.infinitec.example.weatherforecast.service.ForecastEntry;
import com.infinitec.example.weatherforecast.service.MainData;
import com.infinitec.example.weatherforecast.service.OpenWeatherData;
import com.infinitec.example.weatherforecast.service.WeatherForecastService;

@RunWith(SpringRunner.class)
@RestClientTest(WeatherForecastService.class)
public class WeatherForecastServiceTest {
	private final String city = "Berlin";

    @Autowired
    private WeatherForecastService forecastService;
 
    private MockRestServiceServer server;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;
 
    private LocalDate currentDate;
    @Before
    public void setUp() throws Exception {
    	server = MockRestServiceServer.createServer(restTemplate);
    	
    	List<ForecastEntry> forecastEntries = new ArrayList<ForecastEntry>();
    	// Day time: 6:00 of Jan 28th
    	forecastEntries.add(new ForecastEntry(LocalDateTime.of(2019, 1, 28, 6, 0), new MainData(2, 3)));
    	// Day time: 9:00 of Jan 28th
    	forecastEntries.add(new ForecastEntry(LocalDateTime.of(2019, 1, 28, 9, 0), new MainData(4, 5)));

    	// Night time: 1:00 of Jan 28th
    	forecastEntries.add(new ForecastEntry(LocalDateTime.of(2019, 1, 28, 1, 0), new MainData(6, 7)));
    	// Night time: 3:00 of Jan 28th
    	forecastEntries.add(new ForecastEntry(LocalDateTime.of(2019, 1, 28, 3, 0), new MainData(8, 9)));
    	
    	OpenWeatherData weatherData = new OpenWeatherData();
    	weatherData.setForecastEntries(forecastEntries);

        String weatherDataString = 
          objectMapper.writeValueAsString(weatherData);
   
    	final String endpoint = "http://api.openweathermap.org/data/2.5/forecast?APPID=" + SecretManager.getAPIKey() + "&mode=json&units=metric" + "&q=" + city;
        this.server.expect(requestTo(endpoint))
          .andRespond(withSuccess(weatherDataString, MediaType.APPLICATION_JSON));
    }

	@Test
	public void dayTimeAndNightTimeAreDetectedCorrectlyTest() {
		// Assume current date is Jan 27th 2019
		currentDate = LocalDate.of(2019, 01, 27);
		AverageTemperatureAndPressure atp = forecastService.getAverageTemperatureAndPressure(city, currentDate, 3);
		
		assertEquals(3, atp.getAvrgDayTimeTemperature(), 0.000000001);
		assertEquals(7, atp.getAvrgNightTimeTemperature(), 0.000000001);
		assertEquals(6, atp.getAvrgPressure(), 0.000000001);
	}

	@Test
	public void noValidForecastsReturnZeroResultTest() {
		// Assume current date is Jan 28th 2019
		currentDate = LocalDate.of(2019, 01, 28);
		AverageTemperatureAndPressure atp = forecastService.getAverageTemperatureAndPressure(city, currentDate, 3);
		
		assertEquals(0, atp.getAvrgDayTimeTemperature(), 0.000000001);
		assertEquals(0, atp.getAvrgNightTimeTemperature(), 0.000000001);
		assertEquals(0, atp.getAvrgPressure(), 0.000000001);
	}
}
