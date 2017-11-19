package com.example.android.engineeraiassignment;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;

import com.google.android.flexbox.AlignItems;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FlexboxLayoutPOC extends AppCompatActivity {

    private static final String LOG_TAG = FlexboxLayoutPOC.class.getSimpleName();
    private static final int EVEN = 0;
    private static final int ODD = 1;

    @BindView(R.id.scrollView)
    ScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flexbox_layout_poc);
        ButterKnife.bind(this);

        scrollView.removeAllViews();
        scrollView.addView(buildImageViewFlexboxLayout(this, 5));
    }

    public FlexboxLayout buildImageViewFlexboxLayout(Context context, int noOfItems) {

        FlexboxLayout flexboxLayout = new FlexboxLayout(context);

        flexboxLayout.setLayoutParams(new FlexboxLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        flexboxLayout.setPadding(
                0, 0, MainApplication.imageViewMarginInPixel, MainApplication.imageViewMarginInPixel);
        flexboxLayout.setPaddingRelative(
                0, 0, MainApplication.imageViewMarginInPixel, MainApplication.imageViewMarginInPixel);

        flexboxLayout.setAlignItems(AlignItems.FLEX_START);
        flexboxLayout.setFlexWrap(FlexWrap.WRAP);

        boolean isOdd = noOfItems % 2 == 1;

        if (isOdd)
            flexboxLayout.addView(getImageViewItem(context, ODD));

        for (int i = isOdd ? 2 : 1; i <= noOfItems; i++)
            flexboxLayout.addView(getImageViewItem(context, EVEN));

        return flexboxLayout;
    }

    public ImageView getImageViewItem(Context context, int type) {

        ImageView imageView = new ImageView(context);

        FlexboxLayout.LayoutParams layoutParams = null;

        if (type == ODD) {

            layoutParams = new FlexboxLayout.LayoutParams(new ViewGroup.LayoutParams(
                    MainApplication.imageViewOddDimens, MainApplication.imageViewOddDimens));

        } else if (type == EVEN) {

            layoutParams = new FlexboxLayout.LayoutParams(new ViewGroup.LayoutParams(
                    MainApplication.imageViewEvenDimens, MainApplication.imageViewEvenDimens));
        }

        layoutParams.setMargins(
                MainApplication.imageViewMarginInPixel, MainApplication.imageViewMarginInPixel, 0, 0);
        layoutParams.setMarginStart(MainApplication.imageViewMarginInPixel);
        imageView.setLayoutParams(layoutParams);

        if (Build.VERSION.SDK_INT < 16)
            imageView.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.border_black));
        else
            imageView.setBackground(ContextCompat.getDrawable(context, R.drawable.border_black));

        return imageView;
    }
}
