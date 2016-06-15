package com.ce.game.myapplication.scrollingblurtext;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.ListView;

import com.ce.game.myapplication.R;
import com.ce.game.myapplication.util.DU;
import com.ce.game.myapplication.util.ViewU;

import java.util.concurrent.CountDownLatch;

public class RollingAnimActivity extends AppCompatActivity {

    static ListView listView;
    final int SCROLLING_DURATION = 3000;
    private static final int HAND_REPEAT_TIMES = 3;

    // len = 10
    String[] appNames = {
            "Attention Fuel", "King Locker Ann", "Judging machine", "King of fighter", "Tencent wechat", "Sina Weibo", "AZ Recorder", "Smooth Shooter", "Smart Cleaner Pro", "Zombie park",
            "Attention Fuel", "King Locker Ann", "Judging machine", "King of fighter", "Tencent wechat", "Sina Weibo", "AZ Recorder", "Smooth Shooter", "Smart Cleaner Pro", "Zombie park",
            "Attention Fuel", "King Locker Ann", "Judging machine", "King of fighter", "Tencent wechat", "Sina Weibo", "AZ Recorder", "Smooth Shooter", "Smart Cleaner Pro", "Zombie park",
            "Attention Fuel", "King Locker Ann", "Judging machine", "King of fighter", "Tencent wechat", "Sina Weibo", "AZ Recorder", "Smooth Shooter", "Smart Cleaner Pro", "Zombie park",
            "Attention Fuel", "King Locker Ann", "Judging machine", "King of fighter", "Tencent wechat", "Sina Weibo", "AZ Recorder", "Smooth Shooter", "Smart Cleaner Pro", "Zombie park",
            "Attention Fuel", "King Locker Ann", "Judging machine", "King of fighter", "Tencent wechat", "Sina Weibo", "AZ Recorder", "Smooth Shooter", "Smart Cleaner Pro", "Zombie park",
            "Attention Fuel", "King Locker Ann", "Judging machine", "King of fighter", "Tencent wechat", "Sina Weibo", "AZ Recorder", "Smooth Shooter", "Smart Cleaner Pro", "Zombie park",
            "Attention Fuel", "King Locker Ann", "Judging machine", "King of fighter", "Tencent wechat", "Sina Weibo", "AZ Recorder", "Smooth Shooter", "Smart Cleaner Pro", "Zombie park",
            "Attention Fuel", "King Locker Ann", "Judging machine", "King of fighter", "Tencent wechat", "Sina Weibo", "AZ Recorder", "Smooth Shooter", "Smart Cleaner Pro", "Zombie park",
            "Attention Fuel", "King Locker Ann", "Judging machine", "King of fighter", "Tencent wechat", "Sina Weibo", "AZ Recorder", "Smooth Shooter", "Smart Cleaner Pro", "Zombie park",
            "Attention Fuel", "King Locker Ann", "Judging machine", "King of fighter", "Tencent wechat", "Sina Weibo", "AZ Recorder", "Smooth Shooter", "Smart Cleaner Pro", "Zombie park",
            "Attention Fuel", "King Locker Ann", "Judging machine", "King of fighter", "Tencent wechat", "Sina Weibo", "AZ Recorder", "Smooth Shooter", "Smart Cleaner Pro", "Zombie park",
            "Attention Fuel", "King Locker Ann", "Judging machine", "King of fighter", "Tencent wechat", "Sina Weibo", "AZ Recorder", "Smooth Shooter", "Smart Cleaner Pro", "Zombie park",
            "Attention Fuel", "King Locker Ann", "Judging machine", "King of fighter", "Tencent wechat", "Sina Weibo", "AZ Recorder", "Smooth Shooter", "Smart Cleaner Pro", "Zombie park",
    };

    ImageView mVerticalHand;
    ImageView mHorizontalHand;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rolling_anim);
        Context context = RollingAnimActivity.this;

        listView = (ListView) findViewById(R.id.list_view);
        mVerticalHand = (ImageView) findViewById(R.id.hand);
        mHorizontalHand = (ImageView) findViewById(R.id.horizontal_hand);

        MyCustomAdapter customAdapter = new MyCustomAdapter(context);
        customAdapter.addData(appNames);

        listView.setAdapter(customAdapter);

        listView.post(new Runnable() {
            @Override
            public void run() {
                if (mOnlySecondScene) {
                    horizontalHanAnim(mHorizontalHand);
                    ViewU.hide(mVerticalHand);
                    return;
                }

                scroll(SCROLLING_DURATION);
                verticalHandAnim(mVerticalHand);
                mFirstDoneLatch = new CountDownLatch(firstRepeatTimes);
                mSecondDoneLatch = new CountDownLatch(firstRepeatTimes);
                mFirstSceneTask = new FirstSceneTask();
                mSecondSceneTask = new SecondSceneTask();

                DU.execute(mFirstSceneTask);
                DU.execute(mSecondSceneTask);
            }
        });

    }

    FirstSceneTask mFirstSceneTask;
    SecondSceneTask mSecondSceneTask;
    private int firstRepeatTimes = 3;

    private static final int HIDE_FIRST_SCENE = 70;
    private static final int START_FIRST_SCENE = 231;
    private static final int START_SECOND_SCENE = 930;
    private static final int ONLY_SECOND_SCENE = 396;
    boolean mOnlySecondScene = true;

    Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case HIDE_FIRST_SCENE:
                    break;
                case START_FIRST_SCENE:
                    mSecondDoneLatch = new CountDownLatch(firstRepeatTimes);
                    mHorizontalHand.clearAnimation();
                    ViewU.invisible(mHorizontalHand);
                    verticalHandAnim(mVerticalHand);
                    DU.execute(mSecondSceneTask);
                    break;
                case START_SECOND_SCENE:
                    mFirstDoneLatch = new CountDownLatch(firstRepeatTimes);
                    mVerticalHand.clearAnimation();
                    ViewU.hide(mVerticalHand);
                    horizontalHanAnim(mHorizontalHand);
                    DU.execute(mFirstSceneTask);
                    break;
                case ONLY_SECOND_SCENE:

                    break;
                default:
                    break;
            }
        }
    };

    private class FirstSceneTask implements Runnable {

        @Override
        public void run() {
            DU.sd("second", "run" + DU.time());
            try {

                mFirstDoneLatch.await();

                mHandler.sendEmptyMessage(START_SECOND_SCENE);
                DU.sd("second", "reset");
            } catch (InterruptedException e) {
                DU.sd("second", "interrupted" + e + DU.time());
            }
        }
    }

    private class SecondSceneTask implements Runnable {

        @Override
        public void run() {
            DU.sd("second", "run" + DU.time());
            try {

                mSecondDoneLatch.await();

                mHandler.sendEmptyMessage(START_FIRST_SCENE);
                DU.sd("second", "reset");
            } catch (InterruptedException e) {
                DU.sd("second", "interrupted" + e + DU.time());
            }
        }
    }

    CountDownLatch mFirstDoneLatch;
    CountDownLatch mSecondDoneLatch;

    private void verticalHandAnim(View view) {
        TranslateAnimation mRollingAnim = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0,
                Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF,
                0, Animation.RELATIVE_TO_PARENT, 0.5f);

        mRollingAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                DU.sd("listener", "start");
//                if(mFirstDoneLatch.getCount() == firstRepeatTimes -1)
//                    ViewU.show(mVerticalHand);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                DU.sd("listener", "end");
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                DU.sd("listener", "repeat");
                if (mFirstDoneLatch != null) mFirstDoneLatch.countDown();
            }
        });
        mRollingAnim.setRepeatCount(Animation.INFINITE);
        mRollingAnim.setRepeatMode(Animation.RESTART);
        mRollingAnim.setInterpolator(new LinearInterpolator());
        mRollingAnim.setDuration(1000);
        view.startAnimation(mRollingAnim);
    }

    private void horizontalHanAnim(View v) {

        TranslateAnimation mRollingAnim = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0,
                Animation.RELATIVE_TO_PARENT, 0.5f, Animation.RELATIVE_TO_SELF,
                0, Animation.RELATIVE_TO_PARENT, 0);

        mRollingAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                DU.sd("h listener", "start");
//                if(mFirstDoneLatch.getCount() == firstRepeatTimes -1)
//                    ViewU.show(mVerticalHand);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                DU.sd("h listener", "end");
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                DU.sd("h listener", "repeat");
                if (mSecondDoneLatch != null) mSecondDoneLatch.countDown();
            }
        });
        mRollingAnim.setRepeatCount(Animation.INFINITE);
        mRollingAnim.setRepeatMode(Animation.RESTART);
        mRollingAnim.setInterpolator(new LinearInterpolator());
        mRollingAnim.setDuration(1000);
        v.startAnimation(mRollingAnim);

    }

    private void scroll(int DURATION) {
        listView.smoothScrollBy(0, 0); // Stops the listview from overshooting.
        listView.setSelection(0);

        listView.smoothScrollBy(listView.getChildAt(0).getHeight() * appNames.length,
                DURATION);
        listView.postDelayed(new Runnable() {
            public void run() {
                DU.t(RollingAnimActivity.this, "done");
//                mVerticalHand.clearAnimation();
            }
        }, DURATION);
    }
}
