package com.ce.game.myapplication.notification;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;

import com.ce.game.myapplication.R;
import com.ce.game.myapplication.util.DU;

import java.util.List;

public class NotiAccessActivity extends Activity {
    private static final String TAG = NotiAccessActivity.class.getSimpleName();

    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noti_access);

        mContext = this;

        openNotificationIfNotYet();

//        bindNotification();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

//        unbindNotification();
    }
//
//    BlaNotificationListenerService myService;
//    protected ServiceConnection mServerConn = new ServiceConnection() {
//        @Override
//        public void onServiceConnected(ComponentName name, IBinder binder) {
//            Log.d(TAG, " ServiceConnection onServiceConnected");
////            NotificationService.MyLocalBinder myBinder = (NotificationService.MyLocalBinder) binder;
////            myService = myBinder.getService();
//        }
//
//        @Override
//        public void onServiceDisconnected(ComponentName name) {
//            Log.d(TAG, "ServiceConnection onServiceDisconnected");
//        }
//    };

//    public void bindNotification() {
//        Log.i(TAG,"NotificationService bind start");
//        Intent i = new Intent(this,BlaNotificationListenerService.class);
//        bindService(i, mServerConn, Context.BIND_AUTO_CREATE);
//        startService(i);
//
//        startService(new Intent(this,BlaNotificationListenerService.class));
//    }
//
//    public void unbindNotification() {
//        Log.i(TAG,"NotificationService unbind start");
//
//        stopService(new Intent(this, BlaNotificationListenerService.class));
//        unbindService(mServerConn);
//    }


    private void openNotificationIfNotYet() {
        if (Settings.Secure.getString(this.getContentResolver(), "enabled_notification_listeners")
                .contains(getApplicationContext().getPackageName())) {
            // enabled
            if (isNLServiceCrashed())
                openNotiSettingPage(null);
        } else {
            // not

            openNotiSettingPage(null);
        }
    }

    public void openNotiSettingPage(View v) {
//        startActivityForResult(new Intent(
//                "android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS"), REQUEST_NOTIFICATION_ACCESS_SETTING);

        startActivity(new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS"));
    }

    private boolean isNLServiceCrashed() {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> runningServiceInfos = manager.getRunningServices(Integer.MAX_VALUE);

        if (runningServiceInfos != null) {
            for (ActivityManager.RunningServiceInfo service : runningServiceInfos) {

                //NotificationListener.class is the name of my class (the one that has to extend from NotificationListenerService)
                if (BlaNotificationListenerService.class.getName().equals(service.service.getClassName())) {

                    if (service.crashCount > 0) {
                        // in this situation we know that the notification mListener service is not working for the app
                        DU.sd("noti", " crashed");

                        return true;
                    }
                    continue;
                }
            }
        }
        return false;
    }

    private static final int REQUEST_NOTIFICATION_ACCESS_SETTING = 24;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_NOTIFICATION_ACCESS_SETTING)
            checkIfOpen();
    }

    private void checkIfOpen() {
        ContentResolver contentResolver = mContext.getContentResolver();
        String enabledNotificationListeners = Settings.Secure.getString(contentResolver, "enabled_notification_listeners");
        String packageName = mContext.getPackageName();

        if (enabledNotificationListeners != null && enabledNotificationListeners.contains(packageName)) {
            // ok
        } else {
            // not
        }
    }
}
