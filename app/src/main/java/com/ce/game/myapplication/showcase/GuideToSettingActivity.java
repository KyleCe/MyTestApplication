package com.ce.game.myapplication.showcase;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;

import com.ce.game.myapplication.R;
import com.ce.game.myapplication.util.DU;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class GuideToSettingActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide_to_setting);

        mContext = GuideToSettingActivity.this;

        Intent setting = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
        setting.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(setting);

        mScheduleExecutor = Executors.newScheduledThreadPool(5);
        mRunningActivityResolvingIntentCheckTask = new RunningActivityResolvingIntentCheckTask(setting);
        mScheduledFuture = mScheduleExecutor.scheduleAtFixedRate(mRunningActivityResolvingIntentCheckTask, 1000, 1000, TimeUnit.MILLISECONDS);
    }

    ScheduledExecutorService mScheduleExecutor;
    ScheduledFuture<?> mScheduledFuture;
    Context mContext;
    RunningActivityResolvingIntentCheckTask mRunningActivityResolvingIntentCheckTask;

    class RunningActivityResolvingIntentCheckTask implements Runnable {
        Intent mIntent;

        RunningActivityResolvingIntentCheckTask(Intent intent) {
            mIntent = intent;
        }

        @Override
        public void run() {
            ActivityManager am = (ActivityManager) mContext.getSystemService(ACTIVITY_SERVICE);
            List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);
            String runningActivityClassName = taskInfo.get(0).topActivity.getClassName();

            if (!isRunningActivityResolveActivity(mContext, mIntent, runningActivityClassName)) {
                mScheduledFuture.cancel(false);
            } else {

            }
        }
    }

    private boolean isRunningActivityResolveActivity(Context context, Intent intent, String activityClassName) {
        ComponentName componentName = intent.resolveActivity(context.getPackageManager());

        DU.sd("info", "target activity: " + componentName.getClassName(), "class name: " + activityClassName);
        return activityClassName.toLowerCase().contains(componentName.getClassName().toLowerCase());
    }
}
