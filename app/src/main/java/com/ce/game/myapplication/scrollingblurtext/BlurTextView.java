package com.ce.game.myapplication.scrollingblurtext;

import android.content.Context;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.TextView;

import com.ce.game.myapplication.util.DisplayUtil;

/**
 * Created by KyleCe on 2016/4/20.
 *
 * @author: KyleCe
 */
public class BlurTextView extends TextView {

    public BlurTextView(Context context) {
        super(context);
    }

    public BlurTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BlurTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Paint paint = new Paint();
        BlurMaskFilter blurMaskFilter = new BlurMaskFilter(15, BlurMaskFilter.Blur.NORMAL);
        paint.setMaskFilter(blurMaskFilter);

        paint.setTextSize(DisplayUtil.sp2px(30));

        canvas.drawText(getText().toString(), 0 + DisplayUtil.sp2px(5), getHeight() - DisplayUtil.sp2px(5), paint);

        // replace the original text drawing, must be commented
//        super.onDraw(canvas);
    }
}
