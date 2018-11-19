package com.sample.sanketshah.network;

import com.google.gson.JsonObject;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiCallInterface {

    @GET("coinlist")
    io.reactivex.Observable<JsonObject> getCoinList();

    @GET("price")
    Observable<JsonObject> getCalculatedPrice(@Query("fsym") String val, @Query("tsyms") String val1);
}
