package com.ce.game.myapplication.act;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;

import com.ce.game.myapplication.R;
import com.ce.game.myapplication.util.AboutPhoneU;
import com.ce.game.myapplication.util.BitmapU;
import com.ce.game.myapplication.util.DU;

public class DecorViewWithColorDrawableActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        requestWindowFeature(Window.FEATURE_NO_TITLE);

        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
//                        | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                        | View.SYSTEM_UI_FLAG_IMMERSIVE);

        setContentView(R.layout.activity_decor_view_with_color_drawable);

        final Context context = getApplicationContext();

//        final View testView = findViewById(R.id.test_view);


//        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.wallpaper_01)
//                .copy(Bitmap.Config.ARGB_8888, true);

        DU.execute(new Runnable() {
            @Override
            public void run() {
                Bitmap originalB = BitmapU.drawableToBitmap(getWindow().getDecorView().getBackground());

                Canvas canvas = new Canvas();

                //Get the width and height of the screen
                DisplayMetrics d = new DisplayMetrics();
                getWindowManager().getDefaultDisplay().getMetrics(d);
                int width  = d.widthPixels;
                int height = d.heightPixels;

                Paint paint = new Paint();
                paint.setColor(Color.parseColor("#80000000"));
                paint.setStyle(Paint.Style.FILL);

                canvas.drawRect(0, 0, width, height, paint);

                Path path = new Path();
                canvas.drawARGB(128,125,0,0);

                int navHeight = new AboutPhoneU().getNavH(getApplicationContext());
                if (navHeight != 0) {
                    int screenHeight = new AboutPhoneU().getRealScreenHeightIncludeVirtualButtonBar(context);
                    int startY = screenHeight - navHeight;

                    path.moveTo(0, startY);
                    path.lineTo(canvas.getWidth(), startY);
                    path.lineTo(canvas.getWidth(), screenHeight);
                    path.lineTo(0, canvas.getHeight());

                    canvas.drawPath(path, paint);
                }

//                getWindow().getDecorView().setBackgroundDrawable(new BitmapDrawable(getResources(), originalB));
                getWindow().getDecorView().draw(canvas);

//                testView.draw(canvas);
            }
        });

    }

    public class MyCanvas extends View {
        public MyCanvas(Context context) {
            super(context);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);

            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.wallpaper_01)
                    .copy(Bitmap.Config.ARGB_8888, true);


            Paint pBackground = new Paint();

            pBackground.setColor(Color.WHITE);

            canvas.drawBitmap(bitmap, 0, 0, pBackground);

            canvas.drawARGB(128, 0, 0, 0);

            canvas.drawColor(Color.parseColor("#80000000"), PorterDuff.Mode.ADD);

//            canvas.drawRect(0, 0, 512, 512, pBackground);
            Paint pText = new Paint();
            pText.setColor(Color.BLACK);
            pText.setTextSize(20);
            canvas.drawText("Sample Text", 100, 100, pText);
        }
    }

}
