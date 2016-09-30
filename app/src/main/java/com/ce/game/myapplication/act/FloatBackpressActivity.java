package com.ce.game.myapplication.act;

import android.app.Activity;
import android.os.Bundle;

import com.ce.game.myapplication.R;
import com.ce.game.myapplication.userguideanim.FloatViewModelTip;
import com.ce.game.myapplication.util.FloatViewContainer;
import com.ce.game.myapplication.view.MessageProcessView;

public class FloatBackPressActivity extends Activity {

    FloatViewContainer mFloatViewModel;
    MessageProcessView mMessageProcessView;
    FloatViewModelTip mViewModelTip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_float_backpress);

        mFloatViewModel = new FloatViewContainer(getApplicationContext());
        mViewModelTip = new FloatViewModelTip(getApplicationContext());
        mMessageProcessView = new MessageProcessView(getApplicationContext());

        mViewModelTip.setView(mMessageProcessView,0, FloatViewModelTip.WidthHeight.WHOLE_VIEW);
    }
}
