package com.beraldo.twstreaming.networking;

import com.google.gson.annotations.SerializedName;

public class Response {
    @SerializedName("status")
    public String status;

    @SuppressWarnings({"unused", "used by Retrofit"})
    public Response() {
    }

    public Response(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
