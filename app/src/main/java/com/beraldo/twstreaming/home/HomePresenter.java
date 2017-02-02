package com.beraldo.twstreaming.home;

import com.beraldo.twstreaming.models.Status;
import com.beraldo.twstreaming.networking.Service;

import rx.Subscriber;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public class HomePresenter {
    private final Service service;
    private final HomeView view;
    private CompositeSubscription subscriptions;

    private String trackSubject;

    public HomePresenter(Service service, HomeView view) {
        this.service = service;
        this.view = view;
        this.subscriptions = new CompositeSubscription();

        trackSubject = "ronaldo";
    }

    public void setTrackSubject(String track) {
        trackSubject = track;
    }

    public void getStatuses() {
        if (subscriptions.hasSubscriptions()) subscriptions.clear();

        Subscription subscription = service.getStatus(new Subscriber<Status>() {
                                                          @Override
                                                          public void onCompleted() {

                                                          }

                                                          @Override
                                                          public void onError(Throwable e) {
                                                              view.onFailure(e.getLocalizedMessage());
                                                          }

                                                          @Override
                                                          public void onNext(Status status) {
                                                              view.onStatusSuccess(status);
                                                          }
                                                      },
                trackSubject);

        subscriptions.add(subscription);
    }

    public void onStop() {
        subscriptions.unsubscribe();
    }
}
