package com.beraldo.twstreaming.networking;

import com.beraldo.twstreaming.App;
import com.facebook.stetho.okhttp3.StethoInterceptor;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import se.akerfeldt.okhttp.signpost.OkHttpOAuthConsumer;
import se.akerfeldt.okhttp.signpost.SigningInterceptor;

@Module
public class NetworkModule {
    private static String baseUrl = "https://stream.twitter.com/1.1/";

    public NetworkModule() {
    }

    @Provides
    @Singleton
    Retrofit provideCall() {
        OkHttpOAuthConsumer consumer = new OkHttpOAuthConsumer(App.getTwitterKey(),
                App.getTwitterSecret());
        consumer.setTokenWithSecret(
                App.getApplicationInstance().getTwitterSession().getAuthToken().token,
                App.getApplicationInstance().getTwitterSession().getAuthToken().secret);

        Retrofit.Builder builder =
                new Retrofit.Builder()
                        .baseUrl(baseUrl)
                        .addConverterFactory(GsonConverterFactory.create())
                        .addConverterFactory(ScalarsConverterFactory.create())
                        .addCallAdapterFactory(RxJavaCallAdapterFactory.create());

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new SigningInterceptor(consumer))
                .addNetworkInterceptor(new StethoInterceptor())
                .build();

        return builder.client(client).build();
    }

    @Provides
    @Singleton
    @SuppressWarnings("unused")
    public ITwitterStreamingAPI providesNetworkService(
            Retrofit retrofit) {
        return retrofit.create(ITwitterStreamingAPI.class);
    }

    @Provides
    @Singleton
    @SuppressWarnings("unused")
    public Service providesService(
            ITwitterStreamingAPI ITwitterStreamingAPI) {
        return new Service(ITwitterStreamingAPI);
    }

}
