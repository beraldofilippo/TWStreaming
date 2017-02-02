package com.beraldo.twstreaming.home;

import com.beraldo.twstreaming.models.Status;

public interface HomeView {
    void onFailure(String appErrorMessage);

    void onStatusSuccess(Status status);
}
