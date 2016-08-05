package com.ce.game.myapplication.util;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;

/**
 * Created by KyleCe on 2016/8/5.
 *
 * @author: KyleCe
 */
public class PermissionHelper {

    public static final int REQUEST_STORAGE_PERMISSION = 268;
    public static final int REQUEST_ACCOUNT_MANAGER = 123;
    public static final String STORAGE_READ_PERMISSION = Manifest.permission.READ_EXTERNAL_STORAGE;
    public static final String STORAGE_WRITE_PERMISSION = Manifest.permission.READ_EXTERNAL_STORAGE;
    public static final String[] STORAGE_PERMISSION_REQUEST_STRINGS = new String[]{STORAGE_READ_PERMISSION, STORAGE_WRITE_PERMISSION};

    public static final String GET_ACCOUNTS_PERMISSION = Manifest.permission.GET_ACCOUNTS;

    public static boolean noReadWriteExternalStoragePermissions(Context context) {
        return isPermissionNotGranted(context, STORAGE_READ_PERMISSION) ||
                isPermissionNotGranted(context, STORAGE_WRITE_PERMISSION);
    }

    public static boolean noGetAccountsPermission(Context context) {
        return isPermissionNotGranted(context, GET_ACCOUNTS_PERMISSION);
    }

    public static boolean isPermissionNotGranted(Context c, final String p) {
        return ActivityCompat.checkSelfPermission(c, p) != PackageManager.PERMISSION_GRANTED;
    }
}
