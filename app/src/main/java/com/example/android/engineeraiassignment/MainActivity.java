package com.example.android.engineeraiassignment;

import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.android.engineeraiassignment.model.User;
import com.example.android.engineeraiassignment.model.UserBody;
import com.example.android.engineeraiassignment.rest.MockService;
import com.example.android.engineeraiassignment.rest.RetrofitConfiguration;
import com.google.gson.Gson;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    private static final int GET_USERS_CALL = 1;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private MockService mockService;
    private ArrayList<User> userArrayList;
    private boolean hasMore = true;
    private UsersAdapter usersAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v(LOG_TAG, "-> onCreate");

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        usersAdapter = new UsersAdapter(this, UsersAdapter.LOADING_VIEW);
        recyclerView.setAdapter(usersAdapter);

        Retrofit retrofit = RetrofitConfiguration.getRetrofit();
        mockService = retrofit.create(MockService.class);

        getSupportLoaderManager().initLoader(GET_USERS_CALL, null, this);
    }

    @Override
    public Loader onCreateLoader(int id, Bundle args) {

        switch (id) {

            case GET_USERS_CALL:
                Log.v(LOG_TAG, "-> onCreateLoader -> GET_USERS_CALL");
                return new UsersAsyncTaskLoader(this, mockService);

            default:
                throw new UnsupportedOperationException("Unknown id = " + id);
        }

    }

    @Override
    public void onLoadFinished(Loader loader, Object data) {

        switch (loader.getId()) {

            case GET_USERS_CALL:

                if (data == null || !(((Response<UserBody>) data).isSuccessful())) {
                    Log.e(LOG_TAG, "-> onLoadFinished -> GET_USERS_CALL -> is not successful");

                    usersAdapter.changeDataSet(null, UsersAdapter.FAILURE_VIEW);
                    usersAdapter.notifyDataSetChanged();

                } else {
                    Log.v(LOG_TAG, "-> onLoadFinished -> GET_USERS_CALL -> is successful");

                    @SuppressWarnings({"unchecked"})
                    Response<UserBody> userBodyResponse = (Response<UserBody>) data;
                    userArrayList = userBodyResponse.body().getData().getUsers();
                    Gson gson = new Gson();
                    Log.d(LOG_TAG, "-> onLoadFinished -> GET_USERS_CALL -> is successful -> " + gson.toJson(userArrayList));
                    hasMore = userBodyResponse.body().getData().getHasMore();

                    usersAdapter.changeDataSet(userArrayList, UsersAdapter.USER_VIEW);
                    usersAdapter.notifyDataSetChanged();
                }

                break;
        }
    }

    @Override
    public void onLoaderReset(Loader loader) {
        Log.v(LOG_TAG, "-> onLoaderReset");
    }
}
