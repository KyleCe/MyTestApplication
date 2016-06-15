package com.ce.game.myapplication.admin;

import android.app.admin.DeviceAdminReceiver;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

import com.ce.game.myapplication.R;
import com.ce.game.myapplication.util.DU;

/**
 * Created by KyleCe on 2016/6/6.
 *
 * @author: KyleCe
 */
final public class AdminHelper {

    /**
     * @throws NullPointerException if context null
     */
    public static void lockOrOpenAdminSetting(Context ctx) {
        if (ctx == null) throw new NullPointerException("context nonnull");

        DU.sd("admin", "trigger");

        DevicePolicyManager dpm = (DevicePolicyManager) ctx.getSystemService(Context.DEVICE_POLICY_SERVICE);
        ComponentName adminName = new ComponentName(ctx, DeviceAdmin.class);

        if (dpm == null) return;

        if (dpm.isAdminActive(adminName)) {
            try {
                dpm.lockNow();
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            // admin active
            DU.sd("admin", "active");

            Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
            intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, adminName);
            intent.setComponent(new ComponentName("com.android.settings", "com.android.settings.DeviceAdminAdd"));
            intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, ctx.getString(R.string.admin_description));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            ctx.startActivity(intent);
        }

    }


    final public class DeviceAdmin extends DeviceAdminReceiver {
        public DeviceAdmin() {
        }

        @Override
        public void onEnabled(Context context, Intent intent) {
            // admin rights
//            .getPreferences().edit().putBoolean(App.ADMIN_ENABLED, true).commit(); //App.getPreferences() returns the sharedPreferences
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
}
