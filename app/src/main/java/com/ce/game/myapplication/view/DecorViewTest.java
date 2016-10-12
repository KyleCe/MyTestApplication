package com.ce.game.myapplication.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.ce.game.myapplication.util.AboutPhoneU;

/**
 * Created by KyleCe on 2016/5/23.
 *
 * @author: KyleCe
 */
public class DecorViewTest extends FrameLayout {

    public DecorViewTest(final Context context) {
        this(context, null);
    }

    public DecorViewTest(final Context context, final AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DecorViewTest(final Context context, final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        Path path = new Path();
        canvas.drawARGB(128,125,0,0);

        int navHeight = new AboutPhoneU().getNavH(getContext());
        if (navHeight != 0) {
            int screenHeight = new AboutPhoneU().getRealScreenHeightIncludeVirtualButtonBar(getContext());
            int startY = screenHeight - navHeight;

            path.moveTo(0, startY);
            path.lineTo(canvas.getWidth(), startY);
            path.lineTo(canvas.getWidth(), screenHeight);
            path.lineTo(0, canvas.getHeight());

            Paint paint = new Paint();
            paint.setColor(Color.parseColor("#80000000"));
            paint.setStyle(Paint.Style.FILL);
            canvas.drawPath(path, paint);
        }
    }
}
