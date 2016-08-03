package com.ce.game.myapplication.wallpaper;

import android.app.Activity;
import android.app.WallpaperManager;
import android.os.Bundle;

import com.ce.game.myapplication.R;

public class WallpaperActivity extends Activity {

    private int[] mDefaultWallpaperResourceIdArray = {
            R.drawable.wallpaper_01,
            R.drawable.wallpaper_03,
            R.drawable.wallpaper_05,
            R.drawable.wallpaper_06,
            R.drawable.wallpaper_07,
            R.drawable.wallpaper_09,
            R.drawable.wallpaper_10,
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallpaper);

        WallpaperManager wm = WallpaperManager.getInstance(this);
        try {
            wm.setResource(mDefaultWallpaperResourceIdArray[5]);
        } catch (Exception e) {
        }

    }
}
