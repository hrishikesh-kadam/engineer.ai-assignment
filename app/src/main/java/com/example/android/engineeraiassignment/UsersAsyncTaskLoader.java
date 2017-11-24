package com.example.android.engineeraiassignment;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.example.android.engineeraiassignment.model.UserBody;
import com.example.android.engineeraiassignment.rest.MockService;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Hrishikesh Kadam on 20/11/2017
 */

public class UsersAsyncTaskLoader extends AsyncTaskLoader {

    private static final String LOG_TAG = UsersAsyncTaskLoader.class.getSimpleName();
    private MockService mockService;

    public UsersAsyncTaskLoader(Context context, MockService mockService) {
        super(context);
        this.mockService = mockService;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        Log.d(LOG_TAG, "-> onStartLoading -> check if this is required");

        forceLoad();
    }

    @Override
    public Object loadInBackground() {
        Log.v(LOG_TAG, "-> loadInBackground");

        Call<UserBody> userBodyCall = mockService.getUsers(0, 10);
        Response<UserBody> userBodyResponse = null;

        try {
            userBodyResponse = userBodyCall.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return userBodyResponse;
    }
}
