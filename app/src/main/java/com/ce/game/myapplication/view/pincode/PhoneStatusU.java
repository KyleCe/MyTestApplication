package com.ce.game.myapplication.view.pincode;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.BatteryManager;
import android.os.Environment;
import android.os.StatFs;

import junit.framework.Assert;

import java.io.File;

/**
 * Created by KyleCe on 2016/6/2.
 *
 * @author: KyleCe
 */
final public class PhoneStatusU {
    private static final int LEAST_SIZE_FOR_AVAILABLE_SDCARD = 3072;/*kB*/

    public static float getBatteryLevel(Context context) {
        if (context == null) return 0;

        Intent batteryIntent = context.registerReceiver(null, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        int level = batteryIntent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        int scale = batteryIntent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);

        // Error checking that probably isn't needed added just in case.
        if (level == -1 || scale == -1) return 50.0f;

        return ((float) level / (float) scale) * 100.0f;
    }

    public static boolean isPowerConnected(Context context) {
        Intent intent = context.registerReceiver(null, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        int plugged = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
        return plugged == BatteryManager.BATTERY_PLUGGED_AC || plugged == BatteryManager.BATTERY_PLUGGED_USB;
    }

    public static boolean isNetworkAvailable(Context context) {
        Assert.assertNotNull(context);
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static boolean isSdcardReady() {
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }

    @SuppressWarnings("deprecation")
    public static boolean isSdcardAvailable() {
        if (!isSdcardReady()) return false;

        File sdcardDir = Environment.getExternalStorageDirectory();
        StatFs sf = new StatFs(sdcardDir.getPath());
        long availCount = sf.getAvailableBlocks();
        long blockSize = sf.getBlockSize();
        long availSize = availCount * blockSize / 1024;

        return availSize >= LEAST_SIZE_FOR_AVAILABLE_SDCARD;
    }

}
