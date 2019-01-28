package com.infinitec.example.weatherforecast.integration;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.infinitec.example.weatherforecast.exception.CityNotFoundException;
import com.infinitec.example.weatherforecast.model.AverageTemperatureAndPressure;
import com.infinitec.example.weatherforecast.service.WeatherForecastService;

import java.time.LocalDate;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class EndpointTest {
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private WeatherForecastService forecastService;
	
	@Test
	public void callDataEndpointShouldReturnCorrectPayload() throws Exception {
		AverageTemperatureAndPressure mockedData = new AverageTemperatureAndPressure(1, 2, 3);
		when(forecastService.getAverageTemperatureAndPressure(anyString(), any(LocalDate.class), anyInt())).thenReturn(mockedData);
		mockMvc.perform(get("/data?city=Berlin"))
			    .andExpect(status().isOk())
			    .andExpect(jsonPath("$.avrgDayTimeTemperature").value("1.0"))
			    .andExpect(jsonPath("$.avrgNightTimeTemperature").value("2.0"))
				.andExpect(jsonPath("$.avrgPressure").value("3.0"));				
	}

	@Test
	public void callWithInvalidCityShouldReturnNotFound() throws Exception {
		when(forecastService.getAverageTemperatureAndPressure(anyString(), any(LocalDate.class), anyInt())).thenThrow(CityNotFoundException.class);
		mockMvc.perform(get("/data?city=BLAH"))
			    .andExpect(status().isNotFound())
			    .andExpect(content().string("City not found"));
				
	}
}
