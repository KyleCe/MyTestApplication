package com.ce.game.myapplication.sideindex;

import android.content.Context;
import android.graphics.Canvas;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.TextView;

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

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        String text = getText().toString();
        if (TextUtils.isEmpty(text)) return;

        float textSize = 100;
        int maxHeight = getHeight();


        int textHeight = 0;
        do {
            textHeight = getHeightOfMultiLineText(text, textSize);
            textSize--;
        } while (textHeight > maxHeight);

        this.setTextSize(textSize);
        super.onDraw(canvas);
    }

    public int getHeightOfMultiLineText(String text, float textSize) {
        int lineCount = IndexImpl.INDEX_TABLE.length();

        TextView testTV = new TextView(getContext());
        testTV.setText(text);
        testTV.setLines(1);
        testTV.setTypeface(getTypeface());
        testTV.setTextSize(textSize);
        double testHeight = testTV.getLineHeight();

        return (int) Math.floor(0 + lineCount * testHeight);
    }
}
