package com.ce.game.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.ce.game.myapplication.act.RequestPermissionActivity;
import com.ce.game.myapplication.reverse.Rotate3dAnimation;
import com.ce.game.myapplication.view.HorizontalSpacesItemDecoration;
import com.ce.game.myapplication.view.MyRecyclerViewAdapter;
import com.ce.game.myapplication.view.PageRecyclerView;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

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
    private GoogleApiClient client;
    int count = 0;

    FrameLayout container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        context = this;

        tvHello = (TextView) findViewById(R.id.hello_tv);
        container = (FrameLayout) findViewById(R.id.container);

        tvHello.setText("Front look !\n\t\t sometimes stay");

        guideToDebugActivity();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                rotate3DTry();

                guideToDebugActivity();
            }
        });


        final String path = "/storage/emulated/0/WhatsApp/Media/WhatsApp Video/VID_20160330_204630.mp4";

//        mRecyclerView = (PageRecyclerView) findViewById(R.id.recycler_view);

//        loadRecyclerView();


//        tvHello.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                Intent intent = new Intent().setClass(MainActivity.this, RollingAnimActivity.class);
////                intent.putExtra("path", path);
////                startActivity(intent);
//
//            }
//        });

//        tvHello.postDelayed(new Runnable() {
//            @Override
//            public void run() {
////                tvHello.performClick();
//            }
//        }, 300);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    private void rotate3DTry() {

        count++;
        animationSet = new AnimationSet(true);

//                if (count % 3 == 0) {
//                    ScaleAnimation scaleAnimation = new ScaleAnimation(1.0f, 0.8f, 1.0f, 0.8f);
//                    animationSet.addAnimation(scaleAnimation);
//                }

//        TranslateAnimation translateAnimation = new TranslateAnimation(Animation.ABSOLUTE, mXdelta, Animation.RELATIVE_TO_SELF, 0f, Animation.ABSOLUTE, mYdelta, Animation.RELATIVE_TO_SELF, 0f);
//        animationSet.addAnimation(translateAnimation);

        final float toDegree = (count % 2 == 0) ? 180 : 360;
//                (count % 2 == 0) ? 800 :
        final long dur = 1000;

        Rotate3dAnimation rotate3dAnimation = new Rotate3dAnimation(0, 90, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f, 0, true);
        rotate3dAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

                AnimationSet animationSet = new AnimationSet(true);
                Rotate3dAnimation rotate3dAnimationSecondPart = new Rotate3dAnimation(270, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f, 0, true);

//                        ScaleAnimation scaleAnimation = new ScaleAnimation( 0.9f,1.0f, 0.9f, 1.0f);
//                        animationSet.addAnimation(scaleAnimation);

                animationSet.addAnimation(rotate3dAnimationSecondPart);
                animationSet.setInterpolator(new AccelerateInterpolator());

                animationSet.setDuration(dur >> 1);

                animationSet.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        container.postDelayed(new Runnable() {
                            @Override
                            public void run() {
//                                tvHello.setVisibility(View.INVISIBLE);
                                tvHello.setText(
                                        count % 2 == 0 ?
                                                "Front look !\n\t\t sometimes stay" :
                                                "Back look!\n\t\t sometimes try");
//                                tvHello.setVisibility(View.VISIBLE);
                            }
                        }, 0);
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {

//                        container.postDelayed(new Runnable() {
//                            @Override
//                            public void run() {
//                                tvHello.setVisibility(View.VISIBLE);
//                            }
//                        }, 10);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });

                container.startAnimation(animationSet);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        animationSet.addAnimation(rotate3dAnimation);
        animationSet.setDuration(dur >> 1);


        container.startAnimation(animationSet);
    }

    private void guideToDebugActivity() {
//        startActivity(new Intent(context, LockOrOpenAdminSettingActivity.class));
//        startActivity(new Intent(context, CameraResultActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
//        startActivity(new Intent(context, FontTextActivity.class));
//        startActivity(new Intent(context, AnimLeftAndRightActivity.class));
//        startActivity(new Intent(context, HomeActivity.class));
//        startActivity(new Intent(context, IndexActivity.class));
//        startActivity(new Intent(context, CheckboxActivity.class));
//        startActivity(new Intent(context, LeadActivity.class));
//        startActivity(new Intent(context, SetImageFromWallpaperActivity.class));
//        startActivity(new Intent(context, GoogleAccountActivity.class));
        startActivity(new Intent(context, RequestPermissionActivity.class));
//        startActivity(new Intent(context, RollingAnimActivity.class));
//        startActivity(new Intent(context, NotiAccessActivity.class));
//        startActivity(new Intent(context, ViewStubWithAnimActivity.class));
//        startActivity(new Intent(context, CamTestActivity.class));
//        startActivity(new Intent(context, GuideActivity.class));
//        startActivity(new Intent(context, TransparentActivity.class));
//        finish();
    }

    private void putAnimationSet() {
    }

//    private void setAnimationDefault(Animation animation) {
//        int durationMillis = 800;
//        animation.setDuration(durationMillis);
//        animation.setFillAfter(true);
//        animation.setInterpolator(new AccelerateInterpolator());
//    }


    /**
     * desc: 加载recycler View
     */
    private void loadRecyclerView() {


        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        // not fixed height? set false
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new MyRecyclerViewAdapter(context);
        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.addItemDecoration(new HorizontalSpacesItemDecoration(PageRecyclerView.pageMargin));
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.ce.game.myapplication/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.ce.game.myapplication/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}
