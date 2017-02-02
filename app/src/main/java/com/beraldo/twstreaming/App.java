package com.beraldo.twstreaming;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterSession;

import io.fabric.sdk.android.Fabric;

/**
 * Created by Filippo Beraldo on 23/01/2017.
 * http://github.com/beraldofilippo
 */

public class App extends Application {
    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = "MY_TWITTER_KEY";
    private static final String TWITTER_SECRET = "MY_TWITTER_SECRET";
    private static App singleton;

    public static String getTwitterKey() {
        return TWITTER_KEY;
    }

    public static String getTwitterSecret() {
        return TWITTER_SECRET;
    }

    public static App getApplicationInstance() {
        return singleton;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        singleton = this;

        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig));

        Stetho.initializeWithDefaults(this);
    }

    public TwitterSession getTwitterSession() {
        return TwitterCore.getInstance()
                .getSessionManager().getActiveSession();
    }
}
