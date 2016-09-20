package com.ce.game.myapplication.act;

import android.app.Activity;
import android.os.Bundle;

import com.ce.game.myapplication.R;
import com.ce.game.myapplication.view.CircleProgressView;
import com.ce.game.myapplication.view.CircularProgressBar;

public class CircleProgressActivity extends Activity {
        CircleProgressView mCircleProgressView;
        CircularProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circle_progress);

        mCircleProgressView = (CircleProgressView) findViewById(R.id.circle_progress);
        mProgressBar = (CircularProgressBar) findViewById(R.id.circularProgressbar);

        mCircleProgressView.postDelayed(new Runnable() {
            @Override
            public void run() {
//                mCircleProgressView.setProgressWithAnimation(20);
//                mProgressBar.setProgressWithAnimation(20);
                mCircleProgressView.setStartProgress(20);
            }
        },1000);
        mCircleProgressView.postDelayed(new Runnable() {
            @Override
            public void run() {
                mCircleProgressView.setProgressWithAnimation(100);
                mProgressBar.setProgressWithAnimation(40);

            }
        },2000);
//        mCircleProgressView.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                mCircleProgressView.setProgressWithAnimation(100);
//                mProgressBar.setProgressWithAnimation(60);
//            }
//        },3000);
//        mCircleProgressView.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                mCircleProgressView.setProgressWithAnimation(80);
//                mProgressBar.setProgressWithAnimation(80);
//            }
//        },4000);
//        mCircleProgressView.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                mCircleProgressView.setProgressWithAnimation(99);
//                mProgressBar.setProgressWithAnimation(99);
//            }
//        },5000);
//        mCircleProgressView.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                mCircleProgressView.setProgressWithAnimation(100);
//                mProgressBar.setProgressWithAnimation(99);
//            }
//        },6000);
    }
}
