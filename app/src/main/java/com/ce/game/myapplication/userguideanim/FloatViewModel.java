package com.ce.game.myapplication.userguideanim;

import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Build;
import android.view.View;
import android.view.WindowManager;

import java.lang.reflect.Method;

public class FloatViewModel {
    protected WindowManager mWindowManager;
    protected WindowManager.LayoutParams mLayoutParams;
    protected View mView;
    int mType = 0;

    public FloatViewModel(Context context) {
        mWindowManager = (WindowManager) context.getApplicationContext().getSystemService(Context.WINDOW_SERVICE);


        if (isFlyme()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                mType = WindowManager.LayoutParams.TYPE_TOAST;
            } else {
                mType = WindowManager.LayoutParams.TYPE_PHONE;
            }
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                mType = WindowManager.LayoutParams.TYPE_TOAST;
            } else {
                mType = WindowManager.LayoutParams.TYPE_PHONE;
            }
        }

        mLayoutParams = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT,
                mType,
                WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                |WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY
                |WindowManager.LayoutParams.FLAG_FULLSCREEN

                ,
                PixelFormat.TRANSLUCENT);

        mLayoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE |
                // this is to enable the notification to recieve touch events
                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL |
                // Draws over status bar
                WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN;

        mLayoutParams.alpha = 1f;
        mLayoutParams.screenOrientation = 1;
    }

    public static boolean isFlyme() {
        try {
            final Method method = Build.class.getMethod("hasSmartBar");
            return method != null;
        } catch (final Exception e) {
            return false;
        }
    }

    public void clearView() {
        if (null != mWindowManager && null != this.mView) {
            mWindowManager.removeView(this.mView);
            this.mView = null;
        }
    }

    public void setView(View view, int flag) {
        clearView();
        if (null != mWindowManager && null != view) {
            mLayoutParams.flags = flag;
            mWindowManager.addView(view, mLayoutParams);
            this.mView = view;
        }
    }

}
