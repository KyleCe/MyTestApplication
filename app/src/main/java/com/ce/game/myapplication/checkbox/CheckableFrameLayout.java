package com.ce.game.myapplication.checkbox;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Checkable;
import android.widget.FrameLayout;

import com.ce.game.myapplication.R;
import com.ce.game.myapplication.util.BitmapU;
import com.ce.game.myapplication.util.DU;


/**
 * Created by Administrator on 2015/11/11.
 */
public class CheckableFrameLayout extends FrameLayout implements Checkable {

    private Bitmap bipSelected = BitmapFactory.decodeResource(getResources(), R.drawable.select_game_unfollow);
    private Bitmap bipUnselected = BitmapFactory.decodeResource(getResources(), R.drawable.select_game_unselected);
    private Bitmap bip = bipUnselected;

    private boolean mChecked = false;

    private FrameLayout mLayout;

    public CheckableFrameLayout(Context context) {
        this(context, null);
    }

    public CheckableFrameLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CheckableFrameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        View inflater = inflate(context, R.layout.permission_layout, this);
        mLayout = (FrameLayout) inflater.findViewById(R.id.parent);

        invalidate();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        DU.sd("view group", "on layout");


        changeLayoutStyle();
    }

    private void changeLayoutStyle() {
        if (isClickable())
            mLayout.setBackgroundResource(R.color.colorAccent);
        else
            mLayout.setBackgroundResource(R.color.dg_unselect_default);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        DU.sd("view group", "on draw");

        super.onDraw(canvas);

        int height = getHeight();
        int width = getWidth();

        int bipXY = (int) (1.0 / 3 * height);

        bip = BitmapU.processBitmap(bip, bipXY, bipXY);

        float drawX = width - (float) bipXY * 2;

        float drawY = (float) (1.0 / 3 * height);

        canvas.drawBitmap(bip, drawX, drawY, null);

        changeLayoutStyle();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_UP)
            setChecked(!isChecked());

        return super.dispatchTouchEvent(ev);
    }

    @Override
    public void setChecked(boolean checked) {
        if (mChecked != checked) {
            mChecked = checked;
        }
        if (isClickable())
            mLayout.setBackgroundResource(checked ? R.color.black : R.color.colorPrimary);
    }

    @Override
    public boolean isChecked() {
        return mChecked;
    }

    @Override
    public void toggle() {
        setChecked(!mChecked);
    }
}
