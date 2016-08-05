package com.ce.game.myapplication.scrollingblurtext;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ScrollView;

import com.ce.game.myapplication.R;

public class ImageViewScrollActivity extends Activity {

    ScrollView mScrollView;


    private final int HORIZONTAL_HAND_ANIM_DURATION = 1000;
    private final int DEFAULT_FIRST_SCENE_DURATION = 1000;
    private int mFirstSceneDuration = DEFAULT_FIRST_SCENE_DURATION;
    private final int FIRST_SCENE_RESERVE_DURATION = 2000;
    private static final float VERTICAL_HAND_DURATION_MOVE_UP_FACTOR = 0.4f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_view_scroll);

        mScrollView = (ScrollView) findViewById(R.id.scroll_view);

        start(null);

        end(null);
    }

    public void start(View v) {
//        int targetScrollY = mScrollView.getBottom()+100;
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
//            ValueAnimator realSmoothScrollAnimation =
//                    ValueAnimator.ofInt(mScrollView.getScrollY(), targetScrollY);
//            realSmoothScrollAnimation.setDuration(500);
//            realSmoothScrollAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//                @Override
//                public void onAnimationUpdate(ValueAnimator animation) {
//                    int scrollTo = (Integer) animation.getAnimatedValue();
//                    mScrollView.scrollTo(0, scrollTo);
//                }
//            });
//
//            realSmoothScrollAnimation.start();
//        } else {
//            mScrollView.smoothScrollTo(0, targetScrollY);
//        }


        ObjectAnimator.ofInt(mScrollView, "scrollY", mScrollView.getBottom())
                .setDuration((long) (mFirstSceneDuration * VERTICAL_HAND_DURATION_MOVE_UP_FACTOR)).start();
    }

    public void end(View v) {
        if (mScrollView != null) {
//            ObjectAnimator.ofInt(mScrollView, "scrollY", 0)
//                    .setDuration(0).start();
            mScrollView.fullScroll(ScrollView.FOCUS_UP);
        }
    }
}
