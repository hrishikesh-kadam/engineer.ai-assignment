package com.example.android.engineeraiassignment.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Data implements Parcelable {

    public final static Creator<Data> CREATOR = new Creator<Data>() {

        @SuppressWarnings({"unchecked"})
        public Data createFromParcel(Parcel in) {
            return new Data(in);
        }

        public Data[] newArray(int size) {
            return (new Data[size]);
        }

    };

    @SerializedName("users")
    @Expose
    private List<User> users = new ArrayList<User>();

    @SerializedName("has_more")
    @Expose
    private Boolean hasMore;

    protected Data(Parcel in) {
        in.readList(this.users, (com.example.android.engineeraiassignment.model.User.class.getClassLoader()));
        this.hasMore = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
    }

    public Data() {
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(users);
        dest.writeValue(hasMore);
    }

    public int describeContents() {
        return 0;
    }

    public List<User> getUsers() {
        return users;
    }

    public Boolean getHasMore() {
        return hasMore;
    }

    @Override
    public String toString() {
        return "Data{" +
                "users=" + users +
                ", hasMore=" + hasMore +
                '}';
    }
}
