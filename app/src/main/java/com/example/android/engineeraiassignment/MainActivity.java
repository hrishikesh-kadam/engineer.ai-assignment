package com.example.android.engineeraiassignment;

import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.android.engineeraiassignment.model.User;
import com.example.android.engineeraiassignment.rest.MockService;
import com.example.android.engineeraiassignment.rest.RetrofitConfiguration;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks,
        UsersAdapter.OnLoadMoreListener {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    private static final int GET_USERS_CALL = 1;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private MockService mockService;
    private ArrayList<User> userArrayList;
    private boolean hasMore;
    private UsersAdapter usersAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v(LOG_TAG, "-> onCreate");

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initRecyclerView();

        Retrofit retrofit = RetrofitConfiguration.getRetrofit();
        mockService = retrofit.create(MockService.class);

        getSupportLoaderManager().initLoader(GET_USERS_CALL, null, this);
    }

    private void initRecyclerView() {
        Log.v(LOG_TAG, "-> initRecyclerView");

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        usersAdapter = new UsersAdapter(this, UsersAdapter.LOADING_VIEW);
        usersAdapter.setOnLoadMoreListener(this);
        recyclerView.setAdapter(usersAdapter);
    }

    @Override
    public Loader onCreateLoader(int id, Bundle args) {

        switch (id) {

            case GET_USERS_CALL:
                Log.v(LOG_TAG, "-> onCreateLoader -> GET_USERS_CALL");
                return new UsersAsyncTaskLoader(this, mockService, usersAdapter.getOffset(), userArrayList);

            default:
                throw new UnsupportedOperationException("Unknown id = " + id);
        }
    }

    @Override
    public void onLoadFinished(Loader loader, Object data) {

        switch (loader.getId()) {

            case GET_USERS_CALL:

                Bundle bundle = (Bundle) data;
                boolean isSuccessful = bundle.getBoolean("isSuccessful");
                userArrayList = bundle.getParcelableArrayList("currentUserArrayList");
                hasMore = bundle.getBoolean("hasMore");

                if (!isSuccessful) {
                    Log.e(LOG_TAG, "-> onLoadFinished -> GET_USERS_CALL -> is not successful");

                    usersAdapter.changeDataSet(UsersAdapter.FAILURE_VIEW, userArrayList, hasMore);
                    usersAdapter.notifyDataSetChanged();

                } else {
                    Log.d(LOG_TAG, "-> onLoadFinished -> GET_USERS_CALL -> is successful -> " + userArrayList.size());

                    usersAdapter.changeDataSet(UsersAdapter.USER_VIEW, userArrayList, hasMore);
                    usersAdapter.notifyDataSetChanged();
                }

                break;
        }
    }

    @Override
    public void onLoaderReset(Loader loader) {
        Log.v(LOG_TAG, "-> onLoaderReset");
    }

    @Override
    public void onLoadMore() {
        Log.v(LOG_TAG, "-> onLoadMore");

        getSupportLoaderManager().restartLoader(GET_USERS_CALL, null, this);
    }
}
