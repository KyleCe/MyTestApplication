package com.ce.game.myapplication.util;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.support.annotation.StringRes;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class Permissions {

    private static final String TAG = Permissions.class.getSimpleName();

    public static final int PERMISSION_REQUEST_CODE_CALENDAR = 1;
    public static final int PERMISSION_REQUEST_CODE_CAMERA = 2;
    public static final int PERMISSION_REQUEST_CODE_CONTACTS = 3;
    public static final int PERMISSION_REQUEST_CODE_LOCATION = 4;
    public static final int PERMISSION_REQUEST_CODE_MICROPHONE = 5;
    public static final int PERMISSION_REQUEST_CODE_PHONE = 6;
    public static final int PERMISSION_REQUEST_CODE_SENSORS = 7;
    public static final int PERMISSION_REQUEST_CODE_SMS = 8;
    public static final int PERMISSION_REQUEST_CODE_STORAGE = 9;


    public static final int REQUEST_STORAGE_PERMISSION = 268;
    public static final int REQUEST_ACCOUNT_MANAGER = 123;
    public static final String STORAGE_READ_PERMISSION = Manifest.permission.READ_EXTERNAL_STORAGE;
    public static final String STORAGE_WRITE_PERMISSION = Manifest.permission.READ_EXTERNAL_STORAGE;
    public static final String[] STORAGE_PERMISSION_REQUEST_STRINGS = new String[]{STORAGE_READ_PERMISSION, STORAGE_WRITE_PERMISSION};

    public static final String GET_ACCOUNTS_PERMISSION = Manifest.permission.GET_ACCOUNTS;

    public interface PermissionCallbacks extends
            ActivityCompat.OnRequestPermissionsResultCallback {

        void onPermissionsGranted(List<String> perms);

        void onPermissionsDenied(List<String> perms);
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    public @interface AfterPermissionGranted {
        int value();
    }

    public static boolean hasPermissions(Context context, String... perms) {
        for (String perm : perms) {
            boolean hasPerm = (ContextCompat.checkSelfPermission(context, perm) == PackageManager.PERMISSION_GRANTED);
            if (!hasPerm) {
                return false;
            }
        }

        return true;
    }

    public static void requestPermissions(final Object object, String rationale,
                                          final int requestCode, final String... perms) {
        requestPermissions(object, rationale,
                android.R.string.ok,
                android.R.string.cancel,
                requestCode, perms);
    }

    public static void requestPermissions(final Object object, String rationale,
                                          @StringRes int positiveButton,
                                          @StringRes int negativeButton,
                                          final int requestCode, final String... perms) {

        checkCallingObjectSuitability(object);

        boolean shouldShowRationale = false;
        for (String perm : perms) {
            shouldShowRationale = shouldShowRationale || shouldShowRequestPermissionRationale(object, perm);
        }

        if (!shouldShowRationale) {
            if (null != rationale) {
                AlertDialog dialog = new AlertDialog.Builder(getActivity(object))
                        .setMessage(rationale)
                        .setPositiveButton(positiveButton, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                executePermissionsRequest(object, perms, requestCode);
                            }
                        }).create();
                dialog.setCancelable(false);
                dialog.show();
            } else {
                executePermissionsRequest(object, perms, requestCode);
            }
        } else {
            executePermissionsRequest(object, perms, requestCode);
        }
    }

    public static void onRequestPermissionsResult(int requestCode, String[] permissions,
                                                  int[] grantResults, Object object) {

        checkCallingObjectSuitability(object);
        PermissionCallbacks callbacks = (PermissionCallbacks) object;

        // Make a collection of granted and denied permissions from the request.
        ArrayList<String> granted = new ArrayList<String>();
        ArrayList<String> denied = new ArrayList<String>();
        for (int i = 0; i < permissions.length; i++) {
            String perm = permissions[i];
            if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                granted.add(perm);
            } else {
                denied.add(perm);
            }
        }

        // Report granted permissions, if any.
        if (!granted.isEmpty()) {
            // Notify callbacks
            callbacks.onPermissionsGranted(granted);
        }

        // Report denied permissions, if any.
        if (!denied.isEmpty()) {
            callbacks.onPermissionsDenied(denied);
        }

        // If 100% successful, call annotated methods
        if (!granted.isEmpty() && denied.isEmpty()) {
            runAnnotatedMethods(object, requestCode);
        }
    }

    private static boolean shouldShowRequestPermissionRationale(Object object, String perm) {
        if (object instanceof Activity) {
            return ActivityCompat.shouldShowRequestPermissionRationale((Activity) object, perm);
        } else if (object instanceof Fragment) {
            return ((Fragment) object).shouldShowRequestPermissionRationale(perm);
        } else {
            return false;
        }
    }

    private static void executePermissionsRequest(Object object, String[] perms, int requestCode) {
        checkCallingObjectSuitability(object);

        if (object instanceof Activity) {
            ActivityCompat.requestPermissions((Activity) object, perms, requestCode);
        } else if (object instanceof Fragment) {
            ((Fragment) object).requestPermissions(perms, requestCode);
        }
    }

    private static Activity getActivity(Object object) {
        if (object instanceof Activity) {
            return ((Activity) object);
        } else if (object instanceof Fragment) {
            return ((Fragment) object).getActivity();
        } else {
            return null;
        }
    }

    private static void runAnnotatedMethods(Object object, int requestCode) {
        Class clazz = object.getClass();
        for (Method method : clazz.getDeclaredMethods()) {
            if (method.isAnnotationPresent(AfterPermissionGranted.class)) {
                // Check for annotated methods with matching request code.
                AfterPermissionGranted ann = method.getAnnotation(AfterPermissionGranted.class);
                if (ann.value() == requestCode) {
                    // Method must be void so that we can invoke it
                    if (method.getParameterTypes().length > 0) {
                        throw new RuntimeException("Cannot execute non-void method " + method.getName());
                    }

                    try {
                        // Make method accessible if private
                        if (!method.isAccessible()) {
                            method.setAccessible(true);
                        }
                        method.invoke(object);
                    } catch (IllegalAccessException e) {
                        Log.e(TAG, "runDefaultMethod:IllegalAccessException", e);
                    } catch (InvocationTargetException e) {
                        Log.e(TAG, "runDefaultMethod:InvocationTargetException", e);
                    }
                }
            }
        }
    }

    private static void checkCallingObjectSuitability(Object object) {
        // Make sure Object is an Activity or Fragment
        if (!((object instanceof Fragment) || (object instanceof Activity))) {
            throw new IllegalArgumentException("Caller must be an Activity or a Fragment.");
        }

        // Make sure Object implements callbacks
        if (!(object instanceof PermissionCallbacks)) {
            throw new IllegalArgumentException("Caller must implement PermissionCallbacks.");
        }
    }

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
