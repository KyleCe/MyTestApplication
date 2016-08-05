package com.ce.game.myapplication.anim;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.ce.game.myapplication.R;

public class UpAndLeftActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_up_and_left);
        showFirstLaunchGuide();
    }


    public void showFirstLaunchGuide() {
        findViewById(R.id.iv_up_slide_arrow).postDelayed(new Runnable() {
            @Override
            public void run() {
                assembleLeftRollingAnim(findViewById(R.id.iv_up_slide_arrow), findViewById(R.id.iv_left_slide_arrow));
            }
        }, 200);
    }

    private void assembleLeftRollingAnim(final View upArrow, final View leftArrow) {
        float toRollDistance = .1f * leftArrow.getWidth();
        ObjectAnimator up = new ObjectAnimator().ofFloat(upArrow, View.TRANSLATION_Y, toRollDistance)
                .setDuration(1000);
        up.setRepeatCount(ValueAnimator.INFINITE);
        up.setRepeatMode(ValueAnimator.REVERSE);
        up.start();
        ObjectAnimator left = new ObjectAnimator().ofFloat(leftArrow, View.TRANSLATION_X, toRollDistance)
                .setDuration(1000);
        left.setRepeatCount(ValueAnimator.INFINITE);
        left.setRepeatMode(ValueAnimator.REVERSE);
        left.start();
    }
}
