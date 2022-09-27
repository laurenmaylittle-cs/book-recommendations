package com.bestreads.bookrecommendations;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class BookRecommendationsApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookRecommendationsApplication.class, args);
	}

}
