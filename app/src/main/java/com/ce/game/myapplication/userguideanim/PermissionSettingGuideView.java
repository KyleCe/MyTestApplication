package com.ce.game.myapplication.userguideanim;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.Transformation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.ce.game.myapplication.R;
import com.ce.game.myapplication.util.ViewU;


public class PermissionSettingGuideView extends FrameLayout
        implements GuideViewInterface.StartSeriesAnimation
        , GuideViewInterface.MinimumToRight {
    private TextView tvGuideUser;

    private GuideViewInterface.GuideCallback mCallback;

    private GuideViewInterface.KeyEventCallback mKeyEventCallback;

    private LinearLayout mSecondSceneParent;
    private ProgressBar mSlideProgress;

    private ImageView mVerticalHand;
    private ImageView mHorizontalHand;

    @Deprecated
    private boolean bSecondSceneOnly = false;

    private final int HORIZONTAL_HAND_ANIM_DURATION = 1000;
    private final int DEFAULT_FIRST_SCENE_DURATION = 1000;
    private int mFirstSceneDuration = DEFAULT_FIRST_SCENE_DURATION;

    private ImageView mListToScroll;
    private FrameLayout mFirstSceneParent;

    private View mStop;

    private View mIndicatorContainer;

    private GuideViewInterface.MinimumToRightEndCallback mToRightEndCallback;

    private ScrollView mScrollView;
    private static final float VERTICAL_HAND_DURATION_FACTOR = 0.67f;

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

        mStop = findViewById(R.id.stop_btn);

        mStop.setOnClickListener(generateStopClickListener());

        mListToScroll = (ImageView) findViewById(R.id.app_list_to_roll);
        mFirstSceneParent = (FrameLayout) findViewById(R.id.first_scene_parent);

        mIndicatorContainer = findViewById(R.id.indicate_container);

        mScrollView = ((ScrollView) findViewById(R.id.scrollview));
    }

    private static final int MINIMUM_ANIMATION_DURATION = 300;

    @NonNull
    private OnClickListener generateStopClickListener() {
        return new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCallback != null) mCallback.onButtonClick();

                onMinimumToRight();
            }
        };
    }

    @NonNull
    private Runnable scaleAfterRotateTask() {
        return new Runnable() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void run() {
                mIndicatorContainer.setPivotX(getRight());

                mIndicatorContainer.animate()
                        .scaleX(0)
                        .scaleY(0)
                        .setInterpolator(new AccelerateInterpolator())
                        .setDuration(MINIMUM_ANIMATION_DURATION)
                        .withEndAction(new Runnable() {
                            @Override
                            public void run() {
                                bringAnimSceneBackToNormal();

                                if (mToRightEndCallback != null)
                                    mToRightEndCallback.onAnimationEnd();
                            }
                        })
                        .start();
            }
        };
    }

    private void bringAnimSceneBackToNormal() {
        ViewU.invisible(mIndicatorContainer, mStop);

        mIndicatorContainer.setPivotX(getWidth());
        mIndicatorContainer.setPivotX(getHeight());

        mIndicatorContainer.animate()
                .rotation(0)
                .scaleX(1)
                .scaleY(1)
                .setDuration(0)
                .start();

        mStop.animate().alpha(1)
                .setDuration(0)
                .start();
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onMinimumToRight() {
        mIndicatorContainer.animate().rotation(27)
                .withStartAction(scaleAfterRotateTask())
                .setInterpolator(new AccelerateInterpolator())
                .setDuration(MINIMUM_ANIMATION_DURATION)
                .start();

        mStop.animate().alpha(0)
                .setInterpolator(new AccelerateInterpolator())
                .setDuration(MINIMUM_ANIMATION_DURATION)
                .start();
    }

    /**
     * update guide text
     *
     * @param guideText resource to set
     */
    @Deprecated
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
    @Deprecated
    public void attachCallBack(final GuideViewInterface.GuideCallback callback) {
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

    public void attachKeyEventCallback(final GuideViewInterface.KeyEventCallback keyEventCallback) {
        mKeyEventCallback = keyEventCallback;
    }

    public void attachMinimumEndAction(GuideViewInterface.MinimumToRightEndCallback callback) {
        mToRightEndCallback = callback;
    }

    @Deprecated
    public void displaySecondSceneOnly() {
        bSecondSceneOnly = true;
    }

    @Override
    public void onStartSeries() {
        startAnimation(false);
    }

    @Override
    public void onStartOnlySecond() {

        startAnimation(true);
    }

    public void startAnimation(boolean secondOnly) {
        ViewU.show(mVerticalHand, mStop, mIndicatorContainer);

        if (secondOnly) {
            mFirstSceneDuration = 0;
            ViewU.hide(mVerticalHand, mListToScroll);
        } else {
            mFirstSceneDuration = DEFAULT_FIRST_SCENE_DURATION;
        }

        ViewU.hide(mSecondSceneParent);

        ViewU.clearAnimation(mListToScroll, mVerticalHand, mHorizontalHand, mSlideProgress, mStop);

        animFirstScene();
    }

    @Deprecated
    public void startAnimation() {
        ViewU.show(mVerticalHand, mStop, mIndicatorContainer);

        if (bSecondSceneOnly) {
            ViewU.hide(mVerticalHand);
            return;
        }
        ViewU.hide(mSecondSceneParent);

        animFirstScene();
    }

    private void animFirstScene() {
        if (mFirstSceneDuration == 0) {
            transfer2SecondScene();
            return;
        }

        ViewU.show(mFirstSceneParent, mVerticalHand, mListToScroll);

        TranslateAnimation mRollingAnim = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0,
                Animation.RELATIVE_TO_SELF, 0,
                Animation.RELATIVE_TO_PARENT, 0.2f,
                Animation.RELATIVE_TO_SELF, 0);

        mRollingAnim.setInterpolator(new LinearInterpolator());
        mRollingAnim.setDuration((int) (mFirstSceneDuration * VERTICAL_HAND_DURATION_FACTOR));

        ViewU.startWithNewAnim(mRollingAnim, mVerticalHand);

        new CountDownTimer(mFirstSceneDuration, 20) {
            @Override
            public void onTick(long millisUntilFinished) {

                mScrollView.scrollTo(0, (int) (mFirstSceneDuration - millisUntilFinished));
            }

            @Override
            public void onFinish() {
                transfer2SecondScene();
            }
        }.start();
    }

    private void transfer2SecondScene() {
        mSlideProgress.setProgress(0);

        ViewU.hideAndShow(mFirstSceneParent, mSecondSceneParent);

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
                ViewU.startWithNewAnim(assembleProgressBarAnim(), mSlideProgress);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                ViewU.showAndHide(mFirstSceneParent, mSecondSceneParent);

                animFirstScene();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        rollingAnim.setInterpolator(new LinearInterpolator());
        rollingAnim.setDuration(HORIZONTAL_HAND_ANIM_DURATION);
        return rollingAnim;
    }

    @NonNull
    private ProgressBarAnimation assembleProgressBarAnim() {
        ProgressBarAnimation progressAnim = new ProgressBarAnimation(mSlideProgress, 0f, 100f);
        progressAnim.setDuration(HORIZONTAL_HAND_ANIM_DURATION);
        return progressAnim;
    }

    private class ProgressBarAnimation extends Animation {
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

            if(interpolatedTime == 1.0) return;/*remove the very first fully to*/

            progressBar.setProgress((int) value);
        }
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) backPressed();

        return super.dispatchKeyEvent(event);
    }

    public void backPressed() {
        if (mKeyEventCallback != null) mKeyEventCallback.onBackPressed();
        if (mIndicatorContainer.isShown()) onMinimumToRight();
    }
}
