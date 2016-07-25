package com.ce.game.myapplication.act;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;

import com.ce.game.myapplication.R;
import com.ce.game.myapplication.util.DU;

public class RequestPermissionActivity extends Activity {

    private Context mContext;

    private final int REQUEST_PERMISSION = 268;
    private final String READ_PERMISSION = Manifest.permission.READ_EXTERNAL_STORAGE;
    private final String WRITE_PERMISSION = Manifest.permission.READ_EXTERNAL_STORAGE;
    private final String[] PERMISSION_REQUEST_STRINGS = new String[]{READ_PERMISSION, WRITE_PERMISSION};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_permission);

        mContext = RequestPermissionActivity.this;

        if (noReadWriteExternalStorage())
            ActivityCompat.requestPermissions(RequestPermissionActivity.this
                    , PERMISSION_REQUEST_STRINGS, REQUEST_PERMISSION);
    }

    private boolean noReadWriteExternalStorage() {
        return isPermissionNotGranted(mContext, READ_PERMISSION) ||
                isPermissionNotGranted(mContext, WRITE_PERMISSION);
    }

    private boolean isPermissionNotGranted(Context c, final String p) {
        return ActivityCompat.checkSelfPermission(c, p) != PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        if (requestCode == REQUEST_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                DU.sd("permission request ok");
            } else {
                DU.sd("permission request fail");
            }
        }

    }

}
