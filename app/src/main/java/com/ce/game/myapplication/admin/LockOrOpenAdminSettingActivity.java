package com.ce.game.myapplication.admin;

import android.app.Activity;
import android.app.admin.DeviceAdminReceiver;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.ce.game.myapplication.R;
import com.ce.game.myapplication.util.DU;

/**
 * must start the admin from an activity, for the intent cannot add new task flag
 * Created by KyleCe on 2016/6/6.
 *
 * @author: KyleCe
 */
public class LockOrOpenAdminSettingActivity extends Activity {

    private static final int REQUEST_ADMIN = 896;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lockOrOpenAdminSetting(getApplicationContext());
    }


    /**
     * @throws NullPointerException  if context null
     * @throws IllegalStateException if cannot get device policy manager
     */
    public void lockOrOpenAdminSetting(Context ctx) {
        if (ctx == null) throw new NullPointerException("context nonnull");

        DU.sd("admin", "trigger");

        DevicePolicyManager dpm = (DevicePolicyManager) ctx.getSystemService(Context.DEVICE_POLICY_SERVICE);
        ComponentName adminName = new ComponentName(this, DeviceAdmin.class);

        if (dpm == null) throw new IllegalStateException("cannot get device policy manager");

        if (dpm.isAdminActive(adminName)) {
            dpm.lockNow();
            finish();
        } else {
            // admin active
            DU.sd("admin", "active");

            Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
            intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, adminName);
            intent.setComponent(new ComponentName("com.android.settings", "com.android.settings.DeviceAdminAdd"));
            intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, ctx.getString(R.string.admin_description));
            this.startActivityForResult(intent, REQUEST_ADMIN);
        }
    }


    /**
     * should be declared as static and inner class
     */
    public static class DeviceAdmin extends DeviceAdminReceiver {

        @Override
        public void onEnabled(Context context, Intent intent) {
            // admin rights
            DU.sd("admin", "enable");
        }

        @Override
        public CharSequence onDisableRequested(Context context, Intent intent) {
            DU.sd("admin", "disable");
            return "admin_receiver_status_disable_warning";
        }

        @Override
        public void onDisabled(Context context, Intent intent) {
            // admin rights removed
            DU.sd("admin", "on disable");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_ADMIN) finish();
    }
}
