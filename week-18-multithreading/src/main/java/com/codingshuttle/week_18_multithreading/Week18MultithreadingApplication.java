package com.codingshuttle.week_18_multithreading;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.concurrent.*;

@SpringBootApplication
@Slf4j
public class Week18MultithreadingApplication {

	public static void main(String[] args) throws InterruptedException {
		SpringApplication.run(Week18MultithreadingApplication.class, args);

		ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(4, 6, 2,
				TimeUnit.SECONDS, new ArrayBlockingQueue<>(10),
				new RejectedExecutionHandler() {
					@Override
					public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
						log.info("Thread rejected... retrying...");
						try {
							Thread.sleep(2000);
						} catch (InterruptedException e) {
							throw new RuntimeException(e);
						}
						executor.submit(r);
					}
				});
		log.info("Starting the main thread {}", Thread.currentThread().getName());


		ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(4,
				new ThreadFactory() {
					@Override
					public Thread newThread(Runnable r) {
						log.info("threads created....");
						return new Thread(r, "thread "+System.nanoTime());
					}
				});

		scheduledThreadPoolExecutor
				.schedule(new LongRunningTask("scheduled class"),
						2, TimeUnit.SECONDS);

//		for (int i=0; i<20; i++){
//		threadPoolExecutor.submit(new LongRunningTask(i + ""));
//	//	Thread.sleep(1000);
//		}

		log.info("Ending the main thread {}", Thread.currentThread().getName());





/*		log.info("Before entering to the t1 thread - {}", Thread.currentThread().getName());
		Thread t1 = new Thread(()->{
			log.info("this is inside the thread - {}", Thread.currentThread().getName());
		});
		t1.start();
		t1.join();
		log.info("After executing t1 thread - {}", Thread.currentThread().getName());*/
	}
}
