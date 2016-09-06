package com.ce.game.myapplication;

import android.os.SystemClock;
import android.support.annotation.IntDef;
import android.support.annotation.StringDef;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;

import com.ce.game.myapplication.util.DU;
import com.ce.game.myapplication.util.ViewU;

/**
 * Created by KyleCe on 2015/9/24.
 * <p>
 * <p>
 * <p>
 * usage:
 * <p>
 * AnimatorU.show(mContainer);
 * AnimatorU.show(mContainer, AnimatorU.Direction.left);
 * AnimatorU.show(mContainer, 1000);
 * AnimatorU.show(mContainer, AnimatorU.Direction.right,listener);
 * AnimatorU.show(mContainer, AnimatorU.Direction.top, 1000);
 * AnimatorU.show(mContainer, AnimatorU.Direction.bottom, 1000, listener);
 * AnimatorU.show(AnimatorU.AnimationType.self,mContainer);
 * AnimatorU.show(AnimatorU.AnimationType.self,mContainer, AnimatorU.Direction.left);
 * AnimatorU.hide(AnimatorU.AnimationType.parent,mContainer, 1000);
 * AnimatorU.show(AnimatorU.AnimationType.parent,mContainer, AnimatorU.Direction.right, listener);
 * AnimatorU.show(AnimatorU.AnimationType.parent,mContainer, AnimatorU.Direction.top, 1000);
 * AnimatorU.hide(AnimatorU.AnimationType.parent,mContainer, AnimatorU.Direction.bottom, 1000,listener);
 */
public class AnimatorU {

    private static final long DEFAULT_ANIM_DURATION = 500;// milliseconds

    @Direction
    private static final String DEFAULT_DIRECTION = Direction.bottom;

    @AnimationType
    private static final int DEFAULT_ANIMATION_TYPE = AnimationType.parent;

    @StringDef({Direction.left, Direction.top, Direction.right, Direction.bottom})
    public @interface Direction {
        String left = "l";
        String top = "t";
        String right = "r";
        String bottom = "b";
    }

    @IntDef({AnimationType.self, AnimationType.parent})
    public @interface AnimationType {
        int self = Animation.RELATIVE_TO_SELF;
        int parent = Animation.RELATIVE_TO_PARENT;
    }

    public static void show(View v) {
        show(v, DEFAULT_DIRECTION);
    }

    public static void show(View v, final long duration) {
        show(v, DEFAULT_DIRECTION, duration);
    }

    public static void show(View v, @Direction String direction) {
        show(v, direction, DEFAULT_ANIM_DURATION);
    }

    public static void show(View v, final Animation.AnimationListener listener) {
        show(v, DEFAULT_DIRECTION, DEFAULT_ANIM_DURATION, listener);
    }

    public static void show(View v, @Direction String direction, final long duration) {
        show(v, direction, duration, null);
    }

    public static void show(View v, @Direction String direction, final Animation.AnimationListener listener) {
        show(v, direction, DEFAULT_ANIM_DURATION, listener);
    }

    public static void show(View v, @Direction String direction, final long duration, final Animation.AnimationListener listener) {
        show(DEFAULT_ANIMATION_TYPE, v, direction, duration, listener);
    }

    public static void show(@AnimationType final int type, View v) {
        show(type, v, DEFAULT_DIRECTION);
    }

    public static void show(@AnimationType final int type, View v, final long duration) {
        show(type, v, DEFAULT_DIRECTION, duration);
    }

    public static void show(@AnimationType final int type, View v, final Animation.AnimationListener listener) {
        show(type, v, DEFAULT_DIRECTION, DEFAULT_ANIM_DURATION, listener);
    }

    public static void show(@AnimationType final int type, View v, @Direction String direction) {
        show(type, v, direction, DEFAULT_ANIM_DURATION);
    }

    public static void show(@AnimationType final int type, View v, @Direction String direction, final long duration) {
        show(type, v, direction, duration, null);
    }

    public static void show(@AnimationType final int type, View v, @Direction String direction, final Animation.AnimationListener listener) {
        show(type, v, direction, DEFAULT_ANIM_DURATION, listener);
    }

    public static void show(@AnimationType final int type,final View v, @Direction String direction, final long duration, final Animation.AnimationListener listener) {
        if (v == null || duration <= 0) return;

        TranslateAnimation anim = generateTranslateAnimation(mShowAnimArray, type, direction);
        anim.setDuration(duration);

        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                if (listener != null) listener.onAnimationStart(animation);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (listener != null) listener.onAnimationEnd(animation);
                ViewU.show(v);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                if (listener != null) listener.onAnimationRepeat(animation);
            }
        });

        v.startAnimation(anim);
    }

    public static void hide(View v) {
        hide(v, DEFAULT_DIRECTION);
    }

    public static void hide(View v, final long duration) {
        hide(v, DEFAULT_DIRECTION, duration);
    }

    public static void hide(View v, @Direction String direction) {
        hide(v, direction, DEFAULT_ANIM_DURATION);
    }

    public static void hide(View v, final Animation.AnimationListener listener) {
        hide(v, DEFAULT_DIRECTION, DEFAULT_ANIM_DURATION, listener);
    }

    public static void hide(View v, @Direction String direction, final long duration) {
        hide(v, direction, duration, null);
    }

    public static void hide(View v, @Direction String direction, final Animation.AnimationListener listener) {
        hide(v, direction, DEFAULT_ANIM_DURATION, listener);
    }

    public static void hide(View v, @Direction String direction, final long duration, final Animation.AnimationListener listener) {
        hide(DEFAULT_ANIMATION_TYPE, v, direction, duration, listener);
    }

    public static void hide(@AnimationType final int type, View v) {
        hide(type, v, DEFAULT_DIRECTION);
    }

    public static void hide(@AnimationType final int type, View v, final long duration) {
        hide(type, v, DEFAULT_DIRECTION, duration);
    }

    public static void hide(@AnimationType final int type, View v, final Animation.AnimationListener listener) {
        hide(type, v, DEFAULT_DIRECTION, DEFAULT_ANIM_DURATION, listener);
    }

    public static void hide(@AnimationType final int type, View v, @Direction String direction) {
        hide(type, v, direction, DEFAULT_ANIM_DURATION);
    }

    public static void hide(@AnimationType final int type, View v, @Direction String direction, final long duration) {
        hide(type, v, direction, duration, null);
    }

    public static void hide(@AnimationType final int type, View v, @Direction String direction, final Animation.AnimationListener listener) {
        hide(type, v, direction, DEFAULT_ANIM_DURATION, listener);
    }

    public static void hide(@AnimationType final int type, final View v, @Direction String direction, final long duration, final Animation.AnimationListener listener) {
        if (v == null || duration <= 0) return;

        TranslateAnimation anim = generateTranslateAnimation(mHideAnimArray, type, direction);
        anim.setDuration(duration);

        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                if (listener != null) listener.onAnimationStart(animation);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (listener != null) listener.onAnimationEnd(animation);
                ViewU.hide(v);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                if (listener != null) listener.onAnimationRepeat(animation);
            }
        });

        v.startAnimation(anim);
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

    private static TranslateAnimation generateTranslateAnimation(float[][] xy, @AnimationType final int type, @Direction String direction) {
        switch (direction) {
            case Direction.left:
                return new TranslateAnimation(type, xy[0][0], type, xy[0][1], type, xy[0][2], type, xy[0][3]);
            case Direction.top:
                return new TranslateAnimation(type, xy[1][0], type, xy[1][1], type, xy[1][2], type, xy[1][3]);
            case Direction.right:
                return new TranslateAnimation(type, xy[2][0], type, xy[2][1], type, xy[2][2], type, xy[2][3]);
            case Direction.bottom:
                return new TranslateAnimation(type, xy[3][0], type, xy[3][1], type, xy[3][2], type, xy[3][3]);
            default:
                throw new UnsupportedOperationException("unsupported direction");
        }
    }

    /**
     * 从控件所在位置移动到控件的底部
     *
     * @return translate animation
     */
    public static TranslateAnimation moveToViewBottomAnim(long duration) {
        TranslateAnimation anim = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                0.0f, Animation.RELATIVE_TO_SELF, 1.0f);
        anim.setDuration(duration);
        return anim;
    }

    /**
     * 从控件的底部移动到控件所在位置
     *
     * @return
     */
    public static TranslateAnimation moveToViewLocationFromBottomAnim(long duration) {
        TranslateAnimation anim = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                1.0f, Animation.RELATIVE_TO_SELF, 0.0f);
        anim.setDuration(duration);
        return anim;
    }

    /**
     * 从控件的顶部移动到控件所在位置
     *
     * @return
     */
    public static TranslateAnimation moveToViewLocationFromTopAnim(long duration) {
        TranslateAnimation anim = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                -1.0f, Animation.RELATIVE_TO_SELF, 0.0f);
        anim.setDuration(duration);
        return anim;
    }

    public static void hideToBottom(View view, long duration) {
        if (view == null) return;
        view.setVisibility(View.GONE);
        view.setAnimation(moveToViewBottomAnim(duration));
    }

    /**
     * @see #moveToLocation(View, long)
     */
    public static void moveToLocation(View view) {
        moveToLocation(view, DEFAULT_ANIM_DURATION);
    }

    /**
     * 从控件的底部移动到控件所在位置
     */
    public static void moveToLocation(View view, long duration) {
        if (view == null) return;
        view.setVisibility(View.VISIBLE);
        view.setAnimation(moveToViewLocationFromBottomAnim(duration));
    }

    /**
     * @see #moveToLocationFromViewTop(View, long)
     */
    public static void moveToLocationFromViewTop(View view) {
        moveToLocationFromViewTop(view, DEFAULT_ANIM_DURATION);
    }

    /**
     * 从控件的顶部移动到控件所在位置
     */
    public static void moveToLocationFromViewTop(View view, long duration) {
        if (view == null) return;
        view.setVisibility(View.VISIBLE);
        view.setAnimation(moveToViewLocationFromTopAnim(duration));
    }

    /**
     * get the scale from view center for duration
     *
     * @param duration duration for anim
     * @return the scale anim
     */
    private static ScaleAnimation scaleToLocationAnimFromCenter(long duration) {
        ScaleAnimation anim = new ScaleAnimation(1, 1, /* from x to x*/
                .5f, 1,/*from y to y*/
                Animation.RELATIVE_TO_SELF, 1, /*pivotXType,pivotXValue */
                Animation.RELATIVE_TO_PARENT, 0.5f /*pivotYType,pivotYValue */);
        anim.setDuration(duration);
        return anim;
    }


    /**
     * @see #scaleToLocationFromCenter(View, long)
     */
    public static void scaleToLocationFromCenter(View view) {
        scaleToLocationFromCenter(view, DEFAULT_ANIM_DURATION);
    }

    /**
     * scale to location from view self center
     *
     * @param view     view to scale
     * @param duration duration for anim unit: milliseconds, 1second is good for a whole screen view
     */
    public static void scaleToLocationFromCenter(View view, long duration) {
        if (view == null) return;
        view.setVisibility(View.VISIBLE);
        view.startAnimation(scaleToLocationAnimFromCenter(duration));
        DU.sd("anim log", SystemClock.elapsedRealtime());
    }

    /**
     * get the alpha anim
     *
     * @param duration duration for anim
     * @param from     from alpha
     * @param to       to alpha
     * @return the alpha anim
     */
    private static AlphaAnimation alphaAnim(float from, float to, long duration) {
        AlphaAnimation anim = new AlphaAnimation(from, to);
        anim.setDuration(duration);
        return anim;
    }


    /**
     * @see #alphaOut(View, long)
     */
    public static void alphaOut(View view) {
        alphaOut(view, DEFAULT_ANIM_DURATION);
    }

    /**
     * alpha out view
     *
     * @param view     view to scale
     * @param duration duration for anim unit: milliseconds
     */
    public static void alphaOut(View view, long duration) {
        alphaOut(view, duration, null);
    }

    /**
     * alpha out view
     *
     * @param view     view to scale
     * @param duration duration for anim unit: milliseconds
     * @param listener anim listener to set
     */
    public static void alphaOut(View view, long duration, Animation.AnimationListener listener) {
        if (view == null) return;
        view.setVisibility(View.VISIBLE);
        AlphaAnimation anim = alphaAnim(1, 0, duration);
        if (listener != null) anim.setAnimationListener(listener);

        // should use the start instead of set fun, otherwise the listener may not take effect
        view.startAnimation(anim);
    }

    /**
     * @see #alphaIn(View, long)
     */
    public static void alphaIn(View view) {
        alphaIn(view, DEFAULT_ANIM_DURATION);
    }

    /**
     * alpha out view
     *
     * @param view     view to scale
     * @param duration duration for anim unit: milliseconds
     */
    public static void alphaIn(View view, long duration) {
        if (view == null) return;
        view.setVisibility(View.VISIBLE);
        view.setAnimation(alphaAnim(0, 1, duration));
    }


    /**
     * @see #moveFromTo(float, float, float, float, long)
     */
    private static TranslateAnimation moveFromTo(float fromX, float toX, float fromY, float toY) {
        return moveFromTo(fromX, toX, fromY, toY, DEFAULT_ANIM_DURATION);
    }

    /**
     * @return
     */
    private static TranslateAnimation moveFromTo(float fromX, float toX, float fromY, float toY, long duration) {
        TranslateAnimation mHiddenAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF, fromX,
                Animation.RELATIVE_TO_SELF, toX, Animation.RELATIVE_TO_SELF,
                fromY, Animation.RELATIVE_TO_SELF, toY);
        mHiddenAction.setDuration(duration);
        return mHiddenAction;
    }

    public static void hideFromLeftToRight(View view) {
        if (view == null) return;
        view.setAnimation(moveFromTo(0, 1, 0, 0));
        view.setVisibility(View.INVISIBLE);
    }

    /**
     * @see #showFromRightToLeft(View, Animation.AnimationListener)
     */
    public static void showFromRightToLeft(View view) {
        showFromRightToLeft(view, null);
    }

    public static void showFromRightToLeft(View view, Animation.AnimationListener listener) {
        showFromRightToLeft(view, 300, listener);
    }

    /**
     * show from screen right to left
     *
     * @param view     view to show
     * @param dura     duration
     * @param listener animator listener to attach
     */
    public static void showFromRightToLeft(View view, long dura, Animation.AnimationListener listener) {
        if (view == null) return;
        view.setVisibility(View.VISIBLE);
        TranslateAnimation tran = moveFromTo(1, 0, 0, 0);
        tran.setDuration(dura);
        if (listener != null) tran.setAnimationListener(listener);

        view.startAnimation(tran);
    }

    public static void hideFromRightToLeft(View view) {
        if (view == null) return;
        view.setAnimation(moveFromTo(0, -1, 0, 0));
        view.setVisibility(View.INVISIBLE);
    }

    public static void showFromLeftToRight(View view) {
        if (view == null) return;
        view.setVisibility(View.VISIBLE);
        view.setAnimation(moveFromTo(-1, 0, 0, 0));
    }

}
