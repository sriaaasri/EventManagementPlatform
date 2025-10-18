package com.hanuman.event.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hanuman.event.service.ApplicationService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@RestController
@AllArgsConstructor
@Slf4j
public class AppController {

    private ApplicationService service;
    // private final Logger logger = LoggerFactory.getLogger(AppController.class);
    // private Logger logger;

    @GetMapping()
    public Mono<String> getData(){
        
        log.info("INFO message");
        log.warn("WARN message");
        log.error("ERROR message");
        log.debug("DEBUG message");
        log.trace("TRACE message");

        // return Mono.just("Hi from Event Management application");
        throw new RuntimeException("Exception in controller message");


      } 
      
    public String print(String input){
        return input;

    }
}
