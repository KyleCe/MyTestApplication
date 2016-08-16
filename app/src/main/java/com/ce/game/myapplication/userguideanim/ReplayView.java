package com.ce.game.myapplication.userguideanim;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.ce.game.myapplication.R;
import com.ce.game.myapplication.util.DU;
import com.ce.game.myapplication.util.ViewU;

/**
 * Created by KyleCe on 2016/7/15.
 *
 * @author: KyleCe
 */
public class ReplayView extends FrameLayout {

    private ImageView mReplay;

    private View mBaseView;

    private GuideViewInterface.KeyEventCallback mKeyEventCallback;

    private Paint paint;

    public ReplayView(Context context) {
        this(context, null);
    }

    public ReplayView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ReplayView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mBaseView = inflate(context, R.layout.permission_lead_replay_view, this);

        mReplay = (ImageView) mBaseView.findViewById(R.id.replay);

        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(getResources().getColor(R.color.permission_setting_guide_replay_tip_bg));

        ViewU.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ReplayView.super.onTouchEvent(event);
                return true;
            }
        }, mReplay, findViewById(R.id.replay_text));

        setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
    }

    public void attachKeyEventCallback(GuideViewInterface.KeyEventCallback callback) {
        mKeyEventCallback = callback;
    }

    public static abstract class UpdatePosition {
        void transition(int x, int y) {
            onPrepare();

            onUpdate(x, y);

            onUpdateEnd(x, y);
        }

        protected abstract void onPrepare();

        protected abstract void onUpdate(int x, int y);

        protected abstract void onUpdateEnd(int x, int y);
    }

    private UpdatePosition mUpdatePositionCallback;

    public void assignUpdatePosition(UpdatePosition callback) {
        mUpdatePositionCallback = callback;
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
//        paint.setStyle(Paint.Style.FILL);
//
//        float radius = Math.min(mBaseView.getHeight(), mBaseView.getWidth()) / 2;
//
//        canvas.drawCircle(radius, radius, radius, paint);
//
//        Path path = new Path();
//        path.moveTo(radius, 0);
//        path.lineTo(mBaseView.getWidth(), 0);
//        path.lineTo(mBaseView.getWidth(), mBaseView.getHeight());
//        path.lineTo(radius, mBaseView.getHeight());
//
//        canvas.drawPath(path, paint);

        super.dispatchDraw(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (clickOutside(event)) backPressed();

        return super.onTouchEvent(event);
    }

    private boolean clickOutside(MotionEvent event) {
        return !(getLeft() < event.getX() && event.getX() < getRight() && getTop() < event.getY() && event.getY() < getBottom());
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) backPressed();

        DU.sd("replay event action", event.getAction());

        return super.dispatchKeyEvent(event);
    }

    public void backPressed() {
        if (mKeyEventCallback != null) mKeyEventCallback.onBackPressed();
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        strategyAnim(event, this);

        return false;
    }

    private float dX = 0f;
    private float dY = 0f;
    private float originX = 0f;

    private void strategyAnim(MotionEvent event, View view) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                dX = view.getX() - event.getRawX();
                dY = view.getY() - event.getRawY();
                originX = view.getX();

                mUpdatePositionCallback.onPrepare();
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

                mUpdatePositionCallback.onUpdate((int) event.getX(), (int) event.getY());
                break;
            case MotionEvent.ACTION_UP:
//                view.animate()
//                        .x(originX)
//                        .setDuration(200)
//                        .start();

                DU.sd("xy"
                        , "original x = " + originX
                        , "x =" + event.getX()
                        , "raw x =" + event.getRawX()
                        , "y =" + event.getY()
                        , "raw y =" + event.getRawY());

                mUpdatePositionCallback.onUpdateEnd((int) (originX)
                        , (int) (event.getRawY()));
                break;
        }
    }
}
