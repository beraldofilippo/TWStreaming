package com.beraldo.twstreaming;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.beraldo.twstreaming.deps.DaggerDeps;
import com.beraldo.twstreaming.deps.Deps;
import com.beraldo.twstreaming.networking.NetworkModule;

public class BaseApp extends AppCompatActivity {
    Deps deps;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        deps = DaggerDeps.builder().networkModule(new NetworkModule()).build();
    }

    public Deps getDeps() {
        return deps;
    }
}
