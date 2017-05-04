package com.example.jihun.copycastexample.tools;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by Jihun on 2017-05-04.
 */

public interface GoogleService {

    @POST("urlshortener/v1/url?key=YOUR_KEY_HERE")
    Call<JsonUrl> getShortUrl(@Body JsonUrl requestBody);

    public static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://www.googleapis.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
}
