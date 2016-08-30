package com.ce.game.myapplication.act;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import com.ce.game.myapplication.R;
import com.ce.game.myapplication.util.Const;
import com.ce.game.myapplication.view.GifView;

public class GifSplitActivity extends Activity {

    ImageView mImageView;

    GifView mGifView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        requestWindowFeature(Window.FEATURE_NO_TITLE);

        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
//                        | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                        | View.SYSTEM_UI_FLAG_IMMERSIVE);

        setContentView(R.layout.activity_gif_split);

//        mImageView = (ImageView) findViewById(R.id.image_view);
//
//
//        mImageView.setImageResource(R.drawable.img_animation);
//
//        AnimationDrawable imgAnimation = (AnimationDrawable) mImageView.getDrawable();
//
//        if (imgAnimation .isRunning()) {
//            imgAnimation .stop();
//        }
//        imgAnimation .start();

        Bundle bundle = getIntent().getExtras();
        int type = bundle.getInt(Const.KEY);

        mGifView = (GifView) findViewById(R.id.gif1);

        mGifView.setGifResource(type == Const.TYPE_ONE ? R.drawable.gif_5m : R.drawable.gif);
    }
}
