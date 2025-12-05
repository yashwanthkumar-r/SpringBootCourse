package com.codingshuttle.yash.modules.module1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class Module1IntroApplication implements CommandLineRunner {

	@Autowired //injecting the bean created by spring
	PaymentService paymentService1;

	@Autowired //injecting the bean created by spring
	PaymentService paymentService2;

//	//@Autowired
//	private final NotificationService notificationService;
//
//	//Constructor DI
//	public Module1IntroApplication(NotificationService notificationService){
//		this.notificationService = notificationService;
//	}
	@Autowired
	Map<String, NotificationService> notificationServiceMap = new HashMap<>();


	public static void main(String[] args) {
		SpringApplication.run(Module1IntroApplication.class, args);
	}

		@Override
		public void run(String... args) throws Exception {
//		System.out.println(paymentService1.hashCode());
//		System.out.println(paymentService2.hashCode());
//		paymentService1.pay();
//		paymentService2.pay();

		/*NotificationService notificationService = new SmsNotificationService();
		notificationService.send("hello, good work!!");*/

		//notificationService.send("hello");

		for(var notificationService:notificationServiceMap.entrySet()){
			System.out.println(notificationService.getKey());
			notificationService.getValue().send("Hello");
		}


	}
}
