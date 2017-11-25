package com.example.android.engineeraiassignment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.example.android.engineeraiassignment.model.User;
import com.example.android.engineeraiassignment.model.UserBody;
import com.example.android.engineeraiassignment.rest.MockService;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Hrishikesh Kadam on 20/11/2017
 */

public class UsersAsyncTaskLoader extends AsyncTaskLoader {

    private static final String LOG_TAG = UsersAsyncTaskLoader.class.getSimpleName();
    private MockService mockService;
    private int offset;
    private ArrayList<User> previousUserArrayList;
    private boolean isLoadDelivered;

    public UsersAsyncTaskLoader(Context context, MockService mockService, int offset, ArrayList<User> userArrayList) {
        super(context);

        this.mockService = mockService;
        this.offset = offset;

        if (userArrayList != null)
            previousUserArrayList = new ArrayList<>(userArrayList);
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        Log.v(LOG_TAG, "-> onStartLoading -> isLoadDelivered = " + isLoadDelivered);

        if (!isLoadDelivered)
            forceLoad();
    }

    @Override
    public Object loadInBackground() {
        Log.v(LOG_TAG, "-> loadInBackground");

        Call<UserBody> userBodyCall = mockService.getUsers(offset, UsersAdapter.LIMIT);
        Response<UserBody> userBodyResponse = null;

        try {
            Thread.sleep(8000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            userBodyResponse = userBodyCall.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Bundle bundle = new Bundle();

        if (userBodyResponse == null) {

            bundle.putBoolean("isSuccessful", false);
            bundle.putParcelableArrayList("currentUserArrayList", previousUserArrayList);
            bundle.putBoolean("hasMore", true);

        } else {

            bundle.putBoolean("isSuccessful", userBodyResponse.isSuccessful());

            if (previousUserArrayList == null || previousUserArrayList.size() == 0)
                bundle.putParcelableArrayList("currentUserArrayList", userBodyResponse.body().getData().getUsers());

            else {
                previousUserArrayList.addAll(userBodyResponse.body().getData().getUsers());
                bundle.putParcelableArrayList("currentUserArrayList", previousUserArrayList);
            }

            if (userBodyResponse.isSuccessful())
                bundle.putBoolean("hasMore", userBodyResponse.body().getData().getHasMore());
            else
                bundle.putBoolean("hasMore", true);
        }

        return bundle;
    }

    @Override
    public void deliverResult(@Nullable Object data) {
        Log.v(LOG_TAG, "-> deliverResult");

        isLoadDelivered = true;
        super.deliverResult(data);
    }
}
