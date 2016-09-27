package com.ce.game.myapplication.util;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

import java.util.List;

import static android.content.Context.ACTIVITY_SERVICE;

/**
 * Created by KyleCe on 2016/8/16.
 *
 * @author: KyleCe
 */

public class CurrentActivityMatchIntentSupervisor extends FutureRunnable {
    protected Intent mIntent;

    protected Runnable mStartActivityOkTask;
    protected Runnable mCancelTask;

    protected volatile boolean mStartTaskExecuted = false;

    protected int mRepeatCount = 0;

    protected Context mContext;

    protected final int MAX_REPEAT_COUNT = 6;

    public CurrentActivityMatchIntentSupervisor(Context context, Intent intent, Runnable startTask, Runnable endTask) {
        mContext = context.getApplicationContext();
        mIntent = intent;
        mStartActivityOkTask = startTask;
        mCancelTask = endTask;
    }

    @SuppressWarnings("deprecation")
    @Override
    public void run() {
        try {
            mRepeatCount++;

            ActivityManager am = (ActivityManager) mContext.getSystemService(ACTIVITY_SERVICE);
            List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);
            String runningActivityClassName = taskInfo.get(0).topActivity.getClassName();

            if (!isRunningActivityResolveActivity(mContext, mIntent, runningActivityClassName)) {
                if (mStartTaskExecuted || mRepeatCount > MAX_REPEAT_COUNT) {
                    getFuture().cancel(false);
                    if (mCancelTask != null) mCancelTask.run();
                }
            } else {
                if (!mStartTaskExecuted) {
                    if (mStartActivityOkTask != null) mStartActivityOkTask.run();
                    mStartTaskExecuted = true;
                }
            }
        } catch (Exception e) {
            getFuture().cancel(false);
            if (mCancelTask != null) mCancelTask.run();
        }
    }

    private boolean isRunningActivityResolveActivity(Context context, Intent intent, String activityClassName) {
        ComponentName componentName = intent.resolveActivity(context.getPackageManager());
        return activityClassName.toLowerCase().contains(componentName.getClassName().toLowerCase());
    }
}
