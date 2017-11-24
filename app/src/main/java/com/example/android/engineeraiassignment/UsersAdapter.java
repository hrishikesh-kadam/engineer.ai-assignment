package com.example.android.engineeraiassignment;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.engineeraiassignment.model.User;
import com.example.android.engineeraiassignment.utils.CircleTransform;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Hrishikesh Kadam on 24/11/2017
 */

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.ViewHolder> {

    private static final int USER_VIEW = 0;
    private static final int EMPTY_VIEW = 1;
    private static final int LOADING_VIEW = 2;
    private Context context;
    private ArrayList<User> userArrayList;

    public UsersAdapter(Context context, ArrayList<User> userArrayList) {

        this.context = context;
        this.userArrayList = userArrayList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        switch (viewType) {

            case EMPTY_VIEW:
                return null;

            case USER_VIEW:
                View itemView = LayoutInflater.from(parent.getContext()).
                        inflate(R.layout.user_item, parent, false);
                return new UserViewHolder(itemView);

            case LOADING_VIEW:
                return null;

            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        switch (getItemViewType(position)) {

            case EMPTY_VIEW:
                break;

            case USER_VIEW:
                UserViewHolder userViewHolder = (UserViewHolder) holder;
                User user = userArrayList.get(position);

                String imageUrl = user.getImage().replace("http:", "https:");
                Picasso.with(context).load(imageUrl).transform(new CircleTransform())
                        .into(userViewHolder.imageView);

                userViewHolder.textView.setText(user.getName());
                break;

            case LOADING_VIEW:
                break;

            default:
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {

        if (userArrayList == null || userArrayList.size() == 0)
            return EMPTY_VIEW;
        else if (position < userArrayList.size())
            return USER_VIEW;
        else if (position == userArrayList.size())
            return LOADING_VIEW;
        else throw new UnsupportedOperationException("Unknown position = " + position);
    }

    @Override
    public int getItemCount() {

        if (userArrayList == null || userArrayList.size() == 0)
            return 1;
        else
            return userArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }

    public class UserViewHolder extends ViewHolder {

        @BindView(R.id.imageViewUser)
        ImageView imageView;
        @BindView(R.id.textViewUserName)
        TextView textView;

        public UserViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
