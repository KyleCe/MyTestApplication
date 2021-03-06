package com.ce.game.myapplication;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.ce.game.myapplication.act.KotlinActivity;
import com.ce.game.myapplication.util.DU;
import com.ce.game.myapplication.view.PageRecyclerView;

public class MainActivity extends Activity {

    private TextView tvHello;

    private PageRecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private Context context;
    AnimationSet animationSet;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    int count = 0;

    FrameLayout mContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;

        tvHello = (TextView) findViewById(R.id.hello_tv);
        mContainer = (FrameLayout) findViewById(R.id.container);

        tvHello.setText("Front look !\n\t\t sometimes stay");

        guideToDebugActivity(null);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                testTranY(tvHello);
            }
        }, 200);
    }


    private void testTranY(View mScanView) {
        int[] locations = new int[2];
        mScanView.getLocationOnScreen(locations);
        ObjectAnimator tran = ObjectAnimator.ofFloat(mScanView, View.TRANSLATION_Y, -200);

        tran.setDuration(500);
        tran.setRepeatCount(3);
        tran.setRepeatMode(ValueAnimator.REVERSE);
        tran.setInterpolator(new DecelerateInterpolator());
        tran.start();
    }

    public void guideToDebugActivity(View view) {
//        startActivity(new Intent(context, LockOrOpenAdminSettingActivity.class));
//        startActivity(new Intent(context, CameraResultActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
//        startActivity(new Intent(context, FontTextActivity.class));
//        startActivity(new Intent(context, AnimLeftAndRightActivity.class));
//        startActivity(new Intent(context, HomeActivity.class));
//        startActivity(new Intent(context, UpAndLeftActivity.class));
//        startActivity(new Intent(context, PermissionActivity.class));
//        startActivity(new Intent(context, IndexActivity.class));
//        startActivity(new Intent(context, CheckboxActivity.class));
//        startActivity(new Intent(context, GuideToSettingActivity.class));
//        startActivity(new Intent(context, RippleActivity.class));
//        startActivity(new Intent(context, DecorViewWithColorDrawableActivity.class));
//        startActivity(new Intent(context, LeadActivity.class));
//        startActivity(new Intent(context, CenterHandActivity.class));
//        startActivity(new Intent(context, GuideAnimActivity.class));
//        startActivity(new Intent(context, FloatBackPressActivity.class));
//        startActivity(new Intent(context, SubtleAdjustActivity.class));
//        startActivity(new Intent(context, ShimmerActivity.class));
//        startActivity(new Intent(context, FloatBackPressActivity.class));
        startActivity(new Intent(context, KotlinActivity.class));
//        startActivity(new Intent(context, AllKeyboardActivity.class));
//        startActivity(new Intent(context, CircleProgressActivity.class));
//        startActivity(new Intent(context, TessActivity.class));
//        startActivity(new Intent(context, SetImageFromWallpaperActivity.class));
//        startActivity(new Intent(context, GoogleAccountActivity.class));
//        startActivity(new Intent(context, RequestPermissionActivity.class));
//        startActivity(new Intent(context, RollingAnimActivity.class));
//        startActivity(new Intent(context, WallpaperActivity.class));
//        startActivity(new Intent(context, NotiAccessActivity.class));
//        startActivity(new Intent(context, ImageViewScrollActivity.class));
//        startActivity(new Intent(context, ViewStubWithAnimActivity.class));
//        startActivity(new Intent(context, CamTestActivity.class));
//        startActivity(new Intent(context, GuideActivity.class));
//        startActivity(new Intent(context, TransparentActivity.class));
//        finish();
    }


    @Override
    public void onStart() {
        super.onStart();


    }

    @Override
    public void onStop() {
        super.onStop();
    }

    public void clickButton2(View view) {
//        startActivity(new Intent(context, GifSplitActivity.class).putExtra(Const.KEY, Const.TYPE_ONE));
        AnimatorU.show(mContainer, AnimatorU.Direction.left);


    }

    public void clickButton1(View view) {
//        startActivity(new Intent(context, GifSplitActivity.class).putExtra(Const.KEY, Const.TYPE_TWO));
        AnimatorU.hide(mContainer, AnimatorU.Direction.right);
//        AnimatorU.show(mContainer,AnimatorU.Direction.right);

//        mPhone.showItemCount(1);

    }

    public void clickButton3(View view) {
        AnimatorU.hide(mContainer, AnimatorU.Direction.top);
//        AnimatorU.show(mContainer,AnimatorU.Direction.top);
//        mPhone.showItemCount(3);

    }

    public void clickButton4(View view) {
        new AnimationEssential.Builder()
                .setView(mContainer)
                .setDirection(AnimatorU.Direction.left)
                .setEndVisiblity(View.VISIBLE)
                .setEndAction(new Runnable() {
                    @Override
                    public void run() {
                        DU.t(getApplicationContext(), "builder");
                    }
                })
                .build().start();

//        mPhone.showItemCount(4);

    }
}
