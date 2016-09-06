package com.ce.game.myapplication.view;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.ce.game.myapplication.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by KyleCe on 2016/8/10.
 *
 * @author: KyleCe
 */

public class ShowcasePinCode extends FrameLayout implements NumberButtonClickInterface {

    protected Context mContext;

    private final int DEFAULT_PIN_CODE_LEN = 4;
    private final int DEFAULT_BUTTON_NUMBER = 10;

    List<NumberKeyboardSingleButton> mButtonList;

    List<ImageView> mPinCodeRoundList;

    public ShowcasePinCode(Context context) {
        this(context, null);
    }

    public ShowcasePinCode(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ShowcasePinCode(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;

        inflate(context, R.layout.first_anim_showcase_password_pin_code, this);

        init();
    }

    protected void init() {

        mPinCodeRoundList = new ArrayList<>(DEFAULT_PIN_CODE_LEN);
        mPinCodeRoundList.add(0, (ImageView) findViewById(R.id.pin_image_0));
        mPinCodeRoundList.add(1, (ImageView) findViewById(R.id.pin_image_1));
        mPinCodeRoundList.add(2, (ImageView) findViewById(R.id.pin_image_2));
        mPinCodeRoundList.add(3, (ImageView) findViewById(R.id.pin_image_3));

        mButtonList = new ArrayList<>(DEFAULT_BUTTON_NUMBER);

        mButtonList.add(0, (NumberKeyboardSingleButton) findViewById(R.id.number_keyboard_0));
        mButtonList.add(1, (NumberKeyboardSingleButton) findViewById(R.id.number_keyboard_1));
        mButtonList.add(2, (NumberKeyboardSingleButton) findViewById(R.id.number_keyboard_2));
        mButtonList.add(3, (NumberKeyboardSingleButton) findViewById(R.id.number_keyboard_3));
        mButtonList.add(4, (NumberKeyboardSingleButton) findViewById(R.id.number_keyboard_4));
        mButtonList.add(5, (NumberKeyboardSingleButton) findViewById(R.id.number_keyboard_5));
        mButtonList.add(6, (NumberKeyboardSingleButton) findViewById(R.id.number_keyboard_6));
        mButtonList.add(7, (NumberKeyboardSingleButton) findViewById(R.id.number_keyboard_7));
        mButtonList.add(8, (NumberKeyboardSingleButton) findViewById(R.id.number_keyboard_8));
        mButtonList.add(9, (NumberKeyboardSingleButton) findViewById(R.id.number_keyboard_9));
    }

    int mLenCount = 0;

    @Override
    public void onNumberClick(String str) {
        if (TextUtils.isEmpty(str)) return;

        char[] chars = str.toCharArray();
        int len = chars.length;

        if (len <= 0) return;

        int index;
        for (int i = 0; i < len; i++) {
            index = chars[i] - '0';
            onNumberClick(index);
        }
    }

    @Override
    public void onNumberClick(int index) {
        if (index < 0 || index >= DEFAULT_BUTTON_NUMBER) return;

        mButtonList.get(index).callOnClick();
        mLenCount++;
        refreshPinCodeRound(mLenCount);
    }

    @Override
    public void onPrepare() {
        for (NumberKeyboardSingleButton b : mButtonList)
            b.onPrepare();
    }

    @Override
    public void onScale(float scale) {
        for (NumberKeyboardSingleButton b : mButtonList)
            b.onScale(scale);
    }

    @Override
    public void onSet() {

    }

    @Override
    public void onReset() {
        for (NumberKeyboardSingleButton b : mButtonList)
            b.onReset();
        mLenCount = 0;
        refreshPinCodeRound(mLenCount);
    }

    private void refreshPinCodeRound(int len) {
        for (int i = 0; i < DEFAULT_PIN_CODE_LEN; i++)
            mPinCodeRoundList.get(i).setImageResource(i < len ?
                    R.drawable.unlockscreen_icon_enter
                    : R.drawable.unlockscreen_icon_noenter);
    }
}
