package com.ce.game.myapplication.home;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;

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

    public static void fakeLauncherInstalledAndOpenChooser(Context context) {
        PackageManager pm = context.getPackageManager();
        ComponentName componentName = new ComponentName(context, FakeHomeActivity.class);
        pm.setComponentEnabledSetting(componentName, PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);

        Intent home = Intent.makeMainSelectorActivity(Intent.ACTION_MAIN, Intent.CATEGORY_HOME);
        home.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        home.addFlags(Intent.FLAG_ACTIVITY_TASK_ON_HOME);
        context.startActivity(home);

        pm.setComponentEnabledSetting(componentName, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
    }

}
