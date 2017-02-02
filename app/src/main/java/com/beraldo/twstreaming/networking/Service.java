package com.beraldo.twstreaming.networking;


import com.beraldo.twstreaming.models.Status;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.ResponseBody;
import okio.BufferedSource;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class Service {
    final Gson gson = new Gson();
    private final ITwitterStreamingAPI ITwitterStreamingAPI;

    public Service(ITwitterStreamingAPI ITwitterStreamingAPI) {
        this.ITwitterStreamingAPI = ITwitterStreamingAPI;
    }

    /**
     * This method effectively translates strings into objects and passes them to the subscriber.
     */
    public static Observable<Status> statuses(final BufferedSource source) {
        final Gson gson = new Gson();
        return Observable.create(new Observable.OnSubscribe<Status>() {
            @Override
            public void call(Subscriber<? super Status> subscriber) {
                try {
                    while (!source.exhausted()) {
                        Status s = gson.fromJson(source.readUtf8Line(), Status.class);
                        subscriber.onNext(s);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    subscriber.onError(e);
                }
                subscriber.onCompleted();
            }
        });
    }

    /**
     * This is the core of the whole thing, since the response from the api must be a ResponseBody
     * in form of a continuous stream, I must extract the stream coming from the ResponseBody object
     * and map it into a source which is readable continuously.
     * The incoming stream separates objects from one another by returning lines separated by
     * \r\n and that's why it's necessary to truncate the stream and transform each line into a
     * Status java object which is in turn passed up to the subscriber.
     */

    public Subscription getStatus(Subscriber<Status> subscriber, String track) {
        return ITwitterStreamingAPI.getStreamingStatuses(track, "low")
                .subscribeOn(Schedulers.io())
                .flatMap(new Func1<ResponseBody, Observable<Status>>() {
                    @Override
                    public Observable<Status> call(ResponseBody responseBody) {
                        return statuses(responseBody.source());
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorResumeNext(new Func1<Throwable, Observable<? extends Status>>() {
                    @Override
                    public Observable<? extends Status> call(Throwable throwable) {
                        return Observable.error(throwable);
                    }
                })
                .subscribe(subscriber);
    }
}
