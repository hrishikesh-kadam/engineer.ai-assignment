package com.example.android.engineeraiassignment;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.android.engineeraiassignment.model.UserBody;
import com.example.android.engineeraiassignment.rest.MockService;
import com.example.android.engineeraiassignment.rest.RetrofitConfiguration;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity implements Callback<UserBody> {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkNetworkCall();
    }

    private void checkNetworkCall() {
        Log.v(LOG_TAG, "-> checkNetworkCall");

        Retrofit retrofit = RetrofitConfiguration.getRetrofit();
        MockService mockService = retrofit.create(MockService.class);
        Call<UserBody> userBodyCall = mockService.getUsers(0, 10);
        userBodyCall.enqueue(this);
    }

    @Override
    public void onResponse(Call<UserBody> call, Response<UserBody> response) {
        Log.v(LOG_TAG, "-> onResponse -> " + response.body());
    }

    @Override
    public void onFailure(Call<UserBody> call, Throwable t) {

    }
}
