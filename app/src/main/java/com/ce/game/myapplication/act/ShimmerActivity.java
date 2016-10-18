package com.ce.game.myapplication.act;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.ce.game.myapplication.R;
import com.ce.game.myapplication.view.ShimmerTextView;

public class ShimmerActivity extends AppCompatActivity {

    protected ShimmerTextView mShimmerTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shimmer);

        mShimmerTextView = (ShimmerTextView) findViewById(R.id.shimmer);
    }

    public void start(View v) {
        mShimmerTextView.startShimmer();
    }

    public void stop(View v) {
        mShimmerTextView.stopShimmer();
    }
}
