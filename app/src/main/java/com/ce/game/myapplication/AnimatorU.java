package com.ce.game.myapplication;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;


/**
 * Created by KyleCe on 2015/9/24.
 */
public class AnimatorU {

    private static final long ANIM_DURATION = 500;// milliseconds

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
        view.setAnimation(AnimatorU.moveToViewBottomAnim(duration));
    }

    /**
     * @see #moveToLocation(View, long)
     */
    public static void moveToLocation(View view) {
        moveToLocation(view, ANIM_DURATION);
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
        moveToLocationFromViewTop(view, ANIM_DURATION);
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
        scaleToLocationFromCenter(view, ANIM_DURATION);
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
        view.setAnimation(scaleToLocationAnimFromCenter(duration));
    }


    /**
     * @see #moveFromTo(float, float, float, float, long)
     */
    private static TranslateAnimation moveFromTo(float fromX, float toX, float fromY, float toY) {
        return moveFromTo(fromX, toX, fromY, toY, ANIM_DURATION);
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

    public static void showFromRightToLeft(View view) {
        if (view == null) return;
        view.setVisibility(View.VISIBLE);
        view.setAnimation(moveFromTo(1, 0, 0, 0));
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
