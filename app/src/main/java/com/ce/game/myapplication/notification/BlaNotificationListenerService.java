package com.ce.game.myapplication.notification;

import android.annotation.TargetApi;
import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.IBinder;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;

import com.ce.game.myapplication.util.DU;

import junit.framework.Assert;

@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
public class BlaNotificationListenerService extends NotificationListenerService
        implements ServiceConnection {
    public BlaNotificationListenerService() {
        DU.sd("noti constructor");
    }

//    @Override
//    public IBinder onBind(Intent mIntent) {
//        mIntent.setAction("dummy.action");
//        IBinder mIBinder = super.onBind(mIntent);
//        Log.i("NLS", "onBind");
//        return mIBinder;
//    }

    @Override
    public void onListenerConnected() {
        super.onListenerConnected();
        DU.sd("noti onListenerConnected");
    }

    @Override
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        DU.sd("noti connected");
    }

    @Override
    public void onServiceDisconnected(ComponentName componentName) {
        DU.sd("noti disconnected");
    }

    public void onCreate() {
        DU.sd("noti create");

//        mReceiver = new UserNotificationReceiver(this);
    }

    public void onDestroy() {
        DU.sd("noti destroy");

        super.onDestroy();
    }

    @Override
    @SuppressWarnings("deprecation")
    public void onNotificationPosted(StatusBarNotification sbn) {
        DU.sd("noti posted");

        Assert.assertNotNull(sbn);
        Assert.assertNotNull(sbn.getNotification());

        if (noNeedToCancel(sbn)) return;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) cancelNotification(sbn.getKey());
        else cancelNotificationBelowLollipop(sbn);
    }

    @SuppressWarnings("deprecation")
    private void cancelNotificationBelowLollipop(StatusBarNotification sbn) {
        String packageName = sbn.getPackageName();
        String tag = sbn.getTag();
        int id = sbn.getId();
        Log.i("notification info::", "package :" + packageName + ",tag : " + tag + ",id : " + id);
        cancelNotification(packageName, tag, id);
    }

    protected boolean noNeedToCancel(StatusBarNotification sbn) {
        return getPackageName().equalsIgnoreCase(sbn.getPackageName());
    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        DU.sd("noti removed");
    }
//
//    static final class UserNotificationReceiver extends BroadcastReceiver {
//
//
//        @Override
//        public void onReceive(final Context context, final Intent intent) {
//
//            final String action = intent.getAction();
//        }
//    }

//    static final class CacheNotification {
//        public final long userId;
//        public final String packageName;
//        public final String sbnKey;
//        public final String sbnTag;
//        public final int sbnId;
//        public final Notification notification;
//
//        public CacheNotification(final long userId, final String packageName, final String sbnKey, final String sbnTag, final int sbnId, final Notification notification) {
//            this.userId = userId;
//            this.packageName = packageName;
//            this.sbnKey = sbnKey;
//            this.sbnId = sbnId;
//            this.sbnTag = sbnTag;
//            this.notification = notification;
//        }
//    }
}
