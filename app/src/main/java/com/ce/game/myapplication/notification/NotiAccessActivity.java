package com.ce.game.myapplication.notification;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;

import com.ce.game.myapplication.R;

public class NotiAccessActivity extends Activity {
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noti_access);

        mContext = this;

        openNotificationIfNotYet();
    }

    private void openNotificationIfNotYet() {
        if (Settings.Secure.getString(this.getContentResolver(), "enabled_notification_listeners")
                .contains(getApplicationContext().getPackageName())) {
            // enabled
        } else {
            // not

            startActivityForResult(new Intent(
                    "android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS"), REQUEST_NOTIFICATION_ACCESS_SETTING);
        }
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
