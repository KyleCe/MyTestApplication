package com.ce.game.myapplication.view;

import android.content.Context;
import android.support.annotation.ColorRes;
import android.support.annotation.StringRes;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.TextView;

import com.ce.game.myapplication.R;

/**
 * Created by KyleCe on 2016/5/23.
 *
 * @author: KyleCe
 */
public class PhoneViewWithText extends PhoneView implements TextCustomInterface {

    public TextView mTextView;

    public static final int DEFAULT_MINIMUM_TEXT_SIZE_SP = 15;

    public PhoneViewWithText(final Context context) {
        this(context, null);
    }

    public PhoneViewWithText(final Context context, final AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PhoneViewWithText(final Context context, final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mContainer.inflate(context, R.layout.first_anim_phone_view_text_content, mContainer);

        mTextView = (TextView) mContainer.findViewById(R.id.phone_frame_text);
    }


    @Override
    public void setContent(String str) {
        mTextView.setText(str);
    }

    @Override
    public void setContent(@StringRes int strId) {
        mTextView.setText(mContext.getString(strId));
    }

    @Override
    public void setBgColor(@ColorRes int colorId) {
        mTextView.setBackgroundColor(mContext.getResources().getColor(colorId));
    }

    @Override
    public void setContentSizeUnitSp(float size) {
        mTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, size);
    }
}
