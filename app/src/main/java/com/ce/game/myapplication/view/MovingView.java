package com.ce.game.myapplication.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.FrameLayout;

/**
 * Created by KyleCe on 2016/5/23.
 *
 * @author: KyleCe
 */
public class MovingView extends FrameLayout {

    public MovingView(final Context context) {
        this(context, null);
    }

    public MovingView(final Context context, final AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MovingView(final Context context, final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);

//        mGestureDetector = new GestureDetector(context,);
    }

    GestureDetector mGestureDetector;



    @Override
    public boolean onTouchEvent(MotionEvent event) {


        return mGestureDetector.onTouchEvent(event);
//        return super.onTouchEvent(event);
    }



}
