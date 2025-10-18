package com.hanuman.event.service;

import org.springframework.stereotype.Service;

import reactor.core.publisher.Mono;

@Service
public class ApplicationService {

    public Mono<String> getData(){
        // throw new EntityNotFoundException("Not found");
        return Mono.just("Hello");
    }

}
