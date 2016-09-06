package com.ce.game.myapplication.camera;

/**
 * @author Jose Davis Nidhin
 */

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.ShutterCallback;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.ce.game.myapplication.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class CamTestActivity extends Activity {
    private static final String TAG = "CamTestActivity";
    Preview preview;
    Button buttonClick;
    Camera camera;
    Activity act;
    Context ctx;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ctx = this;
        act = this;
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.main);

        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {

                openCamera();
            }
        });

        preview = new Preview(this, (SurfaceView) findViewById(R.id.surfaceView));
        preview.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        ((FrameLayout) findViewById(R.id.layout)).addView(preview);
        preview.setKeepScreenOn(true);

        preview.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if (camera != null)
                    camera.takePicture(shutterCallback, rawCallback, jpegCallback);
            }
        });

        Toast.makeText(ctx, "take_photo_help", Toast.LENGTH_LONG).show();

        //		buttonClick = (Button) findViewById(R.id.btnCapture);
        //
        //		buttonClick.setOnClickListener(new OnClickListener() {
        //			public void onNumberClick(View v) {
        ////				preview.camera.takePicture(shutterCallback, rawCallback, jpegCallback);
        //				camera.takePicture(shutterCallback, rawCallback, jpegCallback);
        //			}
        //		});
        //
        //		buttonClick.setOnLongClickListener(new OnLongClickListener(){
        //			@Override
        //			public boolean onLongClick(View arg0) {
        //				camera.autoFocus(new AutoFocusCallback(){
        //					@Override
        //					public void onAutoFocus(boolean arg0, Camera arg1) {
        //						//camera.takePicture(shutterCallback, rawCallback, jpegCallback);
        //					}
        //				});
        //				return true;
        //			}
        //		});
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void openCamera() {
        int numCams = Camera.getNumberOfCameras();
        if (numCams > 0) {
            try {
                camera = Camera.open();
            } catch (RuntimeException ex) {
                Toast.makeText(ctx, "camera_not_found", Toast.LENGTH_LONG).show();
                try {
                    camera = Camera.open(0);
                } catch (Exception e) {
                }

            } finally {
                if (camera != null) {
                    camera.startPreview();
                    preview.setCamera(camera);
                }
            }
        }
    }

    @Override
    protected void onPause() {
        if (camera != null) {
            camera.stopPreview();
            preview.setCamera(null);
            camera.release();
            camera = null;
        }
        super.onPause();
    }

    private void resetCam() {
        camera.startPreview();
        preview.setCamera(camera);
    }

    private void refreshGallery(File file) {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        mediaScanIntent.setData(Uri.fromFile(file));
        sendBroadcast(mediaScanIntent);
    }

    ShutterCallback shutterCallback = new ShutterCallback() {
        public void onShutter() {
            //			 Log.d(TAG, "onShutter'd");
        }
    };

    PictureCallback rawCallback = new PictureCallback() {
        public void onPictureTaken(byte[] data, Camera camera) {
            //			 Log.d(TAG, "onPictureTaken - raw");
        }
    };

    PictureCallback jpegCallback = new PictureCallback() {
        public void onPictureTaken(byte[] data, Camera camera) {
            new SaveImageTask().execute(data);
            resetCam();
            Log.d(TAG, "onPictureTaken - jpeg");
        }
    };

    private class SaveImageTask extends AsyncTask<byte[], Void, Void> {

        @Override
        protected Void doInBackground(byte[]... data) {
            FileOutputStream outStream = null;

            // Write to SD Card
            try {
                File sdCard = Environment.getExternalStorageDirectory();
                File dir = new File(sdCard.getAbsolutePath() + "/camtest");
                dir.mkdirs();

                String fileName = String.format("%d.jpg", System.currentTimeMillis());
                File outFile = new File(dir, fileName);

                outStream = new FileOutputStream(outFile);
                outStream.write(data[0]);
                outStream.flush();
                outStream.close();

                Log.d(TAG, "onPictureTaken - wrote bytes: " + data.length + " to " + outFile.getAbsolutePath());

                refreshGallery(outFile);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
            }
            return null;
        }

    }
}


