package com.sumit.subscriber;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;


//custom subscriber
public class CustomSubscriber implements Subscriber<Integer> {
    private Subscription subscription;

    @Override
    public void onSubscribe(Subscription subscription) {
        System.out.println("subscribed");
        this.subscription=subscription;
        subscription.request(1); //backpressure
    }


    @Override
    public void onNext(Integer integer) {
        System.out.println("on Next "+integer);
        subscription.request(1);
    }

    @Override
    public void onError(Throwable t) {
        System.out.println("On Error "+t);
    }

    @Override
    public void onComplete() {
        System.out.println("On Complete");
    }
}
