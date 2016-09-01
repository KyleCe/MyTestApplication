package com.ce.game.myapplication.util;

import android.graphics.Paint;
import android.view.View;
import android.view.animation.Animation;

import junit.framework.Assert;

/**
 * Created by KyleCe on 2015/10/15.
 */
public class ViewU {
    public static void hide(View... views) {
        for (View v : views) if (v != null) v.setVisibility(View.GONE);
    }

    public static void invisible(View... views) {
        for (View v : views) if (v != null) v.setVisibility(View.INVISIBLE);
    }

    public static void show(View... views) {
        for (View v : views) if (v != null) v.setVisibility(View.VISIBLE);
    }

    public static void showAndHide(View sv, View hv) {
        show(sv);
        hide(hv);
    }

    public static void hideAndShow(View hv, View sv) {
        showAndHide(sv, hv);
    }

    public static boolean isVisible(View v) {
        return v != null && v.getVisibility() == View.VISIBLE;
    }

    public static void enClick(View... views) {
        for (View v : views) if (v != null) v.setClickable(true);
    }

    public static void disClick(View... views) {
        for (View v : views) if (v != null) v.setClickable(false);
    }

    public static void clearClickListener(View... views) {
        for (View v : views) if (v != null) v.setOnClickListener(null);
    }


    public static void setClickListener(View.OnClickListener listener, View... views) {
        for (View v : views) if (v != null) v.setOnClickListener(listener);
    }

    public static void setOnTouchListener(View.OnTouchListener listener, View... views) {
        for (View v : views) if (v != null) v.setOnTouchListener(listener);
    }

    /**
     * set background color by color int
     *
     * @param views views you want to set background color
     * @param color the color that user want to set
     */
    public static void setBackgroundColor(int color, View... views) {
        for (View v : views) if (v != null) v.setBackgroundColor(color);
    }

    /**
     * set click mListener for views
     *
     * @param listener the click mListener to assign
     * @param views    views
     */
    public static void clickListen(View.OnClickListener listener, View... views) {
        for (View v : views) if (v != null && listener != null) v.setOnClickListener(listener);
    }

    public static void setPaintAlpha(int alpha, Paint... paints) {
        for (Paint p : paints) if (p != null && legalAlpha(alpha)) p.setAlpha(alpha);
    }

    /**
     * @param alpha [0..255]
     */
    private static boolean legalAlpha(int alpha) {
        if (0 <= alpha && alpha <= 255) return true;
        return false;
    }

    public static void clearAnimation(View... views) {
        for (View v : views)
            if (v != null && v.getAnimation() != null) v.clearAnimation();
    }

    public static void startWithNewAnim(Animation anim, View... views) {
        Assert.assertNotNull(anim);

        for (View v : views) {
            if (v == null) continue;

            if (v.getAnimation() != null) v.clearAnimation();
            v.startAnimation(anim);
        }
    }

}
