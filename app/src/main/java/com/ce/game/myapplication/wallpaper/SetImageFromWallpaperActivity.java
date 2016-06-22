package com.ce.game.myapplication.wallpaper;

import android.app.WallpaperManager;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.ce.game.myapplication.R;
import com.ce.game.myapplication.util.DU;

import java.io.IOException;

public class SetImageFromWallpaperActivity extends AppCompatActivity {

    ImageView mImageToSet;
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

        mImageToSet = (ImageView) findViewById(R.id.image_to_set);

        myWallpaperManager = WallpaperManager.getInstance(getApplicationContext());
        mOriginalDrawable = myWallpaperManager.getDrawable();
        mImageToSet.setImageDrawable(mOriginalDrawable);
    }

    private void onAction() {

        Intent intent = new Intent(Intent.ACTION_SET_WALLPAPER);
        startActivityForResult(Intent.createChooser(intent, "Select Lock Screen Wallpaper"),
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

        if (requestCode == REQUEST_PICK_WALLPAPER)
            DU.sd("return ");
    }
}
