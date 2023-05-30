package com.sumit.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.stream.Collector;

@RestController
public class DemoController2 {

    @GetMapping(value = "/demo3",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> demo(){
        Flux<String> f1=Flux.just("amit","amit","sumit","shubham","amit","pandurang","ram");

        Flux<String> f2=Flux.just("abc","sdfg","ieioj","sdfgdsf");

        //here spring boot attaching subscriber
        return f1
//                .filter(s->s.length()>4)
//                .distinct()
//                .distinctUntilChanged()
//                .map(String::length)
//                .flatMap(s->Flux.just(s.split("")))
//                .doOnNext(System.out::println)
//                .collectList()
//                .concatWith(f2)
//                .thenMany(f2)  // only returns the value of f2
                .zipWith(f2,(x,y)->x+"="+y)
                .log();
    }
}
