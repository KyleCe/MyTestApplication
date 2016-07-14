package com.ce.game.myapplication.showcase;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.ce.game.myapplication.R;
import com.ce.game.myapplication.util.DU;

public class LeadActivity extends Activity {

    private LeaderView mLeaderView;

    private View mView;

    public Button mFirst;
    public Button mSecond;
    public Button mThird;
    public Button mForth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lead);

        mLeaderView = (LeaderView) findViewById(R.id.leader_view);

        mFirst = (Button) findViewById(R.id.first);
        mSecond = (Button) findViewById(R.id.second);
        mThird = (Button) findViewById(R.id.third);
        mForth = (Button) findViewById(R.id.forth);

        mLeaderView.post(new Runnable() {
            @Override
            public void run() {
                mLeaderView.assignTargetView(mFirst);
            }
        });
    }


    public void click1(View v) {
        mLeaderView.assignTargetView(mSecond);
        DU.sd(1);
        startRipple();
    }

    private void startRipple() {
    }

    public void click2(View v) {
        mLeaderView.assignTargetView(mThird);
        DU.sd(2);
        startRipple();
    }

    public void click3(View v) {
        mLeaderView.assignTargetView(mForth);
        DU.sd(3);
        startRipple();
    }

    public void click4(View v) {
        mLeaderView.assignTargetView(mFirst);
        DU.sd(4);
        startRipple();
    }
}
