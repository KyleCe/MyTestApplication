package com.ce.game.myapplication;

import android.app.Activity;
import android.content.Context;
import android.graphics.PixelFormat;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Looper;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

public class PlayVideoActivity extends Activity
        implements
        MediaPlayer.OnPreparedListener, MediaController.MediaPlayerControl {

    private Context context;
    private static Activity activity;
    private static VideoView videoView;
    private static FrameLayout videoCard;
    private MediaController mediaController;

    Handler handler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_play_video);

        Bundle bundle = getIntent().getExtras();
        if (bundle == null) {
            finish();
            return;
        }

        final String path = bundle.getString("path");
        if (TextUtils.isEmpty(path)) return;

        activity = this;
        context = PlayVideoActivity.this;

        initVideoViews();
        initController();

        play(path);

        // set window pixel format, or the video play view or blink while creating
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
    }

    public void play(String path) {
        if (TextUtils.isEmpty(path)) return;

        videoView.setVideoPath(path);
        videoView.setMediaController(mediaController);
        videoView.start();

        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                finish();
            }
        });
    }

    /**
     * init video view
     */
    private void initVideoViews() {
        videoCard = (FrameLayout) findViewById(R.id.video_play_card);
        videoView = (VideoView) findViewById(R.id.video_view);
    }

    //--MediaPlayerControl methods----------------------------------------------------
    public void start() {
        videoView.start();
    }

    public void pause() {
        videoView.pause();
    }

    public int getDuration() {
        return videoView.getDuration();
    }

    public int getCurrentPosition() {
        return videoView.getCurrentPosition();
    }

    public void seekTo(int i) {
        videoView.seekTo(i);
    }

    public boolean isPlaying() {
        return videoView.isPlaying();
    }

    public int getBufferPercentage() {
        return 0;
    }

    public boolean canPause() {
        return true;
    }

    public boolean canSeekBackward() {
        return true;
    }

    public boolean canSeekForward() {
        return true;
    }

    @Override
    public int getAudioSessionId() {
        return 0;
    }
    //--------------------------------------------------------------------------------


    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        mediaController.setMediaPlayer(this);
        mediaController.setAnchorView(findViewById(R.id.player_anchor));

        handler.post(new Runnable() {
            public void run() {
                mediaController.setEnabled(true);
                mediaController.show();
            }
        });
    }

    // init controller
    private void initController() {
        mediaController = new MediaController(context);
    }

}
