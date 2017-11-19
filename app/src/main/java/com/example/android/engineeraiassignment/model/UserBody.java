package com.example.android.engineeraiassignment.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserBody implements Parcelable {

    public final static Creator<UserBody> CREATOR = new Creator<UserBody>() {

        @SuppressWarnings({"unchecked"})
        public UserBody createFromParcel(Parcel in) {
            return new UserBody(in);
        }

        public UserBody[] newArray(int size) {
            return (new UserBody[size]);
        }

    };

    @SerializedName("status")
    @Expose
    private Boolean status;

    @SerializedName("message")
    @Expose
    private Object message;

    @SerializedName("data")
    @Expose
    private Data data;

    protected UserBody(Parcel in) {
        this.status = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.message = ((Object) in.readValue((Object.class.getClassLoader())));
        this.data = ((Data) in.readValue((Data.class.getClassLoader())));
    }

    public UserBody() {
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(status);
        dest.writeValue(message);
        dest.writeValue(data);
    }

    public int describeContents() {
        return 0;
    }

    public Boolean getStatus() {
        return status;
    }

    public Object getMessage() {
        return message;
    }

    public Data getData() {
        return data;
    }

    @Override
    public String toString() {
        return "UserBody{" +
                "status=" + status +
                ", message=" + message +
                ", data=" + data +
                '}';
    }
}
