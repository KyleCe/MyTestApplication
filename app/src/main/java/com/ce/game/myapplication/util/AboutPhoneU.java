package com.ce.game.myapplication.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.ViewConfiguration;
import android.view.WindowManager;

import java.lang.reflect.Method;

/**
 * Created by KyleCe on 2016/6/16.
 *
 * @author: KyleCe
 */
public class AboutPhoneU {
    private final String PRODUCT = Build.PRODUCT.toLowerCase();
    private final String MODEL = Build.MODEL.toLowerCase();
    private final String MANUFACTURER = Build.MANUFACTURER.toLowerCase();
    private final String DISPLAY = Build.DISPLAY.toLowerCase();

    public static boolean notFlyMe() {
        try {
            final Method method = Build.class.getMethod("hasSmartBar");
            return method == null;
        } catch (final Exception e) {
            return true;
        }
    }

    public boolean hasPermanentMenuKey(@NonNull Context context) {
        return ViewConfiguration.get(context).hasPermanentMenuKey();
    }

    @SuppressLint("NewApi")
    public int getRealScreenHeightIncludeVirtualButtonBar(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            DisplayMetrics metrics = new DisplayMetrics();
            wm.getDefaultDisplay().getMetrics(metrics);
            wm.getDefaultDisplay().getRealMetrics(metrics);
            return metrics.heightPixels;
        } else {
            return getDisplayHeight(context);
        }
    }

    public int getDisplayHeight(@NonNull Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size.y;
    }

    @SuppressWarnings("NewApi")
    public void parseScreenSize(Point screenSize, final Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        if (Utilities.ATLEAST_JB_MR1) display.getRealSize(screenSize);
        else display.getSize(screenSize);
    }

    public boolean isHuaWei() {
        return MANUFACTURER.equalsIgnoreCase("huawei");
    }
}
