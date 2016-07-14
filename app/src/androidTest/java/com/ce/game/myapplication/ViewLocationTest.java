package com.ce.game.myapplication;

import android.app.Application;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.test.ApplicationTestCase;
import android.test.UiThreadTest;

import com.ce.game.myapplication.showcase.LeadActivity;
import com.ce.game.myapplication.util.DU;

/**
 * Created by KyleCe on 2016/7/13.
 *
 * @author: KyleCe
 */
public class ViewLocationTest extends ApplicationTestCase<Application> {
    private String TAG = AppSearchChineseTest.class.getSimpleName();

    private Context mContext;

    private LeadActivity mActivity;

    public ViewLocationTest() {
        super(Application.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mContext = getContext();

        mActivity = new LeadActivity();
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

    @UiThreadTest
    public void testViewLocationOnScreen() {
        // Trying to trigger layout

        // Successfull asserts
        assertTrue(mActivity.hasWindowFocus());
        mActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        // Unexpected screen coordinates returned from
        // getLocationOnScreen() and getLocationInWindow()
        int[] above = new int[2];
        mActivity.mFirst.getLocationOnScreen(above);
        int[] below = new int[2];
        mActivity.mSecond.getLocationOnScreen(below);
        DU.sd("getLocationOnScreen-above", above);
        DU.sd("getLocationOnScreen-below", below);
        // Logs screen coodinates [0, 76] and [0, 178]

        above = new int[2];
        mActivity.mFirst.getLocationInWindow(above);
        below = new int[2];
        mActivity.mSecond.getLocationInWindow(below);
        DU.sd("getLocationInWindow-above", above);
        DU.sd("getLocationInWindow-below", below);
        // Logs window coordinates [0, 76] and [0, 178]
    }
}
