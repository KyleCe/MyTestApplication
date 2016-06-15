package com.ce.game.myapplication.scrollingblurtext.userguideanim;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.ce.game.myapplication.R;

public class AssistGuideView extends FrameLayout {
    private TextView tvGuideUser;

    public interface GuideCallback {
        void onTouch();
    }
    public AssistGuideView(final Context context) {
        this(context, null);
    }

    public AssistGuideView(final Context context, final AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AssistGuideView(final Context context, final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflate(context, R.layout.assist_guide_view, this);

        tvGuideUser = (TextView) findViewById(R.id.tv_guide_user);
    }

    /**
     * update guide text
     *
     * @param guideText resource to set
     */
    public void updateGuideText(String guideText) {
        if (TextUtils.isEmpty(guideText)) return;

        if (tvGuideUser == null) return;

        tvGuideUser.setText(guideText);
    }

    /**
     * attach call back
     *
     * @param callback call back to attach
     * @throws NullPointerException
     */
    public void attachCallBack(final GuideCallback callback) {
        if (callback == null)
            throw new NullPointerException("call back cannot be null");

//        this.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                callback.onTouch();
//            }
//        });

        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                callback.onTouch();
                return false;
            }
        });
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {

        return super.dispatchKeyEvent(event);
    }
}
