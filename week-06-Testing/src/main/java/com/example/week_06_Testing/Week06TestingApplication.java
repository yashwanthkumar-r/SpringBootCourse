package com.example.week_06_Testing;

import com.example.week_06_Testing.services.DataService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@RequiredArgsConstructor
@SpringBootApplication
public class Week06TestingApplication implements CommandLineRunner {

//	private final DataService dataService;

	@Value("${my.variable}")
	private String myVariable;

	public static void main(String[] args) {
		SpringApplication.run(Week06TestingApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("my variable: "+ myVariable);
//		System.out.println("This data is: "+ dataService.getData());
	}

}
