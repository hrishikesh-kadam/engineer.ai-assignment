package com.example.android.engineeraiassignment;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.engineeraiassignment.model.User;
import com.example.android.engineeraiassignment.utils.CircleTransform;
import com.google.android.flexbox.FlexboxLayout;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Hrishikesh Kadam on 24/11/2017
 */

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.ViewHolder> {

    public static final int USER_VIEW = 0;
    public static final int EMPTY_VIEW = 1;
    public static final int LOADING_VIEW = 2;
    public static final int FAILURE_VIEW = 3;
    private static final String LOG_TAG = UsersAdapter.class.getSimpleName();
    private static final int FLEXBOX_LAYOUT_ID = 100;
    private int CURRENT_VIEW;
    private Context context;
    private ArrayList<User> userArrayList;

    public UsersAdapter(Context context, int CURRENT_VIEW) {

        this.context = context;
        this.CURRENT_VIEW = CURRENT_VIEW;
    }

    public UsersAdapter(Context context, ArrayList<User> userArrayList) {

        this.context = context;
        this.userArrayList = userArrayList;
    }

    public void changeDataSet(ArrayList<User> userArrayList, int CURRENT_VIEW) {

        this.userArrayList = userArrayList;
        this.CURRENT_VIEW = CURRENT_VIEW;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView;

        switch (viewType) {

            case EMPTY_VIEW:
            case FAILURE_VIEW:
                itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.empty_view_layout, parent, false);
                return new EmptyViewHolder(itemView);

            case USER_VIEW:
                itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.user_item, parent, false);
                return new UserViewHolder(itemView);

            case LOADING_VIEW:
                itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.loading_layout, parent, false);
                return new ViewHolder(itemView);

            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        switch (getItemViewType(position)) {

            case USER_VIEW:
                UserViewHolder userViewHolder = (UserViewHolder) holder;
                User user = userArrayList.get(position);

                Log.d(LOG_TAG, "-> onBindViewHolder -> position = " + position + ", user name: " + user.getName() + ", no of items = " + user.getItems().size());

                String imageUrl = user.getImage().replace("http:", "https:");
                Picasso.with(context)
                        .load(imageUrl)
                        .placeholder(R.drawable.imageview_user_loading_placeholder)
                        .error(R.drawable.imageview_user_error_placeholder)
                        .transform(new CircleTransform())
                        .into(userViewHolder.imageView);

                userViewHolder.textView.setText(user.getName());

                FlexboxLayout flexboxLayout = FlexboxLayoutPOC.buildImageViewFlexboxLayout(context,
                        user.getItems());
                flexboxLayout.setId(FLEXBOX_LAYOUT_ID);

                View view = userViewHolder.rootUserItem.findViewById(FLEXBOX_LAYOUT_ID);
                if (view != null)
                    userViewHolder.rootUserItem.removeViewAt(2);
                userViewHolder.rootUserItem.addView(flexboxLayout);

                ConstraintSet constraintSet = new ConstraintSet();
                constraintSet.clone(userViewHolder.rootUserItem);

                constraintSet.connect(
                        flexboxLayout.getId(),
                        ConstraintSet.TOP,
                        userViewHolder.imageView.getId(),
                        ConstraintSet.BOTTOM);

                constraintSet.connect(
                        flexboxLayout.getId(),
                        ConstraintSet.LEFT,
                        userViewHolder.imageView.getId(),
                        ConstraintSet.LEFT
                );

                constraintSet.connect(
                        flexboxLayout.getId(),
                        ConstraintSet.START,
                        userViewHolder.imageView.getId(),
                        ConstraintSet.START
                );

                constraintSet.connect(
                        flexboxLayout.getId(),
                        ConstraintSet.RIGHT,
                        userViewHolder.imageView.getId(),
                        ConstraintSet.RIGHT
                );

                constraintSet.connect(
                        flexboxLayout.getId(),
                        ConstraintSet.END,
                        userViewHolder.imageView.getId(),
                        ConstraintSet.END
                );

                constraintSet.applyTo(userViewHolder.rootUserItem);

                break;

            case LOADING_VIEW:
                break;

            case EMPTY_VIEW:
                EmptyViewHolder emptyViewHolder = (EmptyViewHolder) holder;
                emptyViewHolder.textViewMessage.setText(R.string.empty_message);
                break;

            case FAILURE_VIEW:
                emptyViewHolder = (EmptyViewHolder) holder;
                emptyViewHolder.textViewMessage.setText(R.string.failure_message);
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {

        if (CURRENT_VIEW == LOADING_VIEW || CURRENT_VIEW == FAILURE_VIEW)
            return CURRENT_VIEW;

        else if (userArrayList == null || userArrayList.size() == 0)
            return EMPTY_VIEW;

        else if (position < userArrayList.size())
            return USER_VIEW;

        else if (position == userArrayList.size())
            return LOADING_VIEW;

        else throw new UnsupportedOperationException("Unknown position = " + position);

//        if (userArrayList == null || userArrayList.size() == 0)
//            return EMPTY_VIEW;
//        else if (position < userArrayList.size())
//            return USER_VIEW;
//        else if (position == userArrayList.size())
//            return LOADING_VIEW;
//        else throw new UnsupportedOperationException("Unknown position = " + position);
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

        @BindView(R.id.rootUserItem)
        ConstraintLayout rootUserItem;
        @BindView(R.id.imageViewUser)
        ImageView imageView;
        @BindView(R.id.textViewUserName)
        TextView textView;

        public UserViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public class EmptyViewHolder extends ViewHolder {

        @BindView(R.id.textViewMessage)
        TextView textViewMessage;

        public EmptyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
