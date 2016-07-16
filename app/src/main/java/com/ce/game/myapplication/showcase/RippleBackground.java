package com.ce.game.myapplication.showcase;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.RelativeLayout;

import com.ce.game.myapplication.R;

import java.util.ArrayList;

/**
 * Created by KyleCe on 2016/7/14.
 *
 * @author: KyleCe
 */

public class RippleBackground extends RelativeLayout {

    private static final int DEFAULT_RIPPLE_COUNT = 6;
    private static final int DEFAULT_DURATION_TIME = 3000;
    private static final float DEFAULT_SCALE = 6.0f;
    private static final int DEFAULT_FILL_TYPE = 0;

    private int mRippleColor;
    private float mRippleStrokeWidth;
    private float mRippleRadius;
    private int mRippleDurationTime;
    private int mRippleAmount;
    private int mRippleDelay;
    private float mRippleScale;
    private int mRippleType;
    private Paint mPaint;
    private volatile boolean mAnimationRunning = false;
    private AnimatorSet mAnimatorSet;
    private ArrayList<Animator> mAnimatorList;
    private LayoutParams mRippleParams;
    private ArrayList<RippleView> mRippleViewList = new ArrayList<>();

    public RippleBackground(Context context) {
        super(context);
    }

    public RippleBackground(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public RippleBackground(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(final Context context, final AttributeSet attrs) {
        if (isInEditMode())
            return;

        if (null == attrs) {
            throw new IllegalArgumentException("Attributes should be provided to this view,");
        }

        final TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RippleBackground);
        mRippleColor = typedArray.getColor(R.styleable.RippleBackground_rb_color, getResources().getColor(R.color.permission_lead_ripple_color));
        mRippleStrokeWidth = typedArray.getDimension(R.styleable.RippleBackground_rb_strokeWidth, getResources().getDimension(R.dimen.rippleStrokeWidth));
        mRippleRadius = typedArray.getDimension(R.styleable.RippleBackground_rb_radius, getResources().getDimension(R.dimen.rippleRadius));
        mRippleDurationTime = typedArray.getInt(R.styleable.RippleBackground_rb_duration, DEFAULT_DURATION_TIME);
        mRippleAmount = typedArray.getInt(R.styleable.RippleBackground_rb_rippleAmount, DEFAULT_RIPPLE_COUNT);
        mRippleScale = typedArray.getFloat(R.styleable.RippleBackground_rb_scale, DEFAULT_SCALE);
        mRippleType = typedArray.getInt(R.styleable.RippleBackground_rb_type, DEFAULT_FILL_TYPE);
        typedArray.recycle();

        mRippleDelay = mRippleDurationTime / mRippleAmount;

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setAntiAlias(true);
        if (mRippleType == DEFAULT_FILL_TYPE) {
            mRippleStrokeWidth = 0;
            mPaint.setStyle(Paint.Style.FILL);
        } else
            mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(mRippleColor);

        mRippleParams = new LayoutParams((int) (2 * (mRippleRadius + mRippleStrokeWidth)), (int) (2 * (mRippleRadius + mRippleStrokeWidth)));
        mRippleParams.addRule(CENTER_IN_PARENT, TRUE);

        mAnimatorSet = new AnimatorSet();
        mAnimatorSet.setInterpolator(new AccelerateDecelerateInterpolator());
        mAnimatorList = new ArrayList<>();

        playAnimSetTogether();
    }

    private void playAnimSetTogether() {
        for (int i = 0; i < mRippleAmount; i++) {
            RippleView rippleView = new RippleView(getContext());
            addView(rippleView, mRippleParams);
            mRippleViewList.add(rippleView);
            final ObjectAnimator scaleXAnimator = ObjectAnimator.ofFloat(rippleView, "ScaleX", 1.0f, mRippleScale);
            scaleXAnimator.setStartDelay(i * mRippleDelay);
            scaleXAnimator.setDuration(mRippleDurationTime);

            mAnimatorList.add(scaleXAnimator);
            final ObjectAnimator scaleYAnimator = ObjectAnimator.ofFloat(rippleView, "ScaleY", 1.0f, mRippleScale);
            scaleYAnimator.setStartDelay(i * mRippleDelay);
            scaleYAnimator.setDuration(mRippleDurationTime);

            mAnimatorList.add(scaleYAnimator);

            final ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(rippleView, "Alpha", 1.0f, 0f);
            alphaAnimator.setStartDelay(i * mRippleDelay);
            alphaAnimator.setDuration(mRippleDurationTime);

            mAnimatorList.add(alphaAnimator);
        }

        mAnimatorSet.playTogether(mAnimatorList);
    }


    private class RippleView extends View {

        public RippleView(Context context) {
            super(context);
            this.setVisibility(INVISIBLE);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            int radius = (Math.min(getWidth(), getHeight())) / 2;
            canvas.drawCircle(getWidth() >> 1, getHeight() >> 1, radius - mRippleStrokeWidth, mPaint);
        }
    }

    public void hideRippleView() {
        if (isRippleAnimationRunning())
            for (RippleView rippleView : mRippleViewList)
                rippleView.setVisibility(INVISIBLE);
    }

    public void showRippleView() {
        if (isRippleAnimationRunning())
            for (RippleView rippleView : mRippleViewList)
                rippleView.setVisibility(VISIBLE);
    }

    public void startRippleAnimation() {
        if (!isRippleAnimationRunning()) {
            for (RippleView rippleView : mRippleViewList) {
                rippleView.setVisibility(VISIBLE);
            }
            mAnimatorSet.start();

            mAnimationRunning = true;
        }
    }

    public void stopAnimatorList() {
        for (Animator a : mAnimatorList)
            a.cancel();

        for (RippleView rippleView : mRippleViewList)
            rippleView.setVisibility(INVISIBLE);

        mAnimationRunning = false;

    }

    public void restartAnimatorList() {
        for (Animator a : mAnimatorList)
            a.start();

        for (RippleView rippleView : mRippleViewList) {
            ObjectAnimator.ofFloat(rippleView, "ScaleX", 1, 1).start();
            ObjectAnimator.ofFloat(rippleView, "ScaleY", 1, 1).start();
            ObjectAnimator.ofFloat(rippleView, "Alpha", 0f, 1.0f).start();

            rippleView.setVisibility(VISIBLE);
        }

        mAnimationRunning = true;
    }

    public void restartAnimatorSet() {

        for (RippleView rippleView : mRippleViewList) {
            rippleView.setVisibility(VISIBLE);
        }
        mAnimatorSet.start();

        mAnimationRunning = true;
    }

    public void stopRippleAnimation() {
        if (isRippleAnimationRunning()) {
            mAnimatorSet.end();

            mAnimatorSet.cancel();

            mAnimationRunning = false;
        }
    }

    public boolean isRippleAnimationRunning() {
        return mAnimationRunning;
    }
}
