package com.ce.game.myapplication.scrollingblurtext.userguideanim;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.Transformation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ce.game.myapplication.R;
import com.ce.game.myapplication.util.DU;
import com.ce.game.myapplication.util.ViewU;

import java.util.concurrent.CountDownLatch;

public class AssistGuideView extends FrameLayout {
    private TextView tvGuideUser;

    private GuideCallback mCallback;

    private LinearLayout mSecondSceneParent;
    private ProgressBar mSlideProgress;

    private ImageView mVerticalHand;
    private ImageView mHorizontalHand;
    private CountDownLatch mFirstDoneLatch;
    private CountDownLatch mSecondDoneLatch;

    private FirstSceneTask mFirstSceneTask;
    private SecondSceneTask mSecondSceneTask;
    private int mRepeatTimes = 2;

    private static final int START_FIRST_SCENE = 231;
    private static final int START_SECOND_SCENE = 930;
    private boolean bSecondSceneOnly = false;

    public interface GuideCallback {
        void onTouch();

        void onButtonClick();
    }

    public AssistGuideView(final Context context) {
        this(context, null);
    }

    public AssistGuideView(final Context context, final AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AssistGuideView(final Context context, final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflate(context, R.layout.assist_guide_view, this);

        init(context);
    }

    private void init(Context ctx) {
        tvGuideUser = (TextView) findViewById(R.id.tv_guide_user);

        mSecondSceneParent = (LinearLayout) findViewById(R.id.second_scene_parent);

        mVerticalHand = (ImageView) findViewById(R.id.vertical_hand);
        mHorizontalHand = (ImageView) findViewById(R.id.horizontal_hand);

        mSlideProgress = (ProgressBar) findViewById(R.id.open_process_indicator);

        findViewById(R.id.stop_btn).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallback.onButtonClick();
            }
        });
    }

    /**
     * update guide text
     *
     * @param guideText resource to set
     */
    public void updateGuideText(String guideText) {
        if (TextUtils.isEmpty(guideText)) return;

        if (tvGuideUser == null) return;

        tvGuideUser.setText(guideText);
    }

    /**
     * attach call back
     *
     * @param callback call back to attach
     * @throws NullPointerException
     */
    public void attachCallBack(final GuideCallback callback) {
        if (callback == null)
            throw new NullPointerException("call back cannot be null");

        mCallback = callback;

        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                callback.onTouch();
                return false;
            }
        });
    }

    public void displaySecondSceneOnly() {
        bSecondSceneOnly = true;
    }

    public void startAnimation() {
        if (bSecondSceneOnly) {
            horizontalHanAnim(mHorizontalHand);
            ViewU.hide(mVerticalHand);
            return;
        }
        ViewU.hideAndShow(mSecondSceneParent, mVerticalHand);

        verticalHandAnim(mVerticalHand);
        mFirstDoneLatch = new CountDownLatch(mRepeatTimes);
        mSecondDoneLatch = new CountDownLatch(mRepeatTimes);
        mFirstSceneTask = new FirstSceneTask();
        mSecondSceneTask = new SecondSceneTask();

        DU.execute(mFirstSceneTask);
        DU.execute(mSecondSceneTask);
    }

    Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case START_FIRST_SCENE:
                    mSecondDoneLatch = new CountDownLatch(mRepeatTimes);
                    mHorizontalHand.clearAnimation();
                    ViewU.hideAndShow(mSecondSceneParent, mVerticalHand);
                    verticalHandAnim(mVerticalHand);
                    DU.execute(mSecondSceneTask);
                    break;
                case START_SECOND_SCENE:
                    mFirstDoneLatch = new CountDownLatch(mRepeatTimes);
                    mVerticalHand.clearAnimation();
                    ViewU.hideAndShow(mVerticalHand, mSecondSceneParent);
                    horizontalHanAnim(mHorizontalHand);
                    DU.execute(mFirstSceneTask);
                    break;
                default:
                    break;
            }
        }
    };

    private class FirstSceneTask implements Runnable {
        @Override
        public void run() {
            try {
                mFirstDoneLatch.await();
                mHandler.sendEmptyMessage(START_SECOND_SCENE);
            } catch (InterruptedException e) {
            }
        }
    }

    private class SecondSceneTask implements Runnable {
        @Override
        public void run() {
            try {
                mSecondDoneLatch.await();
                mHandler.sendEmptyMessage(START_FIRST_SCENE);
            } catch (InterruptedException e) {
                DU.sd("second", "interrupted" + e + DU.time());
            }
        }
    }

    private void verticalHandAnim(View view) {
        TranslateAnimation mRollingAnim = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0,
                Animation.RELATIVE_TO_SELF, 0,
                Animation.RELATIVE_TO_PARENT, 0.2f,
                Animation.RELATIVE_TO_SELF, 0);

        mRollingAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                if (mFirstDoneLatch != null) mFirstDoneLatch.countDown();
            }
        });
        mRollingAnim.setRepeatCount(Animation.INFINITE);
        mRollingAnim.setRepeatMode(Animation.RESTART);
        mRollingAnim.setInterpolator(new LinearInterpolator());
        mRollingAnim.setDuration(1500);
        view.startAnimation(mRollingAnim);
    }

    private void horizontalHanAnim(View v) {

        TranslateAnimation mRollingAnim = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0,
                Animation.RELATIVE_TO_SELF, 0.8f, Animation.RELATIVE_TO_SELF,
                0, Animation.RELATIVE_TO_PARENT, 0);

        mRollingAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                if (mSecondDoneLatch != null) mSecondDoneLatch.countDown();
            }
        });
        mRollingAnim.setRepeatCount(Animation.INFINITE);
        mRollingAnim.setRepeatMode(Animation.RESTART);
        mRollingAnim.setInterpolator(new LinearInterpolator());
        mRollingAnim.setDuration(1200);
        v.startAnimation(mRollingAnim);

        ProgressBarAnimation anim = new ProgressBarAnimation(mSlideProgress, 0f, 100f);
        anim.setDuration(1200);
        anim.setRepeatCount(Animation.INFINITE);
        anim.setRepeatMode(Animation.RESTART);
        mSlideProgress.startAnimation(anim);
    }

    final private class ProgressBarAnimation extends Animation {
        private ProgressBar progressBar;
        private float from;
        private float to;

        public ProgressBarAnimation(ProgressBar progressBar, float from, float to) {
            super();
            this.progressBar = progressBar;
            this.from = from;
            this.to = to;
        }

        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            super.applyTransformation(interpolatedTime, t);
            float value = from + (to - from) * interpolatedTime;
            progressBar.setProgress((int) value);
        }

    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {

        return super.dispatchKeyEvent(event);
    }
}
