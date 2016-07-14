package com.ce.game.myapplication.scrollingblurtext.userguideanim;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.AttributeSet;
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
import com.ce.game.myapplication.util.ViewU;


public class PermissionSettingGuideView extends FrameLayout {
    private TextView tvGuideUser;

    private GuideCallback mCallback;

    private LinearLayout mSecondSceneParent;
    private ProgressBar mSlideProgress;

    private ImageView mVerticalHand;
    private ImageView mHorizontalHand;

    private boolean bSecondSceneOnly = false;

    private final int HORIZONTAL_HAND_ANIM_DURATION = 1000;
    private final int VERTICAL_HAND_ANIM_DURATION = 1200;

    private volatile boolean mProgressBarAnimStarted = false;

    public interface GuideCallback {
        void onButtonClick();
    }

    public PermissionSettingGuideView(final Context context) {
        this(context, null);
    }

    public PermissionSettingGuideView(final Context context, final AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PermissionSettingGuideView(final Context context, final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflate(context, R.layout.permission_guide_view, this);

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
                return false;
            }
        });
    }

    public void displaySecondSceneOnly() {
        bSecondSceneOnly = true;
    }

    public void startAnimation() {
        if (bSecondSceneOnly) {
            animHorizontalScene();
            ViewU.hide(mVerticalHand);
            return;
        }
        ViewU.hideAndShow(mSecondSceneParent, mVerticalHand);

        animVerticalHand(mVerticalHand);
    }

    private void animVerticalHand(View vertical) {
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
                ViewU.hideAndShow(mVerticalHand, mSecondSceneParent);

                animHorizontalScene();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        mRollingAnim.setInterpolator(new LinearInterpolator());
        mRollingAnim.setDuration(VERTICAL_HAND_ANIM_DURATION);

        ViewU.startWithNewAnim(mRollingAnim, vertical);
    }

    private void animHorizontalScene() {
        ViewU.startWithNewAnim(assembleHorizontalHandAnim(), mHorizontalHand);
    }

    @NonNull
    private TranslateAnimation assembleHorizontalHandAnim() {
        TranslateAnimation rollingAnim = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0,
                Animation.RELATIVE_TO_SELF, 0.8f, Animation.RELATIVE_TO_SELF,
                0, Animation.RELATIVE_TO_PARENT, 0);

        rollingAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                if (!mProgressBarAnimStarted) startProgressBarAnim();
            }

            private void startProgressBarAnim() {
                ViewU.startWithNewAnim(assembleProgressBarAnim(), mSlideProgress);
            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        rollingAnim.setRepeatCount(Animation.INFINITE);
        rollingAnim.setRepeatMode(Animation.RESTART);
        rollingAnim.setInterpolator(new LinearInterpolator());
        rollingAnim.setDuration(HORIZONTAL_HAND_ANIM_DURATION);
        return rollingAnim;
    }

    private ProgressBarAnimation assembleProgressBarAnim() {
        ProgressBarAnimation progressAnim = new ProgressBarAnimation(mSlideProgress, 0f, 100f);
        progressAnim.setDuration(HORIZONTAL_HAND_ANIM_DURATION);

        progressAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                mProgressBarAnimStarted = true;
            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        progressAnim.setRepeatCount(Animation.INFINITE);
        progressAnim.setRepeatMode(Animation.RESTART);

        return progressAnim;
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
}
