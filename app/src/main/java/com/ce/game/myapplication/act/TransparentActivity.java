package com.ce.game.myapplication.act;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;

import com.ce.game.myapplication.R;

public class TransparentActivity extends Activity {

    TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transparent);
        mTextView = (TextView) findViewById(R.id.title);

        new Handler().post(new Runnable() {
            @Override
            public void run() {
                long start= System.nanoTime();
                for (long t = start; t < start + 3000; t = System.nanoTime()) {
                    mTextView.setText(String.valueOf(System.currentTimeMillis()));
                }



            }
        });

        unlockAnim();

//        viewY -0.3f * viewY
//        float viewY = mTextView.getY();
//        ObjectAnimator rollingAnim = ObjectAnimator.ofFloat(mTextView, View.TRANSLATION_Y
//                ,-viewY, viewY,1);
////        rollingAnim.setRepeatCount(Animation.INFINITE);
////        rollingAnim.setRepeatMode(Animation.REVERSE);
////        rollingAnim.setEvaluator(new FloatEvaluator());
//        rollingAnim.setDuration(5000);
//        rollingAnim.start();
    }


    private void unlockAnim(){
        TranslateAnimation rollingAnim = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0,
                Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF,
                -0.3f, Animation.RELATIVE_TO_SELF, 0);
        rollingAnim.setRepeatCount(Animation.INFINITE);
        rollingAnim.setRepeatMode(Animation.REVERSE);
        rollingAnim.setDuration(1500);
        mTextView.startAnimation(rollingAnim);
    }
}
