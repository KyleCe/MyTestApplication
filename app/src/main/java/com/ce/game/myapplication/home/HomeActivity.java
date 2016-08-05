
package com.ce.game.myapplication.home;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.view.View;

import com.ce.game.myapplication.R;
import com.ce.game.myapplication.util.DU;

import java.util.concurrent.Future;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class HomeActivity extends Activity {

    private Context mContext;

    private Future<?> mFuture;

    private ScheduledThreadPoolExecutor mExecutor;
    private DefaultHomeGuardian mHomeGuardian;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fake_home);

        mContext = HomeActivity.this;

        if (mExecutor == null)
            mExecutor = new ScheduledThreadPoolExecutor(2);

        if (mHomeGuardian == null)
            mHomeGuardian = new DefaultHomeGuardian();

        mFuture = mExecutor.scheduleAtFixedRate(mHomeGuardian, 200, 500, TimeUnit.MILLISECONDS);

        View parent = findViewById(R.id.home_parent);
        animIt(parent);
    }

    private void animIt(final View parent) {
        TransitionDrawable trans = (TransitionDrawable) parent.getBackground();
        trans.startTransition(2000);

//        int colorFrom = getResources().getColor(R.color.retrieve_view_mismatch_dialog_title);
//        int colorTo = getResources().getColor(R.color.permission_setting_guide_replay_tip_bg);
//        ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, colorTo);
//        colorAnimation.setDuration(250); // milliseconds
//        colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//
//            @Override
//            public void onAnimationUpdate(ValueAnimator animator) {
//                parent.setBackgroundColor((int) animator.getAnimatedValue());
//            }
//
//        });
//        colorAnimation.setRepeatCount(ValueAnimator.INFINITE);
//        colorAnimation.setRepeatMode(ValueAnimator.RESTART);
//        colorAnimation.start();
    }

    public void click1(View v) {
        choose(1);
    }

    public void click2(View v) {
        choose(2);
    }

    public void click3(View v) {
        choose(3);
    }

    public void click4(View v) {
        choose(4);
    }

    public void click5(View v) {
        choose(5);
    }

    public void click6(View v) {
        choose(6);
    }

    public void click7(View v) {
        choose(7);
    }

    public void choose(int i) {
        SettingHelper.fakeLauncherInstalledAndOpenChooser(mContext, i);
    }

    final private class DefaultHomeGuardian implements Runnable {
        @Override
        public void run() {
            DU.sd("check current or default",
                    "current : " + SettingHelper.isMyLauncherCurrent(mContext)
                    , "default : " + SettingHelper.isMyLauncherDefault(mContext));
            if (checkPermissionSettingAllComplete()) {
                mFuture.cancel(false);
//                finish();

                DU.tsd(mContext, "getString(R.string.ps_switch_on_all_complete)");

                DU.sd("permission setting guardian", "future canceled, exit ok");
            }
        }
    }

    private boolean checkPermissionSettingAllComplete() {
        return SettingHelper.isMyLauncherCurrent(mContext);
    }
}
