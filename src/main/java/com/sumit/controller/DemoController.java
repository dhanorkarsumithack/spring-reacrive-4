package com.sumit.controller;

import com.sumit.publisher.CustomPublisher;
import com.sumit.subscriber.CustomSubscriber;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@RestController
public class DemoController {

    // custom subscriber
    @GetMapping("/demo")
    public void demo(){
        var f1=  Flux.just(1,2,3,4,5,6,6);
//        var f2= Flux.fromStream(Stream.of(1,2,4,6,7,5));
//        var f3=Flux.fromIterable(Set.of(1,2,3,4,5,6,7));
//        var m1= Mono.just(1);
//        f1.doOnNext(System.out::println) //will not execute because no subscriber
//                .subscribe(System.out::println);

        //firstly from the subscriber method will print after that from doOnNext()

        f1.doOnNext(c->{throw new RuntimeException("nooooooooooo");})
                .subscribe(new CustomSubscriber());
    }


    //custom publisher

    @GetMapping("/demo2")
    public void demo2(){

//        Flux<Integer> f1=Flux.just(1,2,3,4,5); //publisher

        //publisher -> this will push the event
//
//        f1.subscribe(System.out::println); //subscriber-> can request no of values
//
//        //stream api
//        Stream<Integer> s1=Stream.of(1,2,3,4,5);
//
//        s1.forEach(System.out::println);

        CustomPublisher f2=new CustomPublisher(List.of(1,2,3,4,5,6)); //custom publisher

        f2.subscribe(new CustomSubscriber()); //custom subscriber

        Flux<String> f1=Flux.create(s->{ //sink
            for (int i = 0; i < 10; i++) {
                s.next(UUID.randomUUID().toString());
            }
            s.complete();
//            s.error(new RuntimeException());
        });

        f1.log()
                .subscribe(new Subscriber<String>() {

                    private Subscription subscription;
                    private int i;
                    @Override
                    public void onSubscribe(Subscription subscription) {
                        this.subscription=subscription;
                        this.subscription.request(2);

                    }

                    @Override
                    public void onNext(String s) {
                        System.out.println(s);
                        i++;
                        if(i%2==0){
                            this.subscription.request(2);
                        }                    }

                    @Override
                    public void onError(Throwable t) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });  //request(indefinite)


        f1.subscribe(System.out::println);
        f1.subscribe(System.out::println);
        f1.subscribe(System.out::println);


        Stream<Integer> s1=Stream.of(1,2,3,4,5); //cannot be reused like flux
        //we can have many subscriber

        s1.forEach(System.out::println);
        s1.forEach(System.out::println);

    }



}
