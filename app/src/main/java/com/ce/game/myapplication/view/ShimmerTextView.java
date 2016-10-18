package com.ce.game.myapplication.view;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.animation.LinearInterpolator;
import android.widget.TextView;

import com.ce.game.myapplication.R;

/**
 * Created on 2016/10/18
 * in BlaBla by Kyle
 */

public class ShimmerTextView extends TextView {
    protected int[] mColorArray;
    protected int[] mStableColorArray;
    protected float[] mPositionsArray;
    protected int mShimmerController = -1;
    private static final int ANIM_DURATION = 3200;
    protected ObjectAnimator mObjectAnimator;
    protected final int TARGET_CONTROLLER = 300;
    protected final int PERIOD_ANIMATOR_TAKEN = 120;

    public ShimmerTextView(Context context) {
        this(context, null);
    }

    public ShimmerTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ShimmerTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(context);
    }

    @SuppressWarnings({"deprecation"})
    private void init(Context context) {
        mColorArray = new int[]{
                context.getResources().getColor(R.color.shimmer_start_color),
                context.getResources().getColor(R.color.shimmer_start_color),
                context.getResources().getColor(R.color.shimmer_start_color),
                context.getResources().getColor(R.color.shimmer_end_color),
                context.getResources().getColor(R.color.shimmer_start_color),
                context.getResources().getColor(R.color.shimmer_start_color),
        };

        mStableColorArray = new int[]{
                context.getResources().getColor(R.color.shimmer_start_color),
                context.getResources().getColor(R.color.shimmer_start_color),
                context.getResources().getColor(R.color.shimmer_start_color),
                context.getResources().getColor(R.color.shimmer_start_color),
                context.getResources().getColor(R.color.shimmer_start_color),
                context.getResources().getColor(R.color.shimmer_start_color),
        };

        mPositionsArray = new float[]{0, .2f, .4f, .6f, .8f, 1};
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mShimmerController >= 0) {
            float alter = (float) mShimmerController / PERIOD_ANIMATOR_TAKEN * getWidth();
            LinearGradient shader = new LinearGradient(alter, getHeight() >> 1, alter + getWidth(), getHeight() >> 1,
                    mShimmerController <= PERIOD_ANIMATOR_TAKEN ? mColorArray : mStableColorArray, mPositionsArray, Shader.TileMode.REPEAT);
            getPaint().setShader(shader);
        }
        super.onDraw(canvas);
    }

    @Deprecated
    public int getShimmerController() {
        return mShimmerController;
    }

    @Deprecated
    public void setShimmerController(int shimmerController) {
        mShimmerController = shimmerController;
        invalidate();
    }

    public void startShimmer() {
        clearExistingAnimation();

        mObjectAnimator = ObjectAnimator.ofInt(this, "shimmerController", 0, TARGET_CONTROLLER);
        mObjectAnimator.setRepeatCount(ValueAnimator.INFINITE);
        mObjectAnimator.setRepeatMode(ValueAnimator.RESTART);
        mObjectAnimator.setInterpolator(new LinearInterpolator());
        mObjectAnimator.setDuration(ANIM_DURATION);
        mObjectAnimator.start();
    }

    public void stopShimmer() {
        clearExistingAnimation();
    }

    private void clearExistingAnimation() {
        if (mObjectAnimator == null) return;

        mObjectAnimator.cancel();
        mObjectAnimator = null;
    }

}
