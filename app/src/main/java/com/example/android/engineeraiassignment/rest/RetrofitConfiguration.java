package com.example.android.engineeraiassignment.rest;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Hrishikesh Kadam on 19/11/2017
 */

public class RetrofitConfiguration {

    public static final String BASE_URL = "http://sd2-hiring.herokuapp.com/api/";

    private static Retrofit retrofit = null;

    public static Retrofit getRetrofit() {

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit;
    }
}
