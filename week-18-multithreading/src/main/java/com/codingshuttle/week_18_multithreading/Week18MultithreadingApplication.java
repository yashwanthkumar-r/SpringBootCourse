package com.codingshuttle.week_18_multithreading;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class Week18MultithreadingApplication {

	public static void main(String[] args) throws InterruptedException {
		SpringApplication.run(Week18MultithreadingApplication.class, args);
		log.info("Before entering to the t1 thread - {}", Thread.currentThread().getName());

		Thread t1 = new Thread(()->{
			log.info("this is inside the thread - {}", Thread.currentThread().getName());
		});
		t1.start();

		t1.join();
		log.info("After executing t1 thread - {}", Thread.currentThread().getName());
	}

}
