package com.ce.game.myapplication.act;

import android.app.Activity;
import android.content.Context;
import android.os.BatteryManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.ce.game.myapplication.R;
import com.ce.game.myapplication.userguideanim.FloatViewModelTip;
import com.ce.game.myapplication.util.FloatViewContainer;
import com.ce.game.myapplication.util.Utilities;
import com.ce.game.myapplication.view.MessageProcessView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FloatBackPressActivity extends Activity {

    FloatViewContainer mFloatViewModel;
    MessageProcessView mMessageProcessView;
    FloatViewModelTip mViewModelTip;

    @BindView(R.id.edit_query)
    EditText mEditText;

    @BindView(R.id.activity_float_backpress)
    View parent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_float_backpress);
        ButterKnife.bind(this);

        mFloatViewModel = new FloatViewContainer(getApplicationContext());
        mViewModelTip = new FloatViewModelTip(getApplicationContext());
        mMessageProcessView = new MessageProcessView(getApplicationContext());

        mEditText.setText(String.valueOf(bat(getApplicationContext())));


        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        parent.setOnClickListener(v -> {
            EditText et = new EditText(getApplicationContext());
            et.setHint("this is hint");
            et.setFocusableInTouchMode(true);
            et.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 300));
            et.setAlpha(.5f);

            InputMethodManager imm = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(et, InputMethodManager.SHOW_IMPLICIT);

            mViewModelTip.setView(et, 0, FloatViewModelTip.WidthHeight.TIP_VIEW);

            et.requestFocus();
            et.dispatchTouchEvent(MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), MotionEvent.ACTION_DOWN, 0, 0, 0));
            et.dispatchTouchEvent(MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), MotionEvent.ACTION_UP, 0, 0, 0));
        });

    }

    @SuppressWarnings("all")
    float bat(Context context){
        if(Utilities.ATLEAST_LOLLIPOP){
            BatteryManager bm = (BatteryManager)context.getSystemService(BATTERY_SERVICE);
            return bm.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY);
        }
        return 0;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mViewModelTip.clearView();
    }
}
