package com.ce.game.myapplication.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.StringDef;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.ce.game.myapplication.R;
import com.ce.game.myapplication.util.ViewU;


/**
 * Created by KyleCe on 2016/8/10.
 *
 * @author: KyleCe
 */

public class NumberKeyboardSingleButton extends FrameLayout implements NumberButtonBgInterface{

    protected Context mContext;

    protected TextView mTextView;

    @NumberKeyboardSingleButton.ButtonContent
    protected String mText;

    protected float TEXT_SCALE_RATIO = .5f;
//    protected final float KEY_SCALE_RATIO = .9f;

    public NumberKeyboardSingleButton(Context context) {
        this(context, null);
    }

    public NumberKeyboardSingleButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    @SuppressWarnings("ResourceType")
    public NumberKeyboardSingleButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;

        if (attrs != null && !isInEditMode()) {
            final TypedArray attributes = mContext.getTheme().obtainStyledAttributes(attrs, R.styleable.NumberKeyboard,
                    defStyleAttr, 0);

            mText = attributes.getString(R.styleable.NumberKeyboard_number_keyboard_text);
        }

        inflate(context, R.layout.first_anim_showcase_number_keyboard_single_button, this);
        init();
    }

    protected void init() {
        mTextView = (TextView) findViewById(R.id.keyboard_button_textview);

        ViewU.setClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onHold();
            }
        }, mTextView, this);

        setButtonText(mText);
    }

    public void setButtonText(String text) {
        if (TextUtils.isEmpty(text)) return;

        if (text.length() > 1) text = text.substring(0, 1);

        mTextView.setText(text);
    }

    @Override
    public void onHold() {
        mTextView.setBackgroundResource(R.drawable.first_anim_num_btn_bg_pressed);
    }

    @Override
    public void onReset() {
        mTextView.setBackgroundResource(R.drawable.first_anim_num_btn_bg);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int measureSpec =(Math.min(widthMeasureSpec,heightMeasureSpec)  );
        int measureSize = measureSpec & View.MEASURED_SIZE_MASK;
//        measureSize *= KEY_SCALE_RATIO;
//        measureSpec = measureSpec & ~View.MEASURED_SIZE_MASK | measureSize;
        super.onMeasure(measureSpec, measureSpec);

        mTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, (measureSize * TEXT_SCALE_RATIO));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawColor(Color.DKGRAY);

        Paint paint = mTextView.getPaint();
        Rect bounds = new Rect();
        int textSize = 1;
        while (bounds.height() < .5 * getMeasuredHeight()) {
            paint.setTextSize(textSize);
            paint.getTextBounds("8", 0, 1, bounds);
            textSize++;
        }

//        paint.setColor(Color.WHITE);
//        int xAlias = getPaddingLeft() + getLeftPaddingOffset() ;
//        int yAlias = getPaddingBottom() + getBottomPaddingOffset();
//        canvas.drawText("8", getLeft() + ((canvas.getWidth() - bounds.width()) >> 1) - xAlias
//                , getBottom() - ((canvas.getHeight() - bounds.height()) >> 1) - yAlias, paint);

//        paint.setStyle(Paint.Style.STROKE);
//        paint.setStrokeWidth();
//        canvas.drawCircle(getLeft() + (getWidth()>>1),getTop() +(getHeight()>>1),getWidth()>>1,paint);

//        mTextView.setTextSize((scale) * mTextView.getTextSize());
    }

    @StringDef({ButtonContent.K0, ButtonContent.K1, ButtonContent.K2, ButtonContent.K3, ButtonContent.K4, ButtonContent.K5, ButtonContent.K6
            , ButtonContent.K7, ButtonContent.K8, ButtonContent.K9, ButtonContent.K_BACK, ButtonContent.K_BACKSPACE})
    public @interface ButtonContent {
        String K0 = "0";
        String K1 = "1";
        String K2 = "2";
        String K3 = "3";
        String K4 = "4";
        String K5 = "5";
        String K6 = "6";
        String K7 = "7";
        String K8 = "8";
        String K9 = "9";
        String K_BACK = "10";
        String K_BACKSPACE = "11";
    }
}
