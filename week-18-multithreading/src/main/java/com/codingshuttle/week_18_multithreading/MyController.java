package com.codingshuttle.week_18_multithreading;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class MyController {

    private final ThreadPoolTaskExecutor taskExecutor;

    private final StudentService studentService;

    @GetMapping("/info")
    public ResponseEntity<Student> getStudentInfo(){

        log.info("Starting...{}",Thread.currentThread().getName());

        taskExecutor.execute(()->{ //task is running in background
            log.info("Middle...{}",Thread.currentThread().getName());
        });

        log.info("Ending...{}",Thread.currentThread().getName());

        return ResponseEntity.ok(studentService.getStudentInfo());
    }
}
