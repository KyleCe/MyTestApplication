package com.ce.game.myapplication.act;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.TranslateAnimation;

import com.ce.game.myapplication.MainActivity;
import com.ce.game.myapplication.R;
import com.ce.game.myapplication.util.DU;
import com.ce.game.myapplication.util.ViewU;
import com.ce.game.myapplication.view.CircleProgressScanView;
import com.ce.game.myapplication.view.CircleProgressView;
import com.ce.game.myapplication.view.CompleteCallback;

public class PrivacyDetectionPreGuideActivity extends Activity implements CompleteCallback {

    protected CircleProgressScanView mScanView;
    protected final float SCALE_ICON_FRAME_RATIO = .8f;
    protected final float SCALE_ICON_COMPLETE_RATIO = .8f;
    protected float mScaleRatioToFitInRect = 0;
    protected CircleProgressView mCircleProgressView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_privacy_detection_pre_guide);

        initView();
        initData();
    }

    private void initView() {
        mScanView = (CircleProgressScanView) findViewById(R.id.circle_progress_scan_view);
        mCircleProgressView = (CircleProgressView) findViewById(R.id.circle_progress);

        mCircleProgressView.onSetStartEndProcess(10,100);
    }

    private void initData() {
        mScanView.setMaxRepeatCount(2);
        mScanView.setTargetProgress(35);
        mScanView.setScanCompleteCallback(this);
    }

    String TAG = PrivacyDetectionPreGuideActivity.class.getSimpleName();

    @Override
    protected void onResume() {
        super.onResume();
        DU.pwa(TAG,"resume");
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        DU.pwa(TAG,"focus");
        super.onWindowFocusChanged(hasFocus);
        final Rect mBoundsRect = new Rect(8, 628, 184, 849);

        if(hasFocus)
            mCircleProgressView.setProgressWithAnimation(100);

        if (hasFocus && mBoundsRect != null) {
            mScaleRatioToFitInRect = SCALE_ICON_FRAME_RATIO
                    * Math.min(mBoundsRect.right - mBoundsRect.left, mBoundsRect.bottom - mBoundsRect.top)
                    / mScanView.getWidth();
            mScanView.animate().scaleX(mScaleRatioToFitInRect).scaleY(mScaleRatioToFitInRect)
                    .withEndAction(new Runnable() {
                        @Override
                        public void run() {
                            mScanView.animate()
                                    .translationX(mBoundsRect.centerX() - (mScanView.getWidth() >> 1))
                                    .translationY(mBoundsRect.centerY() - ((mBoundsRect.bottom - mBoundsRect.top) >> 1))
                                    .setDuration(0).start();
                        }
                    })
                    .setDuration(0).start();

//            ObjectAnimator tranX = ObjectAnimator.ofFloat(mScanView, View.TRANSLATION_X,mBoundsRect.left).setDuration(0);
//            ObjectAnimator tranY = ObjectAnimator.ofFloat(mScanView, View.TRANSLATION_Y,mBoundsRect.top).setDuration(0);
//            new AnimatorSet().play(tranX).with(tranY);

//            animateShortCutWithEndAction(null, mScanView, null);

            animTest(mScanView,mBoundsRect);
        }
    }


    public void animTest(final View v,final Rect mBoundsRect) {
        final int startBottom = v.getBottom();
        ObjectAnimator tran = ObjectAnimator.ofFloat(v, View.TRANSLATION_Y, v.getBottom(), v.getBottom()
                        + (-1) * v.getHeight()
        );
        tran.setRepeatCount(3);
        tran.setRepeatMode(ValueAnimator.REVERSE);
        tran.setInterpolator(new DecelerateInterpolator());
        tran.setDuration(500);
        tran.start();
        tran.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                ViewU.invisible(mScanView);

                mScaleRatioToFitInRect = SCALE_ICON_FRAME_RATIO
                        * Math.min(mBoundsRect.right - mBoundsRect.left, mBoundsRect.bottom - mBoundsRect.top)
                        / mScanView.getWidth();
                mScanView.animate().scaleX(mScaleRatioToFitInRect).scaleY(mScaleRatioToFitInRect)
                        .withEndAction(new Runnable() {
                            @Override
                            public void run() {
                                mScanView.animate()
                                        .translationX(mBoundsRect.centerX() - (mScanView.getWidth() >> 1))
                                        .translationY(mBoundsRect.centerY() - ((mBoundsRect.bottom - mBoundsRect.top) >> 1))
                                        .setDuration(0).start();
                            }
                        })
                        .setDuration(0).start();

                mScanView.setScanCompleteCallback(new CompleteCallback() {
                    @Override
                    public void onComplete() {
                        ViewU.hideAndShow(mScanView, v);
//                        endAction.run();
                    }
                });
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                v.animate().translationY(startBottom).setDuration(0).start();

                mScanView.startScanAnimation();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    @Override
    public void onComplete() {
        mScanView.animate().scaleX(SCALE_ICON_COMPLETE_RATIO).scaleY(SCALE_ICON_COMPLETE_RATIO)
                .withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);

                        intent.getComponent();

//                        startActivity(intent);
                        finish();
                    }
                })
                .setDuration(200).start();
    }


    public static void animateShortCutWithEndAction(Intent intent, View v, final Runnable endAction) {
        TranslateAnimation tranY = new TranslateAnimation(0, 0, 0, 0, Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, -1f);
        tranY.setDuration(500);
        tranY.setRepeatCount(3);
        tranY.setRepeatMode(Animation.REVERSE);
        tranY.setInterpolator(new DecelerateInterpolator());
        tranY.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (endAction != null) endAction.run();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        v.setAnimation(tranY);
        tranY.start();
    }

    @Override
    public void onBackPressed() {
    }
}
