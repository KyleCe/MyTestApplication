package com.ce.game.myapplication.showcase;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.view.View;
import android.widget.Button;

import com.ce.game.myapplication.R;
import com.ce.game.myapplication.userguideanim.FloatViewModelTip;
import com.ce.game.myapplication.userguideanim.GuideViewInterface;
import com.ce.game.myapplication.userguideanim.PermissionSettingGuideView;
import com.ce.game.myapplication.userguideanim.ReplayView;
import com.ce.game.myapplication.util.DU;

public class LeadActivity extends Activity {


    private View mView;

    public Button mFirst;
    public Button mSecond;
    public Button mThird;
    public Button mForth;

    private LeaderView mLeaderView;
    public ReplayView mRightTip;

    private PermissionSettingGuideView mGuideView;

    private FloatViewModelTip mRightTipModel;

    private final long LONGEST_REPLAY_SHOW_UP_TIME = 5000;

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

        mGuideView = new PermissionSettingGuideView(LeadActivity.this);

        mRightTipModel = new FloatViewModelTip(this);

        IBinder mToken = null;
        Context context = this;
        if (context != null && (context instanceof Activity))
            mToken = ((Activity) context).getWindow().getDecorView().getWindowToken();

        mRightTipModel.setToken(mToken);

        mRightTip = new ReplayView(this);
        mRightTip.veryFirstSet(this);

        mRightTip.attachKeyEventCallback(new GuideViewInterface.KeyEventCallback() {
            @Override
            public void onBackPressed() {
                clearTipReplayView();
            }
        });

        mRightTip.attachReplayCallback(new GuideViewInterface.ReplayCallback() {
            @Override
            public void onReplay() {
                if (mRightTipModel == null) return;

                mGuideView.onStartSeries();

                mRightTipModel.setView(mGuideView, 0, FloatViewModelTip.WidthHeight.WHOLE_VIEW);

                mHandler.removeCallbacks(mRemoveReplayViewTask);
            }
        });
    }

    private void clearTipReplayView() {
        mRightTipModel.clearView();
    }


    private void startGuide(int type) {
        if (type % 2 != 0) mGuideView.onStartSeries();
        else mGuideView.onStartOnlySecond();

        mRightTipModel.setView(mGuideView, 0, FloatViewModelTip.WidthHeight.WHOLE_VIEW);


        mGuideView.attachKeyEventCallback(new GuideViewInterface.KeyEventCallback() {
            @Override
            public void onBackPressed() {
                if (mGuideView.isShown()) mGuideView.onMinimumToRight();
            }
        });

        if(mRemoveReplayViewTask == null) mRemoveReplayViewTask = new RemoveReplayViewTask();

        mGuideView.attachMinimumEndAction(new GuideViewInterface.MinimumToRightEndCallback() {
            @Override
            public void onAnimationEnd() {
                mRightTipModel.setView(mRightTip, 0, FloatViewModelTip.WidthHeight.TIP_VIEW);
                mHandler.postDelayed(mRemoveReplayViewTask, LONGEST_REPLAY_SHOW_UP_TIME);
            }
        });
    }

    Handler mHandler = new Handler(Looper.getMainLooper()){};
    RemoveReplayViewTask mRemoveReplayViewTask;

    class RemoveReplayViewTask implements Runnable {
        @Override
        public void run() {
            clearTipReplayView();
        }
    }

    public void click1(View v) {
        mLeaderView.assignTargetView(mSecond);
        DU.sd(1);
        startGuide(1);
    }

    public void click2(View v) {
        mLeaderView.assignTargetView(mThird);
        DU.sd(2);
        startGuide(2);
    }

    public void click3(View v) {
        mLeaderView.assignTargetView(mForth);
        DU.sd(3);
        startGuide(3);
    }

    public void click4(View v) {
        mLeaderView.assignTargetView(mFirst);
        DU.sd(4);
        startGuide(4);
    }
}
