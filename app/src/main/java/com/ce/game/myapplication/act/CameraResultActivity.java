package com.ce.game.myapplication.act;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.ce.game.myapplication.R;
import com.ce.game.myapplication.util.DU;

import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class CameraResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        DU.sd("start", "for result");
//        startActivityForResult(new Intent(this, GuideActivity.class),789);

        triggerCameraWithSecure(this, true);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
            }
        }, 1000);
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                DU.sd("camera using???", judgeCameraStrategy());
            }
        };

        timer.schedule(task, 0, 1000);
    }

    public boolean judgeCameraStrategy() {
//        return safeCameraOpen(0);
//        return isCameraUsing();
        return cameraRuning();
    }

    public boolean isCameraUsing() {
        Camera camera = null;
        try {
            camera = Camera.open();
        } catch (RuntimeException e) {
            return true;
        } finally {
            if (camera != null) camera.release();
        }
        return false;
    }

    Camera _camera = null;

    private boolean cameraRuning() {
        try {
            _camera = Camera.open();
            return _camera == null;
        } catch (Exception e) {
            e.printStackTrace();
            return true;
        } finally {
            if (_camera != null)
                _camera.release();
            _camera = null;
        }

    }

    private boolean safeCameraOpen(int id) {
        boolean qOpened = false;

        try {
            releaseCameraAndPreview();
            mCamera = Camera.open(id);
            qOpened = (mCamera != null);
        } catch (Exception e) {
            Log.e(getString(R.string.app_name), "failed to open Camera");
            e.printStackTrace();
        } finally {
            releaseCameraAndPreview();
        }

        return qOpened;
    }

    Camera mCamera;

    private void releaseCameraAndPreview() {
        if (mCamera != null) {
            mCamera.release();
            mCamera = null;
        }
    }

    private static final int REQUEST_CAMERA = 363;


    private void triggerCameraWithSecure(Context context, boolean withSecure) {
        if (!CameraHelper.hasCameraHardware(context)) return;

        judgeCameraStrategy(this, withSecure);

    }

    public static void judgeCameraStrategy(Activity act, boolean withSecure) {
        if (!withSecure) {
            Intent direct = new Intent(MediaStore.INTENT_ACTION_STILL_IMAGE_CAMERA);
            direct.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            act.startActivityForResult(direct, REQUEST_CAMERA);
        }

        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (cameraIntent.resolveActivity(act.getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = CameraHelper.createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
                cameraIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                act.startActivityForResult(cameraIntent, REQUEST_CAMERA);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        DU.sd("result", resultCode);

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        DU.sd("camera", "new intent");

        super.onNewIntent(intent);
    }
}
