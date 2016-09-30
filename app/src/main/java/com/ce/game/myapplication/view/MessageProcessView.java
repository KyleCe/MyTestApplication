package com.ce.game.myapplication.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.ce.game.myapplication.AnimatorU;
import com.ce.game.myapplication.BuildConfig;
import com.ce.game.myapplication.R;
import com.ce.game.myapplication.util.ButtonClickCallback;
import com.ce.game.myapplication.util.DU;
import com.ce.game.myapplication.util.MessageProcessViewInterface;
import com.ce.game.myapplication.util.RefreshUiStatusCallbackWithArg;
import com.ce.game.myapplication.util.ViewU;

/**
 * Created by KyleCe on 2016/8/10.
 *
 * @author: KyleCe
 */

public class MessageProcessView extends FrameLayout implements MessageProcessViewInterface,RefreshUiStatusCallbackWithArg {

    private static final String TAG = MessageProcessView.class.getSimpleName();
    protected CircleProgressAlwaysScanningView mCircleProgressScanView;
    protected TextView mDescriptionContent;
    protected TextView mDescriptionTile;
    protected TextView mButtonToClick;
    protected ButtonClickCallback mClickCallback = ButtonClickCallback.NULL;
    protected boolean mBackPressable = false;
    protected Context mContext;

    public MessageProcessView(Context context) {
        this(context, null);
    }

    public MessageProcessView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MessageProcessView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        inflate(context, R.layout.message_processing_view_layout, this);

        init(context);
    }

    protected void init(Context context) {
        mContext = context.getApplicationContext();

        mCircleProgressScanView = (CircleProgressAlwaysScanningView) findViewById(R.id.progress_indicator);
        mDescriptionTile = (TextView) findViewById(R.id.process_result_description_title);
        mDescriptionContent = (TextView) findViewById(R.id.process_result_description_content);
        mButtonToClick = (TextView) findViewById(R.id.try_now);

        mButtonToClick.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mClickCallback.onOk();
            }
        });

        mCircleProgressScanView.setCompleteCallback(new CompleteCallback() {
            @Override
            public void onComplete() {
                endAffairs();
            }
        });

        mCircleProgressScanView.setRefreshUiStatusCallback(this);

        if (BuildConfig.DEBUG) {
            setAlpha(.8f);
            TextView debugText = new TextView(mContext);
            debugText.setTextSize(20);
            debugText.setText("alpha only in debug mode");
            LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            lp.gravity= Gravity.BOTTOM|Gravity.RIGHT;
            debugText.setLayoutParams(lp);
            addView(debugText);
        }
    }

    public void setClickCallback(ButtonClickCallback clickCallback) {
        mClickCallback = clickCallback;
    }

    @Override
    public void onStartProcess(int progress) {
        onUpdateProcess(progress);

        mBackPressable = false;
        mDescriptionContent.setText(getResources().getString(R.string.privacy_process_result_content));
        ViewU.hide(mButtonToClick, mDescriptionContent);
    }

    @Override
    public void onSetStartEndProcess(int startProgress, int endProcess) {
        mCircleProgressScanView.setCurrentProgress(startProgress);
        mCircleProgressScanView.setTargetProgress(endProcess);
    }

    @Override
    public void onStartScanAnimation() {
        mCircleProgressScanView.startScanAnimation();
    }

    @Override
    public void onUpdateProcess(int progress) {
        DU.pwa(TAG,"update progress:",progress);
        mCircleProgressScanView.onIncreaseProgress(progress - mCircleProgressScanView.getCurrentProgress());
    }

    @Override
    public void onUpdateDescription(String description) {

    }

    @Override
    public void onEndProcess(int progress) {
        DU.pwa(TAG,"end progress:",progress);
        mCircleProgressScanView.endingProcess();
        endAffairs();
    }

    @Override
    public void onShowUpResultPage(int resultProgress, String resultTitle, String resultDescription
            , String buttonContent, final Runnable buttonClickCallback) {
        if (CircleProcessSlaver.isProgressValueValid(resultProgress))
            mCircleProgressScanView.setProgressWithAnimation(resultProgress);

        updateTextViewText(mDescriptionTile, resultTitle);

        updateTextViewText(mDescriptionContent, resultDescription);

        updateTextViewText(mButtonToClick, buttonContent);

        ViewU.show(mDescriptionTile, mDescriptionContent, mButtonToClick);

        ViewU.setClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (buttonClickCallback != null)
                    buttonClickCallback.run();
            }
        }, mButtonToClick);
    }

    private void updateTextViewText(TextView targetView, String targetText) {
        if (targetText == null || targetView == null) {
            ViewU.hide(targetView);
            return;
        }

        targetView.setText(targetText);
    }

    private void endAffairs() {
        if (mCircleProgressScanView.getProgress() != CircleProcessSlaver.DEFAULT_COMPLETE_PROCESS)
            return;

        mDescriptionContent.setText(getResources().getString(R.string.privacy_process_result_content_second));
        ViewU.show(mButtonToClick, mDescriptionContent);
        AnimatorU.alphaIn(mButtonToClick);
        AnimatorU.alphaIn(mDescriptionContent);
        mBackPressable = true;
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) backPressed();

        return super.dispatchKeyEvent(event);
    }

    public void backPressed() {
        if (mBackPressable) mClickCallback.onCancel();
    }

    @Override
    public void onRefreshUi(Object obj) {
        if(!(obj instanceof Integer)) return;
        int progress = (int) obj;
        DU.pwa(TAG, "adjust bg progress:", progress);
        setBackgroundColor(new CircleProcessSlaver(mContext).parseColorToDraw(progress)[1]);
        invalidate();
    }
}
