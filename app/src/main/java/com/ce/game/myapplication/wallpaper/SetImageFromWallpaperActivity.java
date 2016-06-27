package com.ce.game.myapplication.wallpaper;

import android.app.WallpaperManager;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.ce.game.myapplication.R;
import com.ce.game.myapplication.util.BitmapU;
import com.ce.game.myapplication.util.DU;

import java.io.IOException;

public class SetImageFromWallpaperActivity extends AppCompatActivity {

    ImageView mOriginalWallpaper;
    ImageView mWallpaperYouPick;
    private static final int REQUEST_PICK_WALLPAPER = 759;
    Drawable mOriginalDrawable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_image_from_wallpaper);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onAction();
            }
        });

        mOriginalWallpaper = (ImageView) findViewById(R.id.original_wallpaper);
        mWallpaperYouPick = (ImageView) findViewById(R.id.wallpaper_just_pick);

        myWallpaperManager = WallpaperManager.getInstance(getApplicationContext());
        mOriginalDrawable = myWallpaperManager.getDrawable();

        mOriginalWallpaper.setImageDrawable(mOriginalDrawable);
    }

    private void onAction() {

        Intent intent = new Intent(Intent.ACTION_SET_WALLPAPER);
        startActivityForResult(
//                intent,
                Intent.createChooser(intent, "Select Lock Screen Wallpaper"),
                REQUEST_PICK_WALLPAPER);

//        this.startActivityForResult(new Intent(Intent.ACTION_SET_WALLPAPER),
//                REQUEST_PICK_WALLPAPER);
    }

    WallpaperManager myWallpaperManager;

    protected void onSetWallpaper() {

        DU.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    myWallpaperManager.setBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.guidepages_bg_01));
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_PICK_WALLPAPER) {
            DU.sd("return ");

            setWallpaperJustPick();
        }
    }

    private void setWallpaperJustPick() {
        mWallpaperYouPick.setImageDrawable(myWallpaperManager.getDrawable());
        reverseWallpaperDelay();
    }

    private void reverseWallpaperDelay() {
        mOriginalWallpaper.postDelayed(new Runnable() {
            @Override
            public void run() {
                mHandler.sendEmptyMessage(0);
            }
        }, 1000);
    }

    private Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    try {
                        myWallpaperManager.setBitmap(BitmapU.drawableToBitmap(mOriginalDrawable));
                    } catch (IOException e) {
                        DU.sd("wallpaper setting", e);
                    }
                    break;
                default:
                    break;
            }
        }
    };
}
