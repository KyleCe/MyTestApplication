package com.ce.game.myapplication.act;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;

import com.ce.game.myapplication.R;

public class DecorViewWithColorDrawableActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_decor_view_with_color_drawable);

//        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.wallpaper_01);
//        Canvas canvas = new Canvas();
//        Paint paint = new Paint();
//        paint.setColor(Color.parseColor("#80000000"));
//
//        bitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true);
//        canvas = new Canvas(bitmap);
//
//        canvas.drawBitmap(bitmap, 0, 0, paint);
//        canvas.drawColor(Color.parseColor("#80000000"), PorterDuff.Mode.ADD);

//        ColorFilter filter = new PorterDuffColorFilter(getResources().getColor(R.color.forth_edition_guide_layout_outside_bg)
//                , PorterDuff.Mode.ADD);
//        paint.setColorFilter(filter);

//        Canvas canvas =;
//        canvas.drawBitmap(bitmap, 0, 0, paint);

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.wallpaper_01)
                .copy(Bitmap.Config.ARGB_8888, true);

        new Canvas(bitmap).drawARGB(128,0,0,0);

        getWindow().getDecorView().setBackgroundDrawable(new BitmapDrawable(getResources(), bitmap));
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

            canvas.drawBitmap(bitmap,0,0,pBackground);

            canvas.drawARGB(128,0,0,0);

            canvas.drawColor(Color.parseColor("#80000000"), PorterDuff.Mode.ADD);

//            canvas.drawRect(0, 0, 512, 512, pBackground);
            Paint pText = new Paint();
            pText.setColor(Color.BLACK);
            pText.setTextSize(20);
            canvas.drawText("Sample Text", 100, 100, pText);
        }
    }

}
