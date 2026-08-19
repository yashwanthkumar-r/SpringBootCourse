package com.codingshuttle.week_18_multithreading;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MyScheduler {

 //   @Scheduled(fixedRate = 200) //not concurrent
//    @Scheduled(fixedDelay = 2000, initialDelay = 1000)
 //   @Scheduled(cron = "*/5 * * * * *")
    public void logMe() throws InterruptedException {
        log.info("Scheduled Logging started...{}", Thread.currentThread().getName());

        Thread.sleep(1000);

        log.info("Scheduled Logging ended...{}", Thread.currentThread().getName());
    }

    @Scheduled(fixedRate = 200)
    @Async("jobExecutor")
    public void logMe2() throws InterruptedException {
        log.info("Scheduled Logging2 started...{}", Thread.currentThread().getName());

        Thread.sleep(1000);

        log.info("Scheduled Logging2 ended...{}", Thread.currentThread().getName());
    }
}
