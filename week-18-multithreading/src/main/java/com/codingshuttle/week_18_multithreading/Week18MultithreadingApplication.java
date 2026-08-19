package com.codingshuttle.week_18_multithreading;

import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.Executor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.concurrent.*;

@SpringBootApplication
@Slf4j
public class Week18MultithreadingApplication {

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		SpringApplication.run(Week18MultithreadingApplication.class, args);

//		learnFuture();

//		learnCompletableFuture();
		learnCF2();
		log.info("End of main method - {}", Thread.currentThread().getName());
	}

	public static void learnThread(){

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

	public static void learnFuture() throws ExecutionException, InterruptedException {
		try(ExecutorService executorService = Executors.newFixedThreadPool(4)){

			Future<String> myNameFuture = executorService.submit(()-> getName());
			myNameFuture.get(); //blocks the calling thread

			log.info("After task ...{}", Thread.currentThread().getState());
		}
	}

	public static void learnCompletableFuture() throws ExecutionException, InterruptedException {

		CompletableFuture<String> myNameCF = CompletableFuture
				.supplyAsync(()-> getName())
					.thenApply(name-> name.toUpperCase())
							.thenApply(upperCaseName -> upperCaseName.length())
									.thenApplyAsync( lengthOfName -> {
										log.info("Inside the method with length");
										if(true) throw new RuntimeException("Faking an error,");
										return "length was "+lengthOfName;
									})
											.exceptionally((err)->{
												return "Default value in case of failure";
											});

		myNameCF.thenAccept(name -> {
			log.info("Got the name: {}", name);
		});
	}

	public static void learnCF2(){
		CompletableFuture<String> nameFuture = CompletableFuture.supplyAsync(()->getName());
		CompletableFuture<String> addressFuture = CompletableFuture.supplyAsync(()->getAddress());

		CompletableFuture.allOf(nameFuture,addressFuture);
		log.info("Got the name: {} and address here: {}", nameFuture.join(), addressFuture.join());
	}

	public static String getName(){
		{
			try{
				log.info("Entering nameFuture task ... {}", Thread.currentThread().getName());
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
			log.info("Ending nameFuture task ...{}", Thread.currentThread().getName());
			return "Yash";
		}
	}

	public static String getAddress(){
		{
			try{
				log.info("Inside AddressFuture task ... {}", Thread.currentThread().getName());
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
			log.info("Ending Inside AddressFuture task ...{}", Thread.currentThread().getName());
			return "New Delhi";
		}
	}
}
