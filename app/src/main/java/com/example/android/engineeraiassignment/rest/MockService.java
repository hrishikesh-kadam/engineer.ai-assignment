package com.example.android.engineeraiassignment.rest;

import com.example.android.engineeraiassignment.model.UserBody;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Hrishikesh Kadam on 19/11/2017
 */

public interface MockService {

    @GET("users")
    Call<UserBody> getUsers(@Query("offset") int offset, @Query("limit") int limit);
}
