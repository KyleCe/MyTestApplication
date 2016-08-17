package com.ce.game.myapplication.home;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Build;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;

import com.ce.game.myapplication.R;
import com.ce.game.myapplication.util.AboutPhoneU;
import com.ce.game.myapplication.util.DU;
import com.ce.game.myapplication.util.Utilities;

import junit.framework.Assert;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by KyleCe on 2016/6/29.
 *
 * @author: KyleCe
 */
public class SettingHelper {
    public static boolean isMyLauncherCurrent(Context context) {
        boolean[] currentAndDefault = isMyLauncherCurrentAndDefault(context);
        Assert.assertFalse(currentAndDefault == null || currentAndDefault.length < 2);

        return currentAndDefault[0];
    }

    public static boolean isMyLauncherDefault(Context context) {
        boolean[] currentAndDefault = isMyLauncherCurrentAndDefault(context);
        Assert.assertFalse(currentAndDefault == null || currentAndDefault.length < 2);

        return currentAndDefault[1];
    }

    public static boolean[] isMyLauncherCurrentAndDefault(Context context) {
        boolean isCurrent = false;
        boolean isDefault = false;

        final IntentFilter filter = new IntentFilter(Intent.ACTION_MAIN);
        filter.addCategory(Intent.CATEGORY_HOME);

        List<IntentFilter> filters = new ArrayList<>();
        filters.add(filter);

        final String myPackageName = context.getPackageName();

        final PackageManager packageManager = context.getPackageManager();

        List<ComponentName> activitiesCurrent = new ArrayList<>();
        List<ComponentName> activitiesDefault = new ArrayList<>();
        packageManager.getPreferredActivities(filters, activitiesCurrent, null);
        packageManager.getPreferredActivities(filters, activitiesDefault, myPackageName);

        for (ComponentName activity : activitiesCurrent)
            if (myPackageName.equals(activity.getPackageName())) isCurrent = true;
        for (ComponentName activity : activitiesDefault)
            if (myPackageName.equals(activity.getPackageName())) isDefault = true;

        return new boolean[]{isCurrent, isDefault};
    }

    private static String findLauncherPackageName(Context context) {
        final Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        final ResolveInfo res = context.getPackageManager().resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY);
        Log.e("SYSTEM_LAUNCHER", "system launcher package name: " + res.activityInfo.packageName);
        return res.activityInfo.packageName;
    }


    private static boolean hasNoCurrentHome(Context context) {
        final IntentFilter filter = new IntentFilter(Intent.ACTION_MAIN);
        filter.addCategory(Intent.CATEGORY_HOME);

        List<IntentFilter> filters = new ArrayList<>();
        filters.add(filter);

        final PackageManager packageManager = context.getPackageManager();

        List<ComponentName> activitiesCurrent = new ArrayList<>();
        packageManager.getPreferredActivities(filters, activitiesCurrent, null);

        return activitiesCurrent.size() == 0;
    }

    public static void fakeLauncherInstalledAndOpenLauncherChooser(Context context, int type) {

        Intent home = null;
        switch (type) {
            case 1:
                home = Intent.makeMainSelectorActivity(Intent.ACTION_MAIN, Intent.CATEGORY_HOME);
                break;
            case 2:
                ComponentName componentName1 = new ComponentName(context, HomeActivity.class);
                home = Intent.makeMainActivity(componentName1);
                break;
            case 3:
//                home = null;
//                findLauncherPackageName(context);
                home = null;

                if (new AboutPhoneU().isHuaWei())
                    try {
                        IntentFilter intentFilter = new IntentFilter();
                        intentFilter.addAction(Intent.ACTION_MAIN);
                        intentFilter.addCategory(Intent.CATEGORY_HOME);
                        Intent intent = new Intent(intentFilter.getAction(0));
                        if (intentFilter.countCategories() > 0 && !TextUtils.isEmpty(intentFilter.getCategory(0))) {
                            intent.addCategory(intentFilter.getCategory(0));
                        }
                        Intent intent2 = new Intent();
                        intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent2.setClassName(Settings.ACTION_HOME_SETTINGS, "com.android.settings.Settings$PreferredSettingsActivity");
                        intent2.putExtra("preferred_app_intent", intent);
                        intent2.putExtra("preferred_app_intent_filter", intentFilter);
                        intent2.putExtra("preferred_app_label", context.getResources().getString(R.string.app_name));
                        intent2.putExtra("preferred_app_package_name", context.getPackageName());
                        intent2.putExtra("preferred_app_class_name", HomeActivity.class.getName());
                        intent2.putExtra("is_user_confirmed", true);
                        context.getApplicationContext().startActivity(intent2);
                    } catch (Exception e) {
                    }
                else
                    DU.t(context, "not huawei");
                break;
            case 4:
                home = Intent.makeMainSelectorActivity(Intent.ACTION_MAIN, Intent.CATEGORY_HOME);
                home = home.createChooser(home, null);
                break;
            case 5:
                isMyLauncherCurrentAndDefault(context);
                printBuildInfo();
                break;
            case 6:
                home = null;
                fakeLauncherInstalledAndOpenLauncherChooser(context);
                break;
            case 7:
                home = null;
                triggerHomeSetting(context);
                break;
            default:
                break;
        }
        if (!(context instanceof Activity))
            home.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        if (home != null) context.startActivity(home);
    }

    private static void printBuildInfo() {
        DU.sd(Build.DEVICE);
        DU.sd(Build.BOARD);
        DU.sd(Build.BOOTLOADER);
        DU.sd(Build.BRAND);
        DU.sd(Build.DISPLAY);
        DU.sd("HOST", Build.HOST);
        DU.sd(Build.ID);
        DU.sd(Build.MODEL);
        DU.sd(Build.PRODUCT);
    }

    public static void openDefaultLauncherSetting(Context context) {
        if (Utilities.isHomeSettingActionAvailable()) triggerHomeSetting(context);
        else processSdkBelowLollipop(context);
    }

    private static void processSdkBelowLollipop(Context context) {
        if (hasNoCurrentHome(context)) {
            Intent home = new Intent(Intent.ACTION_MAIN);
            home.addCategory(Intent.CATEGORY_HOME);
            context.startActivity(home);
        } else
            fakeLauncherInstalledAndOpenLauncherChooser(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void triggerHomeSetting(Context context) {
        context.startActivity(new Intent(Settings.ACTION_HOME_SETTINGS).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
    }

    public static void fakeLauncherInstalledAndOpenLauncherChooser(Context context) {
        PackageManager pm = context.getPackageManager();
        ComponentName componentName = new ComponentName(context, FakeHomeActivity.class);
        pm.setComponentEnabledSetting(componentName, PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);

        startHomeChooserFlexible(context);

        pm.setComponentEnabledSetting(componentName, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
    }

    private static void startHomeChooserFlexible(Context context) {
        Intent home = new Intent(Intent.ACTION_MAIN);
        home.addCategory(Intent.CATEGORY_HOME);
        home.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivityFlexible(context, home);
    }

    private static void startActivityFlexible(Context context, Intent home) {
        if (context instanceof Activity)
            /*do not add flag NEW_TASK to receive activity result*/
            ((Activity) context).startActivityForResult(home, REQUEST_FOR_LAUNCHER);
        else context.startActivity(home.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
    }

    public static final int REQUEST_FOR_LAUNCHER = 465;

}
