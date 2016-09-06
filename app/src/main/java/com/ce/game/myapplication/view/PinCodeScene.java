package com.ce.game.myapplication.view;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.AttributeSet;
import android.view.animation.Animation;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.ce.game.myapplication.AnimatorU;
import com.ce.game.myapplication.R;
import com.ce.game.myapplication.util.ViewU;

/**
 * Created by KyleCe on 2016/5/23.
 *
 * @author: KyleCe
 */
public class PinCodeScene extends FrameLayout implements PinCodeSceneControl {

    protected Context mContext;

    ShowcasePinCode mShowcasePinCode;

    FrameLayout mParent;

    private static final int PREPARE = 99;
    private static final int START_MYSELF_ANIM = 100;
    private static final int START_LOVER_ANIM = 101;
    private static final int PRESS = 102;
    private static final int SHOW_MYSELF_BG = 103;
    private static final int SHOW_LOVER_BG = 104;
    private static final int COMPLETE = 105;

    protected final long DEFAULT_PREPARE_DELAY = 100;
    protected final long DEFAULT_PRESS_DELAY = 500;
    protected final long DEFAULT_DESKTOP_SHOWING_DURATION = 1500;

    CompleteCallback mCompleteCallback = CompleteCallback.NULL;

    TextView mSetPinCodeText;

    int[] mMyselfPinArray = {2, 5, 8, 0,};

    int[] mLoverPinArray = {1, 5, 9, 0,};

    public PinCodeScene(final Context context) {
        this(context, null);
    }

    public PinCodeScene(final Context context, final AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PinCodeScene(final Context context, final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mContext = context;

        inflate(context, R.layout.first_anim_pincode_content, this);

        setBackgroundColor(Color.TRANSPARENT);

        mShowcasePinCode = (ShowcasePinCode) findViewById(R.id.showcase_pin_code);

        mParent = (FrameLayout) findViewById(R.id.parent);

        mSetPinCodeText = (TextView) findViewById(R.id.set_pin_code_text);
    }

    Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {
                case PREPARE:
                    mShowcasePinCode.onPrepare();
                    mShowcasePinCode.onScale(.8f);
                    break;
                case PRESS:
                    int key = msg.arg1;
                    onClickButton(key);
                    break;
                case START_MYSELF_ANIM:
                    startAnimation(mMyselfPinArray);

                    mShowcasePinCode.getWidth();
                    mShowcasePinCode.getHeight();

                    sendEmptyMessageDelayed(SHOW_MYSELF_BG, (mMyselfPinArray.length + 1) * DEFAULT_PRESS_DELAY);
                    break;
                case SHOW_MYSELF_BG:
                    setBackgroundResource(R.drawable.first_anim_myself_desktop);
                    AnimatorU.alphaOut(mShowcasePinCode, DEFAULT_PRESS_DELAY);
                    ViewU.hide(mShowcasePinCode);

                    sendEmptyMessageDelayed(START_LOVER_ANIM, DEFAULT_PRESS_DELAY + DEFAULT_DESKTOP_SHOWING_DURATION);
                    break;
                case START_LOVER_ANIM:
                    mShowcasePinCode.setBackgroundColor(getResources().getColor(R.color.guide_view_5_lover));
                    AnimatorU.alphaIn(mShowcasePinCode);
                    startAnimation(mLoverPinArray);

                    sendEmptyMessageDelayed(SHOW_LOVER_BG, (mLoverPinArray.length + 1) * DEFAULT_PRESS_DELAY);
                    break;
                case SHOW_LOVER_BG:
                    setBackgroundResource(R.drawable.first_anim_lover_desktop);
                    AnimatorU.alphaOut(mShowcasePinCode, DEFAULT_PRESS_DELAY);
                    ViewU.hide(mShowcasePinCode);

                    sendEmptyMessageDelayed(COMPLETE, DEFAULT_PRESS_DELAY + DEFAULT_DESKTOP_SHOWING_DURATION);
                    break;
                case COMPLETE:
                    AnimatorU.alphaOut(mParent, DEFAULT_PRESS_DELAY, new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            setBackgroundResource(R.drawable.first_anim_complete_desktop);

                            ViewU.show(mSetPinCodeText);
                            AnimatorU.alphaIn(mSetPinCodeText);
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });
                    mCompleteCallback.onComplete();
                    break;
                default:
                    break;
            }
        }

        private void startAnimation(int[] array) {
            mShowcasePinCode.onReset();
            int size = array.length;
            Message tempMessage;
            for (int i = 0; i < size; i++) {
                tempMessage = new Message();
                tempMessage.what = PRESS;
                tempMessage.arg1 = array[i];
                sendMessageDelayed(tempMessage, (i + 1) * DEFAULT_PRESS_DELAY);
            }
        }
    };

    protected void onClickButton(int keyType) {
        if (keyType < 0) return;
        mShowcasePinCode.onNumberClick(keyType);
    }

    public void setCompleteCallback(CompleteCallback completeCallback) {
        mCompleteCallback = completeCallback;
    }

    @Override
    public void onPrepare() {
        mHandler.sendEmptyMessage(PREPARE);
    }

    @Override
    public void onStartAnim() {
        mHandler.sendEmptyMessageDelayed(START_MYSELF_ANIM, DEFAULT_PREPARE_DELAY);
    }
}
