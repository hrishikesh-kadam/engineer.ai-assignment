package com.example.android.engineeraiassignment;

import android.app.Application;
import android.util.DisplayMetrics;
import android.util.Log;

/**
 * Created by Hrishikesh Kadam on 19/11/2017
 */

public class MainApplication extends Application {

    public static final String LOG_TAG = MainApplication.class.getSimpleName();
    public static float density;
    public static int widthInPixels;
    public static int heightInPixels;
    public static float widthInDp;
    public static float heightInDp;

    public static int imageViewOddDimens;
    public static int imageViewEvenDimens;
    public static int imageViewMarginInPixel;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.v(LOG_TAG, "-> onCreate");

        initDisplayMetrics();
    }

    private void initDisplayMetrics() {
        Log.v(LOG_TAG, "-> initDisplayMetrics");

        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        density = displayMetrics.density;
        widthInPixels = displayMetrics.widthPixels;
        heightInPixels = displayMetrics.heightPixels;
        widthInDp = widthInPixels / density;
        heightInDp = heightInPixels / density;

        Log.i(LOG_TAG, "-> initDisplayMetrics -> desity = " + density);
        Log.i(LOG_TAG, "-> initDisplayMetrics -> widthInPixels x heightInPixels = " + widthInPixels + " x " + heightInPixels);
        Log.i(LOG_TAG, "-> initDisplayMetrics -> widthInDp x heightInDp = " + widthInDp + " x " + heightInDp);

        computeImageViewDimens(widthInPixels > heightInPixels ? heightInPixels : widthInPixels);
    }

    private void computeImageViewDimens(int smallestWidthInPixel) {
        Log.v(LOG_TAG, "-> computeImageViewDimens -> smallestWidthInPixel = " + smallestWidthInPixel);

        imageViewMarginInPixel = getResources().getDimensionPixelSize(R.dimen.imageViewMargin);

        imageViewOddDimens = smallestWidthInPixel - (2 * imageViewMarginInPixel);
        imageViewEvenDimens = (smallestWidthInPixel - (3 * imageViewMarginInPixel)) / 2;
    }
}