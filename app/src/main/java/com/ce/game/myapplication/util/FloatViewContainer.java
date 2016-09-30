package com.ce.game.myapplication.util;

import android.content.Context;
import android.graphics.PixelFormat;
import android.view.View;
import android.view.WindowManager;

public class FloatViewContainer {
    public static final int DEFAULT_WINDOW_TYPE = -1;
    public static final int DEFAULT_WINDOW_FLAG = -1;

    protected WindowManager mWindowManager;
    protected WindowManager.LayoutParams mLayoutParams;
    protected View mView;

    public FloatViewContainer(Context context) {
        mWindowManager = (WindowManager) context.getApplicationContext().getSystemService(Context.WINDOW_SERVICE);

        int type = WindowManager.LayoutParams.TYPE_TOAST;
        if (AboutPhoneU.notFlyMe()) {
            if (Utilities.ATLEAST_KITKAT) {
                type = WindowManager.LayoutParams.TYPE_TOAST;
            } else {
                type = WindowManager.LayoutParams.TYPE_PHONE;
            }
        } else {
            if (Utilities.ATLEAST_MARSHMALLOW) {
                type = WindowManager.LayoutParams.TYPE_TOAST;
            } else {
                type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
            }
        }

        mLayoutParams = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT,
                type,
                WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);

        mLayoutParams.alpha = 1f;
        mLayoutParams.screenOrientation = 1;
    }


    public void addView(View view) {
        addView(view, DEFAULT_WINDOW_FLAG);
    }

    public void addView(View view, int flag) {
        addView(view, DEFAULT_WINDOW_TYPE, flag);
    }

    public void addView(View view, int type, int flag) {
        removeView();
        if (null != mWindowManager && null != view) {
            if(type != DEFAULT_WINDOW_TYPE) {
                mLayoutParams.type = type;
            }
            if(flag != DEFAULT_WINDOW_FLAG) {
                mLayoutParams.flags = flag;
            }
            mWindowManager.addView(view, mLayoutParams);
            this.mView = view;
        }
    }

    public void removeView() {
        if (null != mWindowManager && null != this.mView) {
            mWindowManager.removeView(this.mView);
            this.mView = null;
        }
    }
}
