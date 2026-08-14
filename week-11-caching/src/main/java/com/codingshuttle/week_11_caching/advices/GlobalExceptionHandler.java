package com.codingshuttle.week_11_caching.advices;

import com.codingshuttle.week_11_caching.Exceptions.ResourceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.StaleObjectStateException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleResourceNotFoundException(ResourceNotFoundException ex){
        log.error("Resource not found: {}", ex.getLocalizedMessage());
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(StaleObjectStateException.class)
    public ResponseEntity<?> handleStaleObjectStateException(StaleObjectStateException ex){
        log.error(ex.getLocalizedMessage());
        return new ResponseEntity<>("Stale data\n", HttpStatus.CONFLICT);
    }

//    @ExceptionHandler(RuntimeException.class)
//    public ResponseEntity<?> handleRuntimeException(RuntimeException ex){
//        log.error("Runtime exception occurred: {}", ex.getLocalizedMessage());
//        return ResponseEntity.internalServerError().build();
//    }
}
