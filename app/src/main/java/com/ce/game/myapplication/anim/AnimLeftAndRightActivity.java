package com.ce.game.myapplication.anim;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

import com.ce.game.myapplication.R;

import junit.framework.Assert;

public class AnimLeftAndRightActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anim_left_and_right);

        leftRightAnim( findViewById(R.id.view_to_anim));
    }

    private TranslateAnimation mLeftRightAnimation;

    private void leftRightAnim(View view) {
        Assert.assertNotNull(view);

        view.startAnimation(initLeftToRightAnimation());
    }

    private TranslateAnimation initLeftToRightAnimation() {
        if (mLeftRightAnimation != null) return mLeftRightAnimation;
        mLeftRightAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, -0.3f,
                Animation.RELATIVE_TO_SELF, 0.3f, Animation.RELATIVE_TO_SELF,
                0, Animation.RELATIVE_TO_SELF, 0);
        mLeftRightAnimation.setRepeatCount(1);
        mLeftRightAnimation.setRepeatMode(Animation.REVERSE);
        mLeftRightAnimation.setDuration(500);
        return mLeftRightAnimation;
    }
}
