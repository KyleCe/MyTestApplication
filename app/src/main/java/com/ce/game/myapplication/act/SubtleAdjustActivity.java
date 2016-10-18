package com.ce.game.myapplication.act;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ce.game.myapplication.R;
import com.ce.game.myapplication.userguideanim.FloatViewModelTip;
import com.ce.game.myapplication.util.DU;
import com.ce.game.myapplication.util.ThreadPoolU;
import com.ce.game.myapplication.view.ShimmerTextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SubtleAdjustActivity extends Activity implements Handler.Callback {

    @BindView(R.id.text_for_multi_link)
    protected TextView mLinkText;

    protected ShimmerTextView mShimmerTextView;

//
//    @BindView(R.id.image_view)
    protected ImageView mImageView;
    protected FloatViewModelTip mViewModelTip;

    Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subtle_adjust);

        mHandler = new Handler(Looper.getMainLooper(), this);

        mViewModelTip = new FloatViewModelTip(getApplicationContext());

        mImageView = new ImageView(getApplicationContext());

        mViewModelTip.setView(mImageView);

        initView();

        ThreadPoolU.execute(new Runnable() {
            @Override
            public void run() {
                Bitmap b = generateTimeImageBitmap(getApplicationContext(),10,11);

                Message msg = new Message();
                msg.what = 0;
                msg.obj = b;
                mHandler.sendMessage(msg);
            }
        });

        mShimmerTextView = (ShimmerTextView) findViewById(R.id.shimmer);

        mShimmerTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHandler.sendEmptyMessage(0);
            }
        });
    }

    private void initView() {
        ButterKnife.bind(this);

        customTextView(mLinkText);

//        mLinkText.setText(Html.fromHtml("<a href=\"http://www.google.com\">Google</a>"));
//        mLinkText.setMovementMethod(LinkMovementMethod.getInstance());
    }

    private void customTextView(TextView view) {
        SpannableStringBuilder spanTxt = new SpannableStringBuilder(
                "I agree to the prefix adding to expand to two lines");
        spanTxt.append("Term of services");
        spanTxt.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                Toast.makeText(getApplicationContext(), "Terms of services Clicked",
                        Toast.LENGTH_SHORT).show();
            }
        }, spanTxt.length() - "Term of services".length(), spanTxt.length(), 0);
        spanTxt.append(" and");
        spanTxt.setSpan(new ForegroundColorSpan(Color.BLACK), 32, spanTxt.length(), 0);
        spanTxt.append(" Privacy Policy");
        spanTxt.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                Toast.makeText(getApplicationContext(), "Privacy Policy Clicked",
                        Toast.LENGTH_SHORT).show();
            }
        }, spanTxt.length() - " Privacy Policy".length(), spanTxt.length(), 0);
        view.setMovementMethod(LinkMovementMethod.getInstance());
        view.setText(spanTxt, TextView.BufferType.SPANNABLE);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
    }

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case 0:
                mImageView.setImageBitmap((Bitmap) msg.obj);
                DU.pwa("w h", mImageView.getWidth(), mImageView.getHeight());

                mShimmerTextView.startShimmer();
                mHandler.sendEmptyMessageDelayed(1,5000);
                break;
            case 1:
                mShimmerTextView.stopShimmer();
                break;
            default:
                break;
        }

        return false;
    }

    @NonNull
    public static Bitmap generateTimeImageBitmap(Context context, int hour, int minute) {
        DU.assertNotNull(context);
        Resources resources = context.getResources();

        Bitmap bitmap = Bitmap.createBitmap(174
                , 70,
                Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmap);

        Paint paint = new Paint();
        paint.setAntiAlias(true);

        paint.setColor(Color.WHITE);
        paint.setTextSize(60);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER));


        String textToDraw = String.format(resources.getConfiguration().locale, "%02d:%02d", hour, minute);

        Rect bounds = new Rect();
        paint.getTextBounds(textToDraw, 0, textToDraw.length(), bounds);

        canvas.drawText(textToDraw, 0, bounds.height(), paint);

        return bitmap;
    }
}
