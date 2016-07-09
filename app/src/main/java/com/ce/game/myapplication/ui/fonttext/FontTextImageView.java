package com.ce.game.myapplication.ui.fonttext;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.support.annotation.DimenRes;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.ce.game.myapplication.R;
import com.ce.game.myapplication.util.DU;

import junit.framework.Assert;

/**
 * Created by KyleCe on 2016/7/9.
 *
 * @author: KyleCe
 */
public class FontTextImageView extends ImageView {
    private Resources mResources;
    Paint mPaint;

    public FontTextImageView(Context context) {
        this(context, null);
    }

    public FontTextImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FontTextImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(context);
    }

    private void init(Context context) {
        this.mResources = context.getResources();
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setTypeface(Typeface.createFromAsset(mResources.getAssets(), "fonts/roboto.ttf"));
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.WHITE);
        mPaint.setTextSize(getPixels(mResources, R.dimen.weather_time_text_size));

        mTextTopBottomMargin = mResources.getDimensionPixelSize(R.dimen.weather_time_text_top_bottom_margin);
    }

    private int mHour;
    private int mMinute;
    String mTextToDraw;
    int mTextWidth;
    int mTextHeight;
    int mTextTopBottomMargin;
    int mTextLeftRightMargin;

    public void setHourMinuteAndInvalidate(int hour, int minute) {
        this.mHour = hour;
        this.mMinute = minute;


        mTextToDraw = String.format(mResources.getConfiguration().locale, "%02d:%02d", mHour, mMinute);

        DU.sd("text", mTextToDraw);

        Rect bounds = new Rect();
        mPaint.getTextBounds(mTextToDraw, 0, mTextToDraw.length(), bounds);

        mTextWidth = bounds.width();
        mTextHeight = bounds.height();

        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);

//        canvas.set

        canvas.drawText(
                mTextToDraw
                , mTextLeftRightMargin
                , mTextHeight + mTextTopBottomMargin, mPaint);

    }


    private static float getPixels(Resources res, @DimenRes int resId) {
        Assert.assertNotNull(res);

        return res.getDimensionPixelSize(resId);
    }
}
