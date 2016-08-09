package com.ce.game.myapplication.userguideanim;

import android.content.Context;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.view.View;
import android.view.WindowManager.LayoutParams;

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

//        mLayoutParams = new WindowManager.LayoutParams(
//                WindowManager.LayoutParams.WRAP_CONTENT,
//                WindowManager.LayoutParams.WRAP_CONTENT,
////                300,300,
//
//
//                WindowManager.LayoutParams.TYPE_TOAST
//                /*must be this one to dispatch touch event on screen's other area*/
////                ,
////                WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED
//////                        | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
//////                        | WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN
//////                        | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
////                        |
////                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
////                |WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN
////                |~WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH
////                525824|67108864
////                262152
////                ,
////                PixelFormat.TRANSLUCENT
//        );
//
//        mLayoutParams.alpha = 1f;
//        mLayoutParams.screenOrientation = 1;
//
//        mLayoutParams.gravity = Gravity.CENTER_VERTICAL | Gravity.END | Gravity.RIGHT;
//
//
////        mLayoutParams.width = -2;
////        mLayoutParams.height = -2;
////        mLayoutParams.gravity = 85;
////        mLayoutParams.format = 1;
////        mLayoutParams.type = WindowManager.LayoutParams.TYPE_TOAST;
////        mLayoutParams.flags = 262152;
////        mLayoutParams.packageName = context.getPackageName();
//
//
//        mLayoutParams.width = -2;
//        mLayoutParams.height = -2;
////       mLayoutParams.gravity = 85;
//
//
//        mLayoutParams.format = PixelFormat.RGBA_8888;/*origin : 1*/
//        mLayoutParams.screenOrientation = 1;
//        mLayoutParams.type = WindowManager.LayoutParams.TYPE_TOAST;
//        mLayoutParams.flags =
//                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
////                |
////                        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
//                        |
//                        WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
//                        |
//
//                        WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH
//        ;
//
////       mLayoutParams.format = 1;
////       mLayoutParams.type = WindowManager.LayoutParams.TYPE_PHONE;
////       mLayoutParams.flags = 262152;
//        mLayoutParams.packageName = context.getPackageName();
//
//
//        mLayoutParams.height = -2;
//        mLayoutParams.format = 1;
//        mLayoutParams.screenOrientation = 1;
//        mLayoutParams.type = WindowManager.LayoutParams.TYPE_TOAST;
//        mLayoutParams.flags = 8;
//        mLayoutParams.packageName = context.getPackageName();


        LayoutParams layoutParams = new LayoutParams();
        layoutParams.width = -2;
        layoutParams.height = -2;
        layoutParams.gravity = 17;
        layoutParams.format = 1;
        layoutParams.type = LayoutParams.TYPE_TOAST;
        layoutParams.flags = LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH | LayoutParams.FLAG_NOT_FOCUSABLE
        |LayoutParams.FLAG_NOT_TOUCH_MODAL;
        layoutParams.packageName = context.getPackageName();

        mLayoutParams = layoutParams;
    }

    @Override
    public void setView(View view, int flag, @FloatViewModelTip.WidthHeight int widthHeight) {
        if (widthHeight == (WidthHeight.TIP_VIEW)) forTipView();
        else forWholeScreenView();

        super.setView(view);
//        super.setView(view, flag);
    }

    @Override
    public void clearView() {
        super.clearView();
    }

    public void setToken(IBinder token) {
        mLayoutParams.token = token;
    }

    public void forTipView() {
//        mLayoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
//        mLayoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
//
//        mLayoutParams.type = WindowManager.LayoutParams.TYPE_TOAST;
//
//        mLayoutParams.gravity = Gravity.CENTER_VERTICAL | Gravity.END | Gravity.RIGHT;
    }

    /**
     * //        mLayoutParams.type = WindowManager.LayoutParams.TYPE_PRIORITY_PHONE ;
     * //        mLayoutParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT ;
     * //        mLayoutParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_DIALOG ;
     * //        mLayoutParams.type = WindowManager.LayoutParams.TYPE_PHONE;
     * mLayoutParams.type = WindowManager.LayoutParams.TYPE_TOAST;
     * //        mLayoutParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY;
     * <p>
     * mLayoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
     * | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
     * | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE;
     * <p>
     * //        final int flags =WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
     * //                ;
     * //                |WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
     * // ;
     * //                |WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH;
     * <p>
     * <p>
     * //        mLayoutParams.flags = flags;
     */

    public void forWholeScreenView() {
//        mLayoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
//        mLayoutParams.height = WindowManager.LayoutParams.MATCH_PARENT;
//
//        mLayoutParams.type = WindowManager.LayoutParams.TYPE_TOAST;
//
//        mLayoutParams.gravity = Gravity.CENTER;
    }
}
