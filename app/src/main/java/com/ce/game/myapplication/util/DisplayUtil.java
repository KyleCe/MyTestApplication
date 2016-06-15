package com.ce.game.myapplication.util;


import android.content.Context;
import android.content.res.Resources;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Shader;
import android.view.Display;
import android.view.WindowManager;
import android.widget.TextView;

public final class DisplayUtil {
    private static final float density = Resources.getSystem().getDisplayMetrics().density;
    private static final float scaledDensity = Resources.getSystem().getDisplayMetrics().scaledDensity;
    private static DisplayUtil instance;
    private static Paint paint = new Paint();

    private DisplayUtil() {
    }

    public static DisplayUtil getInstance() {
        if (instance == null) {
            synchronized (DisplayUtil.class) {
                if (instance == null) {
                    instance = new DisplayUtil();
                }
            }
        }
        return instance;
    }

    public static int sp2px(float sp) {
        return (int) (sp * scaledDensity + 0.5f);
    }

    public static int px2sp(float pix) {
        return (int) (pix / scaledDensity + 0.5f);
    }

    public static int dp2px(float dp) {
        return dp2Pix(dp);
    }

    public static int dp2Pix(float dp) {
        return (int) (dp * density + 0.5f);
    }

    public int pix2Dp(float pix) {
        return (int) (pix / density + 0.5f);
    }

    /**
     * 设置textview字体渐变颜色
     */
    public void setTextViewGradientColor(TextView textview, int start, int end) {
        int height = (int) textview.getTextSize();
        Shader shader = new LinearGradient(0, 0, 0, height, start, end, Shader.TileMode.CLAMP);
        textview.getPaint().setShader(shader);
    }

    static public float calcTextWidth(String str, float textSize) {
        paint.setTextSize(textSize);
        return paint.measureText(str);
    }

    static public float calcTextHeight(float textSize) {
        paint.setTextSize(textSize);
        return paint.getFontMetrics().bottom - paint.getFontMetrics().top;
    }

    /**
     * get screen width and height
     *
     * int[0,1] [width,height]
     */
    public static int[] getScreenWH(Context context) {
        int[] wh = {0, 0};
        if (context == null) return wh;

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        wh[0] = size.x;
        wh[1] = size.y;
        return wh;
    }
}
