package com.aroha.assignment1.task;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class DateFunctions {
	public static void main(String[] args) {
		System.out.println("----------Date functions--------");
		LocalDate date = LocalDate.now(); // Date only

		int getCurrentMonth = date.getDayOfMonth();
		int getCurrentyear = date.getDayOfYear();
		DayOfWeek getCurrentweek = date.getDayOfWeek();

		System.out.println("getCurrentMonth :" + getCurrentMonth);
		System.out.println("getCurrentyear :" + getCurrentyear);
		System.out.println("getCurrentweek :" + getCurrentweek);

		System.out.println("----- time function--------------");

		LocalTime time = LocalTime.now(); // Time only
		System.out.println("LocalTime--current time :" + time);

		int currentMinute = time.getMinute();
		System.out.println("current minute :" + currentMinute);
		int curretHour = time.getHour();
		System.out.println("current hour :" + curretHour);
		int currentSecond = time.getSecond();
		System.out.println("get currentSecond :" + currentSecond);
		System.out.println("------------------------------------");
		LocalDateTime dateTime = LocalDateTime.now(); // date and time
		System.out.println("LocalDateTime-- today's date and time  :" + dateTime);

		System.out.println("------------getting different times by regions----------------");

		ZonedDateTime indiaTime = ZonedDateTime.now(ZoneId.of("Asia/Kolkata"));
		ZonedDateTime amTime = ZonedDateTime.now(ZoneId.of("America/New_York"));
		ZonedDateTime londonTime = ZonedDateTime.now(ZoneId.of("Europe/London"));
		ZonedDateTime asiaDhaka = ZonedDateTime.now(ZoneId.of("Asia/Dhaka"));

		System.out.println("Asia Dhaka: " + asiaDhaka);
		System.out.println("India Time: " + indiaTime);
		System.out.println("New York Time: " + amTime);
		System.out.println("London Time: " + londonTime);

		System.out.println("------------for getting am or pm----------------------");
		ZonedDateTime indiaTime1 = ZonedDateTime.now(ZoneId.of("Asia/Kolkata"));
		ZonedDateTime amTime1 = ZonedDateTime.now(ZoneId.of("America/New_York"));
		ZonedDateTime londonTime1 = ZonedDateTime.now(ZoneId.of("Europe/London"));

//		ZoneId.getAvailableZoneIds();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a z");
		DateTimeFormatter.ofPattern("hh/mm");
		System.out.println("India Time: " + indiaTime.format(formatter));
		System.out.println("New York Time: " + amTime.format(formatter));
		System.out.println("London Time: " + londonTime.format(formatter));

		LocalDateTime now = LocalDateTime.now(); // 2025-06-13T01:30:45 (example)
		DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

		String formattedDate = now.format(formatter1);
		System.out.println("Formatted Date: " + formattedDate);
		
		
		System.out.println("-------------------------------");
		LocalDate today = LocalDate.now();
		LocalDate birthday = LocalDate.of(2020, 9, 19);

		long daysBetween = ChronoUnit.DAYS.between(birthday, today);
		long yearsBetween = ChronoUnit.YEARS.between(birthday, today);

		System.out.println("Days between: " + daysBetween);
		System.out.println("Years between: " + yearsBetween);

		LocalDate shipDate = LocalDate.of(2025, 6, 12);
		LocalDate deliveryDate = LocalDate.of(2025, 6, 14);

		long daysBetweenShipment = ChronoUnit.DAYS.between(shipDate, deliveryDate);
		long monthsBetween = ChronoUnit.MONTHS.between(shipDate, deliveryDate);
		long weeksBetween = ChronoUnit.WEEKS.between(shipDate, deliveryDate);
		System.out.println("----------shipment date----------");
		System.out.println("Days between: " + daysBetweenShipment); // 10
		System.out.println("Weeks between: " + weeksBetween); // 1
		System.out.println("Months between: " + monthsBetween); // 0

		//ChronoUnit.
	}
}
