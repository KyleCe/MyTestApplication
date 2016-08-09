package com.ce.game.myapplication.userguideanim;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.IBinder;
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
public class ReplayView extends LinearLayout
//        implements View.OnTouchListener
{

    ImageView mReplay;

    GuideViewInterface.KeyEventCallback mKeyEventCallback;
    GuideViewInterface.ReplayCallback mReplayCallback;

    private IBinder mToken;

    public ReplayView(Context context) {
        this(context, null);
    }

    public ReplayView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ReplayView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

//        this.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
    }

    public void veryFirstSet(Context context) {
        View view = inflate(context, R.layout.permission_lead_replay_view, this);

//        view.setBackgroundResource(R.color.permission_setting_guide_replay_tip_bg);

        mReplay = (ImageView) view.findViewById(R.id.replay);

        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(getResources().getColor(R.color.permission_setting_guide_replay_tip_bg));

        this.setBackgroundColor(getResources().getColor(R.color.colorAccent));

        if (context != null && (context instanceof Activity))
            this.mToken = ((Activity) context).getWindow().getDecorView().getWindowToken();
    }

    public void attachReplayCallback(GuideViewInterface.ReplayCallback replayCallback) {
        mReplayCallback = replayCallback;
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

//    @Override
//    public boolean dispatchTouchEvent(MotionEvent ev) {
//        return false;
////        return super.dispatchTouchEvent(ev);
//    }
////
//    @Override
//    public boolean onInterceptTouchEvent(MotionEvent ev) {
//        onTouchEvent(ev);
//        return false;
////        return super.onInterceptTouchEvent(ev);
//    }
////
//
    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        if (clickOutside(event)) backPressed();
        if (!clickOutside(event) && mReplayCallback != null) mReplayCallback.onReplay();

//        return super.onTouchEvent(event);
        return false;
//        return true;
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

//    @Override
//    public boolean onTouch(View v, MotionEvent event) {
////
//////        if (this.mTouchDisable) {
//////            return false;
//////        }
//////        int x = (int) motionEvent.getX();
//////        int y = (int) motionEvent.getY();
//////        if (motionEvent.getAction() == 0 && (x < 0 || x >= this.mRootView.getWidth() || y < 0 || y >= this.mRootView.getHeight())) {
//////            close(CLOSE_OUTTOUCH);
//////            return true;
//////        } else if (motionEvent.getAction() != 4) {
//////            return false;
//////        } else {
//////            close(CLOSE_OUTTOUCH);
//////            return true;
//////        }
////
////
//        return false;
//    }
}
