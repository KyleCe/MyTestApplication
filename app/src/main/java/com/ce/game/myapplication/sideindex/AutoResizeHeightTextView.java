package com.ce.game.myapplication.sideindex;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.TextView;

import com.ce.game.myapplication.util.DU;

/**
 * Created by KyleCe on 2016/5/26.
 *
 * @author: KyleCe
 */
public class AutoResizeHeightTextView extends TextView {
    public AutoResizeHeightTextView(Context context) {
        super(context);
    }

    public AutoResizeHeightTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AutoResizeHeightTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    /**
     * Resize text after measuring
     */
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
//        if (changed || mNeedsResize) {
//            int widthLimit = (right - left) - getCompoundPaddingLeft() - getCompoundPaddingRight();
//            int heightLimit = (bottom - top) - getCompoundPaddingBottom() - getCompoundPaddingTop();
//            resizeText(widthLimit, heightLimit);
//        }


        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        Paint paint = getPaint();
        float textSize = getTextSize();

        Rect bounds = new Rect();
        String text = getText().toString();
        if (TextUtils.isEmpty(text)) return;

        paint.getTextBounds(text, 0, text.length() - 1, bounds);
        int width = bounds.width();
        int height = bounds.height();

        DU.sd("bounds", height, width);

        textSize = 100;
        int maxWidth = getWidth();
        int maxHeight = canvas.getHeight();

        int textHeight = 0;
        DU.sd("origin before while", "text height=" + textHeight
                , "max height =" + maxHeight
                , "text size = " + textSize
        );
        do {
            DU.sd("while", "text height=" + textHeight
                    , "max height =" + maxHeight
                    , "text size = " + textSize
            );

            textHeight = getHeightOfMultiLineText(paint,text,textSize,maxWidth);
            textSize--;
        } while (textHeight > maxHeight);

//        paint.setTextSize(textSize);
        this.setTextSize(textSize);


//        canvas.drawText(text, 0, 0, paint);

//        getLayout().draw(canvas);

//        invalidate();


        super.onDraw(canvas);

    }
    private String mSections = "#ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public int getHeightOfMultiLineText(Paint paint, String text, float textSize, int maxWidth) {
        paint.setTextSize(textSize);

        text = mSections;

        TextView testTV = new TextView(getContext());
        testTV.setText(text);
        testTV.setTypeface(getTypeface());
        testTV.setText(text, TextView.BufferType.NORMAL);
        testTV.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
        int lineCount = testTV.getLineCount();

        lineCount = mSections.length();

        Rect bounds = new Rect();
        paint.getTextBounds("YY", 0, 2, bounds);
        // obtain space between lines
        double lineSpacing = Math.max(0, ((lineCount - 1) * bounds.height() * 0.25));
        lineSpacing = getPaint().getFontSpacing();

        return (int) Math.floor(lineSpacing + lineCount * bounds.height());
    }
}
