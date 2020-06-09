package com.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

//@SpringBootApplication //when use datasource in application.yml
@SpringBootApplication(exclude={DataSourceAutoConfiguration.class}) //when don't use datasource
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
