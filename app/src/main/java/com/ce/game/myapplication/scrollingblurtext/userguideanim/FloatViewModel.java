package com.ce.game.myapplication.scrollingblurtext.userguideanim;

import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Build;
import android.view.View;
import android.view.WindowManager;

import java.lang.reflect.Method;

public class FloatViewModel {
    private WindowManager windowManager;
    private WindowManager.LayoutParams layoutParams;
    private View view;

    public FloatViewModel(Context context) {
        windowManager = (WindowManager) context.getApplicationContext().getSystemService(Context.WINDOW_SERVICE);

        int type = 0;
        if (isFlyme()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                type = WindowManager.LayoutParams.TYPE_TOAST;
            } else {
                type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
            }
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                type = WindowManager.LayoutParams.TYPE_TOAST;
            } else {
                type = WindowManager.LayoutParams.TYPE_PHONE;
            }
        }

        layoutParams = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT,
                type,
                WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);

        layoutParams.alpha = 1f;
        layoutParams.screenOrientation = 1;
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
        if (null != windowManager && null != this.view) {
            windowManager.removeView(this.view);
            this.view = null;
        }
    }

    public void setView(View view, int flag) {
        clearView();
        if (null != windowManager && null != view) {
            layoutParams.flags = flag;
            windowManager.addView(view, layoutParams);
            this.view = view;
        }
    }
}
