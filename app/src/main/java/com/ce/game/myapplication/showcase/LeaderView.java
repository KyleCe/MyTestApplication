package com.ce.game.myapplication.showcase;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;

import com.ce.game.myapplication.R;
import com.ce.game.myapplication.util.ViewU;

/**
 * Created by KyleCe on 2016/7/13.
 *
 * @author: KyleCe
 */
public class LeaderView extends FrameLayout {
    private View mView;

    private RippleBackground mRippleBackground;
    private View mHand;

    private Paint mBgPaint;
    private Path mBgPath;

    private int mStartY;
    private int mStopY;

    private int mRippleRightOffset;

    private static final float CENTER_HAND_X_AXIS_FACTOR = .3f;
    private static final float CENTER_HAND_Y_AXIS_FACTOR = .5f;

    private TranslateAnimation tran;
    private static final int ANIMATION_DURATION = 800;
    private static final int HOLDING_HAND_DELAY = 1000;

    private Handler mHandler = new Handler(Looper.getMainLooper());

    public LeaderView(Context context) {
        this(context, null);
    }

    public LeaderView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LeaderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        View view = inflate(getContext(), R.layout.permission_lead_ripple_view, this);
        mRippleBackground = (RippleBackground) view.findViewById(R.id.ripple);
        mHand = findViewById(R.id.lead_hand);

        mRippleRightOffset = context.getResources().getDimensionPixelOffset(R.dimen.rippleRightOffset);

        init();
    }

    private void init() {

        mBgPaint = new Paint();

        mBgPaint.setColor(getResources().getColor(R.color.leader_view_bg));
    }

    public void assignTargetView(View v) {
        mView = v;

        mStartY = mView.getTop();
        mStopY = mStartY + mView.getHeight();

        mBgPath = generateGapPath(mStartY, mStopY);

        animRippleAndHand(mStartY, mStopY);

        invalidate();
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void animRippleAndHand(final int start, int stop) {
        int xPoint = getWidth() - mRippleBackground.getWidth() - mRippleRightOffset;
        int yPoint = (start + stop - mRippleBackground.getHeight()) >> 1;

        mRippleBackground.animate().translationX(xPoint)
                .translationY(yPoint)
                .setDuration(0)
                .start();

        mHand.animate().translationX(mHand.getWidth() * CENTER_HAND_X_AXIS_FACTOR)
                .translationY(mRippleBackground.getHeight() * CENTER_HAND_Y_AXIS_FACTOR)
                .setDuration(0)
                .withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        ViewU.show(mRippleBackground);
                    }
                })
                .start();

        generalAnimationStrategy();
    }

    private void generalAnimationStrategy() {
        ScaleAnimation scale = new ScaleAnimation(1f, .8f, 1f, .8f, Animation.RELATIVE_TO_SELF
                , CENTER_HAND_X_AXIS_FACTOR, Animation.RELATIVE_TO_PARENT, CENTER_HAND_Y_AXIS_FACTOR);
        scale.setDuration(ANIMATION_DURATION);

        ScaleAnimation reverseScale = new ScaleAnimation(.8f, 1f, .8f, 1f, Animation.RELATIVE_TO_SELF
                , CENTER_HAND_X_AXIS_FACTOR, Animation.RELATIVE_TO_PARENT, CENTER_HAND_Y_AXIS_FACTOR);
        reverseScale.setDuration(ANIMATION_DURATION);

        TranslateAnimation reverseTran = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0
                , Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, .2f);
        reverseTran.setDuration(ANIMATION_DURATION);

        tran = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0
                , Animation.RELATIVE_TO_SELF, .2f, Animation.RELATIVE_TO_SELF, 0);
        tran.setDuration(ANIMATION_DURATION);

        final AnimationSet set = new AnimationSet(true);
        set.setInterpolator(new AccelerateDecelerateInterpolator());
        set.addAnimation(scale);
        set.addAnimation(tran);
        set.setFillAfter(true);
        set.setFillEnabled(true);

        final AnimationSet reverseSet = new AnimationSet(true);
        reverseSet.addAnimation(reverseScale);
        reverseSet.addAnimation(reverseTran);
        reverseSet.setFillAfter(true);
        reverseSet.setFillEnabled(true);

        set.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                mRippleBackground.stopAnimatorList();
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mRippleBackground.restartAnimatorList();

                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mHand.startAnimation(reverseSet);
                    }
                }, HOLDING_HAND_DELAY);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        reverseSet.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mHand.startAnimation(set);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });

        mRippleBackground.startRippleAnimation();
        mHand.startAnimation(set);
    }

    private Path generateGapPath(int start, int stop) {
        Path path = new Path();
        path.lineTo(getWidth(), 0);
        path.lineTo(getWidth(), start);
        path.lineTo(0, start);

        path.moveTo(0, stop);
        path.lineTo(getWidth(), stop);
        path.lineTo(getWidth(), getBottom());
        path.lineTo(0, getBottom());

        return path;
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        if (mView != null) canvas.drawPath(mBgPath, mBgPaint);

        super.dispatchDraw(canvas);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (mView == null) return true;

        return mStartY < ev.getY() && ev.getY() < mStopY ?
                super.dispatchTouchEvent(ev) : true;
    }
}
