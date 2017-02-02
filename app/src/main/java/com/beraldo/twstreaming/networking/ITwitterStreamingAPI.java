package com.beraldo.twstreaming.networking;


import okhttp3.ResponseBody;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Streaming;
import rx.Observable;

public interface ITwitterStreamingAPI {
    @Streaming
    @POST("statuses/filter.json")
    Observable<ResponseBody> getStreamingStatuses(@Query("track") String track, @Query("filter_level") String filterLevel);

}
