package com.codingshuttle.week_18_multithreading;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RestController
@Slf4j
public class DummyController {

    @GetMapping("/hello")
    public ResponseEntity<String> getName() throws InterruptedException{

        log.info("Tomcat Thread blocked, until it gets rersponse, atmost only 200 thread are supported");
        Thread.sleep(5000);

        return ResponseEntity.ok("Anuj");
    }

    @GetMapping("/hello-cf")
    public CompletableFuture<ResponseEntity<String>> getNameCF() throws InterruptedException{

        log.info("new thread can be assigned, and tomcat thread get free");

        return CompletableFuture.supplyAsync(()->{
            log.info("inside the future call");
            try{
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return ResponseEntity.ok("Anuj");
        }, Executors.newFixedThreadPool(4));
    }
}
