package com.ce.game.myapplication.scrollingblurtext.userguideanim;

import android.content.Context;
import android.graphics.PixelFormat;
import android.support.annotation.IntDef;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;

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
                WindowManager.LayoutParams.TYPE_TOAST,/*must be this one to dispatch touch event on screen's other area*/
                WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);

        mLayoutParams.alpha = 1f;
        mLayoutParams.screenOrientation = 1;

        mLayoutParams.gravity = Gravity.CENTER_VERTICAL | Gravity.END | Gravity.RIGHT;
    }

    @Override
    public void setView(View view, int flag, @FloatViewModelTip.WidthHeight int widthHeight) {
        if (widthHeight == (WidthHeight.TIP_VIEW)) forTipView();
        else forWholeScreenView();

        super.setView(view, flag);
    }

    @Override
    public void clearView() {
        super.clearView();
    }

    public void forTipView() {
        mLayoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        mLayoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;

        mLayoutParams.type = WindowManager.LayoutParams.TYPE_TOAST ;

        mLayoutParams.gravity = Gravity.CENTER_VERTICAL | Gravity.END | Gravity.RIGHT;
    }

    /**
     *

     //        mLayoutParams.type = WindowManager.LayoutParams.TYPE_PRIORITY_PHONE ;
     //        mLayoutParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT ;
     //        mLayoutParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_DIALOG ;
     //        mLayoutParams.type = WindowManager.LayoutParams.TYPE_PHONE;
     mLayoutParams.type = WindowManager.LayoutParams.TYPE_TOAST;
     //        mLayoutParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY;

     mLayoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
     | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
     | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE;

     //        final int flags =WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
     //                ;
     //                |WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
     // ;
     //                |WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH;


     //        mLayoutParams.flags = flags;
     * */

    public void forWholeScreenView() {
        mLayoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        mLayoutParams.height = WindowManager.LayoutParams.MATCH_PARENT;

        mLayoutParams.type = WindowManager.LayoutParams.TYPE_TOAST;

        mLayoutParams.gravity = Gravity.CENTER;
    }
}
