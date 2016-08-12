package com.ce.game.myapplication.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import com.ce.game.myapplication.R;

/**
 * Created by KyleCe on 2016/5/23.
 *
 * @author: KyleCe
 */
public class MovingViewWithFixedXPosition extends FrameLayout {

    public MovingViewWithFixedXPosition(final Context context) {
        this(context, null);
    }

    public MovingViewWithFixedXPosition(final Context context, final AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MovingViewWithFixedXPosition(final Context context, final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        inflate(context, R.layout.move_with_hand, this);

        findViewById(R.id.move_view).setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        strategyAnim(event, this);

        return super.dispatchTouchEvent(event);
    }

    float dX = 0f;
    float dY = 0f;
    float originX = 0f;

    private void strategyAnim(MotionEvent event, View view) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                dX = view.getX() - event.getRawX();
                dY = view.getY() - event.getRawY();
                originX = view.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                view.animate()
                        .x(event.getRawX() + dX)
                        .setDuration(0)
                        .start();
                view.animate()
                        .y(event.getRawY() + dY)
                        .setDuration(0)
                        .start();
                break;
            case MotionEvent.ACTION_UP:
                view.animate()
                        .x(originX)
                        .setDuration(200)
                        .start();
                break;
        }
    }

}
