package com.ce.game.myapplication.view.newpincode;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import com.ce.game.myapplication.R;
import com.ce.game.myapplication.util.ViewU;
import com.ce.game.myapplication.view.NumberKeyboardSingleButton;


/**
 * Created by KyleCe on 2016/8/10.
 *
 * @author: KyleCe
 */

public class NumberKeyboardSingleButtonForUnlock extends NumberKeyboardSingleButton implements KeyboardInterface{

    protected PressCallback mPressCallback = PressCallback.NULL;

    public NumberKeyboardSingleButtonForUnlock(Context context) {
        this(context, null);
    }

    public NumberKeyboardSingleButtonForUnlock(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NumberKeyboardSingleButtonForUnlock(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        setForUnlock();
    }

    private void setForUnlock() {
        ViewU.setClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mPressCallback.onPress(mText);
            }
        },  decideViewToResponse(), this);

        mTextView.setBackgroundResource(R.drawable.keyboard_num_btn_bg_selector);
    }

    @Override
    public View decideViewToResponse(){
        return mTextView;
    }

    public void setButtonText(String text) {
        if (TextUtils.isEmpty(text)) return;

        if (text.length() > 1) text = text.substring(0, 1);

        mTextView.setText(text);
    }

    @Override
    public void setPressCallback(PressCallback pressCallback) {
        mPressCallback = pressCallback;
    }
}
