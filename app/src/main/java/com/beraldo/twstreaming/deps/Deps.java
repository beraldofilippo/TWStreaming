package com.beraldo.twstreaming.deps;

import com.beraldo.twstreaming.home.HomeActivity;
import com.beraldo.twstreaming.networking.NetworkModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {NetworkModule.class,})
public interface Deps {
    void inject(HomeActivity homeActivity);
}
