package com.infinitec.example.weatherforecast.utils;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.junit.Test;

import com.infinitec.example.weatherforecast.utils.DateTimeUtils;

public class DateTimeUtilsTest {

	@Test
	public void todayIsNotPartOfTheNextDays() {
		LocalDate todayDate = LocalDate.now();
		LocalDateTime todayDateTime = LocalDateTime.now(ZoneOffset.UTC);
		assertFalse(DateTimeUtils.dateTimeIsPartOfTheNextDays(todayDate, todayDateTime, 1));
	}

	@Test
	public void tomorrowIsPartOfNextThreeDays() {
		LocalDate todayDate = LocalDate.now();
		LocalDateTime tomorrow = LocalDateTime.now(ZoneOffset.UTC).plusDays(1);
		assertTrue(DateTimeUtils.dateTimeIsPartOfTheNextDays(todayDate, tomorrow, 3));
	}

	@Test
	public void fourthDayFromTodayIsNotPartOfNextThreeDays() {
		LocalDate todayDate = LocalDate.now();
		LocalDateTime tomorrow = LocalDateTime.now(ZoneOffset.UTC).plusDays(4);
		assertFalse(DateTimeUtils.dateTimeIsPartOfTheNextDays(todayDate, tomorrow, 3));
	}
	
	@Test
	public void dayTimeCorrectlyDetectedTest() {
		assertTrue(DateTimeUtils.isDayTime(LocalDateTime.of(2019, 1, 27, 6, 0)));
		assertTrue(DateTimeUtils.isDayTime(LocalDateTime.of(2019, 1, 27, 17, 59)));
	}

	@Test
	public void nightTimeCorrectlyDetectedTest() {
		assertFalse(DateTimeUtils.isDayTime(LocalDateTime.of(2019, 1, 27, 18, 0)));
		assertFalse(DateTimeUtils.isDayTime(LocalDateTime.of(2019, 1, 27, 5, 59)));
	}
}
