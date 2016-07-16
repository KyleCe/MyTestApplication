package com.ce.game.myapplication.scrollingblurtext.userguideanim;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.ce.game.myapplication.R;

/**
 * Created by KyleCe on 2016/7/15.
 *
 * @author: KyleCe
 */
public class ReplayView extends LinearLayout {

    ImageView mReplay;

    GuideViewInterface.KeyEventCallback mKeyEventCallback;

    public ReplayView(Context context) {
        this(context, null);
    }

    public ReplayView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ReplayView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        View view = inflate(context, R.layout.permission_lead_replay_view, this);

//        view.setBackgroundResource(R.color.permission_setting_guide_replay_tip_bg);

        mReplay = (ImageView) view.findViewById(R.id.replay);

        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(getResources().getColor(R.color.permission_setting_guide_replay_tip_bg));
    }

    public void attachKeyEventCallback(GuideViewInterface.KeyEventCallback callback) {
        mKeyEventCallback = callback;
    }

    Paint paint;

    @Override
    protected void dispatchDraw(Canvas canvas) {
        paint.setStyle(Paint.Style.FILL);

        float radius = Math.min(getHeight(), getWidth()) / 2;

        canvas.drawCircle(radius, radius, radius, paint);

        Path path = new Path();
        path.moveTo(radius, 0);
        path.lineTo(getWidth(), 0);
        path.lineTo(getWidth(), getHeight());
        path.lineTo(radius, getHeight());

        canvas.drawPath(path, paint);

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

        return super.dispatchKeyEvent(event);
    }

    public void backPressed() {
        if (mKeyEventCallback != null) mKeyEventCallback.onBackPressed();
    }

}
