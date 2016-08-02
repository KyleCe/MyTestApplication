package com.ce.game.myapplication.anim;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import com.ce.game.myapplication.R;
import com.ce.game.myapplication.showcase.RippleBackground;

/**
 * Created by KyleCe on 2016/8/1.
 *
 * @author: KyleCe
 */
public class RippleBackgroundWithCenteredHand extends FrameLayout {
    private RippleBackground mRippleBackground;

    public RippleBackgroundWithCenteredHand(Context context) {
        this(context, null);
    }

    public RippleBackgroundWithCenteredHand(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RippleBackgroundWithCenteredHand(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        inflate(context, R.layout.ripple_background_with_center_hand, this);

        mVerticalHand = findViewById(R.id.vertical_hand);

        mRippleBackground = (RippleBackground) findViewById(R.id.vertical_hand_ripple_bg);

        mCenterProcessed = false;
    }


    private void centerHandAnimateStrategy() {
        mVerticalHand.animate().translationX(VERTICAL_HAND_CENTER_X_ALIAS_FACTOR * mVerticalHand.getWidth())
                .setDuration(0).start();
        mVerticalHand.animate().translationY(VERTICAL_HAND_CENTER_Y_ALIAS_FACTOR * mVerticalHand.getHeight())
                .setDuration(0).start();

        mCenterProcessed = true;

        mRippleBackground.startRippleAnimation();
    }

    private View mVerticalHand;


    private final float VERTICAL_HAND_CENTER_X_ALIAS_FACTOR = (float) 7.0 / 30;
    private final float VERTICAL_HAND_CENTER_Y_ALIAS_FACTOR = (float) 9.0 / 20;
    private boolean mCenterProcessed = false;

//    private void centerVerticalHandInRippleContainer() {
//        new ObjectAnimator().ofFloat(mVerticalHand, View.TRANSLATION_X
//                , 0, VERTICAL_HAND_CENTER_X_ALIAS_FACTOR * mVerticalHand.getWidth())
//                .setDuration(0).start();
//        new ObjectAnimator().ofFloat(mVerticalHand, View.TRANSLATION_Y
//                , 0, VERTICAL_HAND_CENTER_Y_ALIAS_FACTOR * mVerticalHand.getHeight())
//                .setDuration(0).start();
//
//        DU.sd("center executed");
//
//    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);

//        mVerticalHand.postDelayed(new Runnable() {
//            @Override
//            public void run() {
        if (!mCenterProcessed)
                centerHandAnimateStrategy();
//            }
//        }, 100);
    }
}
