package com.ce.game.myapplication.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.StringDef;
import android.text.TextUtils;
import android.util.AttributeSet;
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

    protected float TEXT_SCALE_RATIO = .3f;
    protected final float KEY_SCALE_RATIO = .8f;

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
        measureSize *= KEY_SCALE_RATIO;
        measureSpec = measureSpec & ~View.MEASURED_SIZE_MASK | measureSize;
        super.onMeasure(measureSpec, measureSpec);

        mTextView.setTextSize((measureSize * TEXT_SCALE_RATIO));
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
