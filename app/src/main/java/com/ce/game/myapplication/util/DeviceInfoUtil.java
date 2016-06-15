package com.ce.game.myapplication.util;

import android.content.Context;
import android.view.WindowManager;

import java.util.Map;
import java.util.TreeMap;

public final class DeviceInfoUtil {
    static Map<Flag, Object> infos = new TreeMap<>();

    public DeviceInfoUtil(Context context) {
        infos = new TreeMap<>();
    }

    public enum Flag {
        SCREEN_WIDTH,
        SCREEN_HEIGHT,
    }

    public static int getScreenWidth(Context context) {
        int ret = 0;
        if (null == context) {
            return ret;
        }

        if (!infos.containsKey(Flag.SCREEN_WIDTH)) {
            WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            if (null != manager) {
                ret = manager.getDefaultDisplay().getWidth();
                infos.put(Flag.SCREEN_WIDTH, ret);
            }
        } else {
            ret = (int)infos.get(Flag.SCREEN_WIDTH);
        }
        return ret;
    }

    public static int getScreenHeight(Context context) {
        int ret = 0;
        if (null == context) {
            return ret;
        }

        if (!infos.containsKey(Flag.SCREEN_HEIGHT)) {
            WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            if (null != manager) {
                ret = manager.getDefaultDisplay().getWidth();
                infos.put(Flag.SCREEN_HEIGHT, ret);
            }
        } else {
            ret = (int)infos.get(Flag.SCREEN_HEIGHT);
        }
        return ret;
    }

}
