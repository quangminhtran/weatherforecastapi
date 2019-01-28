package com.infinitec.example.weatherforecast.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * This class provides util functions for dealing with date time
 *
 */
public class DateTimeUtils {
	
	/**
	 * Return trues if a given date time represented by this calendar instance 
	 * is a time point in the next given days starting from tomorrow.
	 * For example, if today Jan 27th 2019 and nextDays = 3, the method returns true
	 * if {@code dateTime} is any time point between Jan 28th 2019 and Jan 30th 2019
	 * 
	 * @param currentDate the current date
	 * @param dateTime the given date time in UTC
	 * @param nextDays the number of next days.
	 * @return true if a given date time is a time point in the next given days 
	 * 				starting from tomorrow and false otherwise
	 */
	public static boolean dateTimeIsPartOfTheNextDays(LocalDate currentDate, LocalDateTime dateTime, int nextDays) {
		LocalDateTime todayMidnight = LocalDateTime.of(currentDate, LocalTime.MIDNIGHT);
		LocalDateTime tomorrowMidnight = todayMidnight.plusDays(1);
		LocalDateTime dayAfterNextDaysMidnight = todayMidnight.plusDays(nextDays + 1);
		return dateTime.isAfter(tomorrowMidnight) && dateTime.isBefore(dayAfterNextDaysMidnight);
	}


	/**
	 * Check if a local date time is day time (between 6:00 and 18:00)
	 * 
	 * @param dateTime The 
	 * @return true if the date time is day time and false otherwise.
	 */
	public static boolean isDayTime(LocalDateTime dateTime) {
		return dateTime.getHour() >= 6 && dateTime.getHour() < 18;
	}
}
