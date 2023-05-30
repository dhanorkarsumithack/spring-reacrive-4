package com.sumit.publisher;

import com.sumit.subscription.CustomSubscription;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.List;

public class CustomPublisher implements Publisher<Integer> {

    private final List<Integer> list;

    public CustomPublisher(List<Integer> list) {
        this.list = list;
    }


    @Override
    public void subscribe(Subscriber<? super Integer> subscriber) {

        Subscription subscription=new Subscription() {

            private int lastRequestedElements=-1;

            @Override
            public void request(long n) {
                lastRequestedElements++;
                if(lastRequestedElements<list.size()){
                    subscriber.onNext(list.get(lastRequestedElements));
                }else {
                    subscriber.onComplete();
                }
            }

            @Override
            public void cancel() {

            }
        };
        subscriber.onSubscribe(subscription); //publisher will give subscription to subscriber

    }
}
