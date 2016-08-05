
package com.ce.game.myapplication.home;

import android.app.Activity;
import android.content.Context;
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

    }

    public void parentClicked(View v){
        SettingHelper.openDefaultLauncherSetting(this);
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
        SettingHelper.fakeLauncherInstalledAndOpenLauncherChooser(mContext, i);
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
