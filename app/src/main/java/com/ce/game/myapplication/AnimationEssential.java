package com.ce.game.myapplication;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

/**
 * Created by KyleCe on 2016/9/1.
 *
 * @author: KyleCe
 */

public class AnimationEssential {
    private static final String TAG = AnimationEssential.class.getSimpleName();

    protected View mView;
    protected long mDuration;
    @AnimatorU.Direction
    protected String mDirection;

    @AnimatorU.AnimationType
    protected int mType;

    protected Animation.AnimationListener mListener;

    protected Runnable mStartAction;

    protected Runnable mEndAction;

    protected int mEndVisibility;

    protected TranslateAnimation mAnimation;

    private static final long DEFAULT_ANIM_DURATION = 500;// milliseconds

    @AnimatorU.Direction
    private static final String DEFAULT_DIRECTION = AnimatorU.Direction.bottom;

    @AnimatorU.AnimationType
    private static final int DEFAULT_ANIMATION_TYPE = AnimatorU.AnimationType.parent;

    private AnimationEssential(View view, long duration, String direction, int type, Animation.AnimationListener listener
            , Runnable startAction, Runnable endAction, int endVisibility) {
        this.mView = view;
        this.mDuration = duration;
        this.mDirection = direction;
        this.mType = type;
        this.mListener = listener;
        this.mStartAction = startAction;
        this.mEndAction = endAction;
        this.mEndVisibility = endVisibility;

        if (view == null) throw new NullPointerException(TAG + "::mView to play is null");

        prepareTask();
    }

    private void prepareTask() {
        mAnimation = generateTranslateAnimation(mShowAnimArray, mType, mDirection);
        mAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                if (mListener != null) mListener.onAnimationStart(animation);
                if (mStartAction != null) mStartAction.run();
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (mListener != null) mListener.onAnimationEnd(animation);
                if (mEndAction != null) mEndAction.run();
                mView.setVisibility(mEndVisibility);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                if (mListener != null) mListener.onAnimationEnd(animation);
            }
        });
    }

    public void start() {
        mView.startAnimation(mAnimation);
    }


    private static final float mShowAnimArray[][] = {
            {-1.f, .0f, .0f, .0f},
            {.0f, .0f, -1.0f, .0f},
            {1.0f, .0f, .0f, .0f},
            {.0f, .0f, 1.f, .0f},
    };

    private static final float mHideAnimArray[][] = {
            {.0f, -1.f, .0f, .0f},
            {.0f, .0f, .0f, -1.0f},
            {.0f, 1.0f, .0f, .0f},
            {.0f, .0f, .0f, 1.f},
    };

    private static TranslateAnimation generateTranslateAnimation(float[][] xy, @AnimatorU.AnimationType final int type, @AnimatorU.Direction String direction) {
        switch (direction) {
            case AnimatorU.Direction.left:
                return new TranslateAnimation(type, xy[0][0], type, xy[0][1], type, xy[0][2], type, xy[0][3]);
            case AnimatorU.Direction.top:
                return new TranslateAnimation(type, xy[1][0], type, xy[1][1], type, xy[1][2], type, xy[1][3]);
            case AnimatorU.Direction.right:
                return new TranslateAnimation(type, xy[2][0], type, xy[2][1], type, xy[2][2], type, xy[2][3]);
            case AnimatorU.Direction.bottom:
                return new TranslateAnimation(type, xy[3][0], type, xy[3][1], type, xy[3][2], type, xy[3][3]);
            default:
                throw new UnsupportedOperationException("unsupported mDirection");
        }
    }


    public static class Builder {
        private View mView;
        private long mDuration = DEFAULT_ANIM_DURATION;
        @AnimatorU.Direction
        private String mDirection = DEFAULT_DIRECTION;
        @AnimatorU.AnimationType
        private int mType = DEFAULT_ANIMATION_TYPE;
        private Animation.AnimationListener mListener;
        private Runnable mStartAction;
        private Runnable mEndAction;
        private int mEndVisibility = View.VISIBLE;

        public Builder setView(View view) {
            this.mView = view;
            return this;
        }

        public Builder setDuration(long duration) {
            this.mDuration = duration;
            return this;
        }

        public Builder setDirection(String direction) {
            mDirection = direction;
            return this;
        }

        public Builder setType(int type) {
            mType = type;
            return this;
        }

        public Builder setListener(Animation.AnimationListener listener) {
            mListener = listener;
            return this;
        }

        public Builder setStartAction(Runnable startAction) {
            mStartAction = startAction;
            return this;
        }

        public Builder setEndAction(Runnable endAction) {
            mEndAction = endAction;
            return this;
        }

        public Builder setEndVisiblity(int endVisiblity) {
            mEndVisibility = endVisiblity;
            return this;
        }

        public AnimationEssential build() {
            return new AnimationEssential(mView, mDuration, mDirection, mType, mListener, mStartAction, mEndAction, mEndVisibility);
        }
    }
}
