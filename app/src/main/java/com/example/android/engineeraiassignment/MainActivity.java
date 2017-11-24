package com.example.android.engineeraiassignment;

import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.android.engineeraiassignment.model.User;
import com.example.android.engineeraiassignment.model.UserBody;
import com.example.android.engineeraiassignment.rest.MockService;
import com.example.android.engineeraiassignment.rest.RetrofitConfiguration;

import java.util.ArrayList;

import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    private static final int GET_USERS_CALL = 1;
    private MockService mockService;
    private ArrayList<User> userArrayList;
    private boolean hasMore = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Retrofit retrofit = RetrofitConfiguration.getRetrofit();
        mockService = retrofit.create(MockService.class);

        getSupportLoaderManager().initLoader(GET_USERS_CALL, null, this);
    }

    @Override
    public Loader onCreateLoader(int id, Bundle args) {

        switch(id) {

            case GET_USERS_CALL:
                Log.v(LOG_TAG, "-> onCreateLoader -> GET_USERS_CALL");
                return new UsersAsyncTaskLoader(this, mockService);

            default:
                throw new UnsupportedOperationException("Unknown id = " + id);
        }

    }

    @Override
    public void onLoadFinished(Loader loader, Object data) {

        switch(loader.getId()) {

            case GET_USERS_CALL:

                if (data == null || !(((Response<UserBody>) data).isSuccessful()) ) {
                    Log.e(LOG_TAG, "-> onLoadFinished -> GET_USERS_CALL -> is not successful");


                } else {
                    Log.v(LOG_TAG, "-> onLoadFinished -> GET_USERS_CALL -> is successful");

                    @SuppressWarnings({"unchecked"})
                    Response<UserBody> userBodyResponse = (Response<UserBody>) data;
                    userArrayList = userBodyResponse.body().getData().getUsers();
                    hasMore = userBodyResponse.body().getData().getHasMore();
                }

                break;
        }
    }

    @Override
    public void onLoaderReset(Loader loader) {
        Log.v(LOG_TAG, "-> onLoaderReset");
    }
}
