package com.ce.game.myapplication.act;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.ce.game.myapplication.R;
import com.ce.game.myapplication.util.ThreadPoolU;
import com.ce.game.myapplication.view.CircleProcessSlaver;
import com.ce.game.myapplication.view.CircleProgressAutoScanView;

import java.util.concurrent.TimeUnit;

public class GuideAnimActivity extends Activity {

    protected CircleProgressAutoScanView mAutoScanView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide_anim);

        mAutoScanView = (CircleProgressAutoScanView) findViewById(R.id.auto_scan);

//        mAutoScanView.setMaxRepeatCount(12);
//        mAutoScanView.onSetStartEndProcess(6,30);
        mAutoScanView.setCurrentProgress(30);
        mAutoScanView.setTargetProgress(80);


        ThreadPoolU.schedule(1, 1, TimeUnit.SECONDS, new Runnable() {
            @Override
            public void run() {

                mHandler.sendEmptyMessage(0);

            }
        });

    }

    Handler mHandler = new Handler(Looper.myLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            mAutoScanView.onIncreaseProgress(10);

            int progressColor = new CircleProcessSlaver(getApplicationContext()).parseColorToDraw(mAutoScanView.getCurrentProgress() + 10)[1];
            mAutoScanView.setBackgroundColor(progressColor);

        }
    };
}
