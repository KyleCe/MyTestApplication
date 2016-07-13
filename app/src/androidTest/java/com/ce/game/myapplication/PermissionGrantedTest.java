package com.ce.game.myapplication;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.test.ApplicationTestCase;

/**
 * Created by KyleCe on 2016/7/13.
 *
 * @author: KyleCe
 */
public class PermissionGrantedTest extends ApplicationTestCase<Application> {
    private String TAG = AppSearchChineseTest.class.getSimpleName();

    private Context mContext;


    public PermissionGrantedTest() {
        super(Application.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mContext = getContext();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }


    /**
     * <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
     * <uses-permission android:name="android.permission.CAMERA"/>
     * <uses-permission android:name="android.permission.WRITE_SETTINGS"/>
     */
    public void testPermissionGranted() {
        assertTrue(isPermissionGranted("android.permission.WRITE_EXTERNAL_STORAGE"));
        assertTrue(isPermissionGranted("android.permission.CAMERA"));
        assertTrue(isPermissionGranted("android.permission.WRITE_SETTINGS"));
    }

    private boolean isPermissionGranted(String permission) {
        int res = getContext().checkCallingOrSelfPermission(permission);
        return res == PackageManager.PERMISSION_GRANTED;
    }
}
