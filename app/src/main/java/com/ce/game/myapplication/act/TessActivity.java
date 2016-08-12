package com.ce.game.myapplication.act;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;

import com.ce.game.myapplication.R;
import com.ce.game.myapplication.util.DU;

public class TessActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tess);

//        mMoveView = findViewById(R.id.move_view);
    }

//    private View mMoveView;

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        DU.sd("activity event action",ev.getAction());

//        mMoveView.dispatchTouchEvent(ev);

        return super.dispatchTouchEvent(ev);
    }
}
