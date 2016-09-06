package com.ce.game.myapplication.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
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

public class NumberKeyboardSingleButton extends FrameLayout implements NumberButtonBgInterface {

    protected Context mContext;

    protected FrameLayout mParent;

    TextView mTextView;

    String mText;

    float TEXT_SCALE_RATIO = .2f;


    public NumberKeyboardSingleButton(Context context) {
        this(context, null);
    }

    public NumberKeyboardSingleButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

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

    private static final int RESIZE = 383;

    Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case RESIZE:
                    float scale = 1;
                    if (msg.obj instanceof Float)
                        scale = (float) msg.obj;
                    float width = mParent.getMeasuredHeight() * scale;

                    mTextView.setTextSize((mParent.getMeasuredHeight() * TEXT_SCALE_RATIO) * scale);

                    mParent.setLayoutParams(new FrameLayout.LayoutParams((int) width, (int) width));
                    mParent.invalidate();
                    break;
                default:
                    break;
            }
        }
    };

    protected void init() {
        mParent = (FrameLayout) findViewById(R.id.parent);

        mTextView = (TextView) findViewById(R.id.keyboard_button_textview);

        ViewU.setClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onSet();
            }
        }, mParent, mTextView, this);

        setButtonText(mText);
    }

    public void setButtonText(String text) {
        if (TextUtils.isEmpty(text)) return;

        if (text.length() > 1) text = text.substring(0, 1);

        mTextView.setText(text);
    }

    @Override
    public void onPrepare() {
        mHandler.sendEmptyMessage(RESIZE);
    }

    @Override
    public void onScale(float scale) {
        Message msg = new Message();
        msg.what = RESIZE;
        msg.obj = scale;
        mHandler.sendMessage(msg);
    }

    @Override
    public void onSet() {
        mTextView.setBackgroundResource(R.drawable.first_anim_num_btn_bg_pressed);
    }

    @Override
    public void onReset() {
        mTextView.setBackgroundResource(R.drawable.first_anim_num_btn_bg);
    }
}
