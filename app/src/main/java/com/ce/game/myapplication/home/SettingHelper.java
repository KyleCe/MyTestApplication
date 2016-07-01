package com.ce.game.myapplication.home;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;

import com.ce.game.myapplication.util.DU;

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
        if (currentAndDefault == null || currentAndDefault.length < 2) return false;

        return currentAndDefault[0];
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

    public static void fakeLauncherInstalledAndOpenChooser(Context context, int type) {
//        PackageManager pm = context.getPackageManager();
//        ComponentName componentName = new ComponentName(context, FakeHomeActivity.class);
//        pm.setComponentEnabledSetting(componentName, PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);

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
                home = new Intent(Intent.ACTION_MAIN);
                home.addCategory(Intent.CATEGORY_HOME);
                home.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                break;
            case 4:
                home = Intent.makeMainSelectorActivity(Intent.ACTION_MAIN, Intent.CATEGORY_HOME);
                home = home.createChooser(home,null);
                break;
            case 5:
//                if(.equals(""))
                DU.sd(Build.DEVICE);
                DU.sd(Build.BOARD);
                DU.sd(Build.BOOTLOADER);
                DU.sd(Build.BRAND);
                DU.sd(Build.DISPLAY);
                DU.sd("HOST",Build.HOST);
                DU.sd(Build.ID);
                DU.sd(Build.MODEL);
                DU.sd(Build.PRODUCT);

                break;
            case 6:
                break;
            case 7:
                break;
            default:
                break;
        }
        if (!(context instanceof Activity))
            home.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        if(home != null) context.startActivity(home);

//        pm.setComponentEnabledSetting(componentName, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
    }

}
