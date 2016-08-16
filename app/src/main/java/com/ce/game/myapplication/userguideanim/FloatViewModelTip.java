package com.ce.game.myapplication.userguideanim;

import android.content.Context;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;

import com.ce.game.myapplication.util.DU;

/**
 * Created by KyleCe on 2016/7/15.
 *
 * @author: KyleCe
 */
public class FloatViewModelTip extends FloatViewModel
        implements GuideViewInterface.SwitchLayoutParams {

    @IntDef({WidthHeight.TIP_VIEW, WidthHeight.WHOLE_VIEW})
    public @interface WidthHeight {
        int TIP_VIEW = 1;
        int WHOLE_VIEW = 2;
    }


    public FloatViewModelTip(Context context) {
        super(context);

        mLayoutParams = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_TOAST
                , WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                ,
                PixelFormat.TRANSLUCENT
        );
    }

    public void updateView(View view) {
        mLayoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        mLayoutParams.height = WindowManager.LayoutParams.MATCH_PARENT;

        DU.sd("manager params", "match");

        mLayoutParams.gravity = Gravity.END | Gravity.CENTER_VERTICAL;

        mWindowManager.updateViewLayout(view, mLayoutParams);
    }

    public void resetToWrapContent(View view, int x, int y) {
        mLayoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        mLayoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;

        mLayoutParams.gravity = Gravity.END|Gravity.AXIS_PULL_BEFORE;

        DU.sd("manager params", "wrap");

        mLayoutParams.x = x;
        mLayoutParams.y = y;

        mWindowManager.updateViewLayout(view, mLayoutParams);
    }

    @Override
    public void setView(View view, int flag, @FloatViewModelTip.WidthHeight int widthHeight) {
        if (widthHeight == (WidthHeight.TIP_VIEW)) forTipView();
        else forWholeScreenView();

        super.setView(view);
    }

    public void setToken(IBinder token) {
        mLayoutParams.token = token;
    }

    public void forTipView() {
        mLayoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        mLayoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;

        mLayoutParams.gravity = Gravity.CENTER_VERTICAL | Gravity.END | Gravity.RIGHT;
    }

    public void forWholeScreenView() {
        mLayoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        mLayoutParams.height = WindowManager.LayoutParams.MATCH_PARENT;
    }
}
