package com.task.csv;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.regex.Pattern;

@SpringBootApplication
public class CsvServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CsvServiceApplication.class, args);
	}

}
