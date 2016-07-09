package com.ce.game.myapplication.ui.fonttext;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.support.annotation.DimenRes;
import android.support.annotation.NonNull;

import com.ce.game.myapplication.R;
import com.ce.game.myapplication.util.DU;

import junit.framework.Assert;

/**
 * Created by KyleCe on 2016/7/8.
 *
 * @author: KyleCe
 */
public class WeatherTimeImageHelper {

    @NonNull
    public static Bitmap generateTimeImageBitmap(Resources resources, int hour, int minute) {
        DU.assertNotNull(resources);

        int topBottomMargin = resources.getDimensionPixelSize(R.dimen.weather_time_text_top_bottom_margin);

        Bitmap bitmap = Bitmap.createBitmap((int) getPixels(resources, R.dimen.weather_time_text_canvas_width)
                , (int) getPixels(resources, R.dimen.weather_time_text_canvas_height),
                Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmap);

        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setTypeface(Typeface.createFromAsset(resources.getAssets(), "fonts/roboto.ttf"));
        paint.setColor(Color.WHITE);
        paint.setTextSize(getPixels(resources, R.dimen.weather_time_text_size));
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER));


        String textToDraw = String.format(resources.getConfiguration().locale, "%02d:%02d", hour, minute);

        Rect bounds = new Rect();
        paint.getTextBounds(textToDraw, 0, textToDraw.length(), bounds);

        canvas.drawText(textToDraw, 0, bounds.height() + topBottomMargin, paint);

        return bitmap;
    }

    private static float getPixels(Resources res, @DimenRes int resId) {
        Assert.assertNotNull(res);

        return res.getDimensionPixelSize(resId);
    }
}
