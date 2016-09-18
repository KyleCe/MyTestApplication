package com.ce.game.myapplication.view.pincode;

import android.content.Context;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.IntDef;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewStub;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ce.game.myapplication.R;
import com.ce.game.myapplication.connectwithgoogle.RetrievePasswordView;
import com.ce.game.myapplication.connectwithgoogle.VerifyCallback;
import com.ce.game.myapplication.userguideanim.GuideViewInterface;
import com.ce.game.myapplication.util.ViewU;
import com.ce.game.myapplication.view.NumberKeyboardSingleButton;
import com.ce.game.myapplication.view.newpincode.NumberKeyboardForUnlock;
import com.ce.game.myapplication.view.newpincode.PinCodeAdapter;
import com.ce.game.myapplication.view.newpincode.PressCallback;

/**
 * Created by KyleCe on 2016/5/25.
 *
 * @author: KyleCe
 */
public class PinCodeView extends RelativeLayout implements View.OnTouchListener
        , KeyboardButtonClickedListener, LockViewInterface.Clickable
        , LockViewInterface.ResetPinCodeView, PressCallback {

    private Context mContext;


    private static final int DEFAULT_PIN_LENGTH = Const.DEFAULT_PIN_CODE_LEN;

    protected TextView mPasswordHint;
    protected PinCodeRoundView mPinCodeRoundView;

    protected ResetTextView mResetPasswordEntrance;

    private ViewStub mRetrieveStub;

    public View mRetrieveView;

    protected volatile int mAttemptCount = 0;

    private static final int ATTEMPT_TIMES_ALLOWED = 5;

    protected volatile String mPinCode;

    protected volatile String mOldPinCode;

    private volatile int mPinLength;

    private UnlockInterface mUnlockRuler;

    private ChangeMyselfPassword mChangeMyselfPassword;

    private String mDefaultHint;

    private TranslateAnimation mLeftRightAnimation;

    float mTranslateCellDistance = 0.05f;

    private float[][] mTranAnimPoints = new float[][]{
            {0, -mTranslateCellDistance},
            {-mTranslateCellDistance, mTranslateCellDistance},
            {mTranslateCellDistance, 0}
    };

    protected static int CELL_DURATION = 123;

    private CountDownTimer mCountDownTimer;
    private static final int COUNT_DOWN_MILLISECONDS = 31000;
    private static final int COUNT_DOWN_INTERNAL = 1000;

    private volatile boolean mClickable = true;

    private volatile boolean mRetrieveModeEnable = false;

    private volatile String mFirstEnterRetrievePassword = "";

    @IntDef({ResetType.INCORRECT, ResetType.GENERAL_ACCOUNT, ResetType.CLEAR_INPUT, ResetType.GUEST
            , ResetType.RETRIEVE_OK})
    public @interface ResetType {
        int CLEAR_INPUT = 0;
        int INCORRECT = 1;
        int GENERAL_ACCOUNT = 2;
        int GUEST = 3;
        int RETRIEVE_OK = 4;
    }

    public PinCodeView(Context context) {
        this(context, null);
    }

    public PinCodeView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PinCodeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        this.mContext = context.getApplicationContext();
        initializeView();
    }

    private void initializeView() {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        inflater.inflate(R.layout.password_pin_code, this);

        setUpView();
    }

    public interface UnlockInterface {
        void onUnlock(String password);

        void onBack();
    }

    public interface ChangeMyselfPassword {
        boolean isAvailable(String password);

        void onChange(String newPassword);
    }

    public void assignUnlockInterface(UnlockInterface unlockInterface) {
        mUnlockRuler = unlockInterface;
    }

    public void assignChangeMyselfPassword(ChangeMyselfPassword changeMyselfPassword) {
        mChangeMyselfPassword = changeMyselfPassword;
    }

    private void setUpView() {
        mPinLength = DEFAULT_PIN_LENGTH;

        mPinCode = "";
        mOldPinCode = "";

        mPinCodeRoundView = (PinCodeRoundView) this.findViewById(R.id.pin_code_round_view);
        mPinCodeRoundView.setPinLength(this.getPinLength());
        mPasswordHint = (TextView) this.findViewById(R.id.pin_code_password_hint);
        mPasswordHint.setOnTouchListener(this);
//        ((KeyboardView) this.findViewById(R.id.pin_code_keyboard_view)).setKeyboardButtonClickedListener(this);
//        ((KeyboardView) this.findViewById(R.id.pin_code_keyboard_view)).assignClickableController(this);

        ((NumberKeyboardForUnlock) findViewById(R.id.new_keyboard)).setPressCallback(this);

        mDefaultHint = mContext.getString(R.string.pin_code_password_default_hint);

        mCountDownTimer = new CountDownTimer(COUNT_DOWN_MILLISECONDS, COUNT_DOWN_INTERNAL) {
            @Override
            public void onTick(long millisUntilFinished) {
                int seconds = (int) millisUntilFinished / 1000;

                mPasswordHint.setText(String.format(getResources().getConfiguration().locale,
                        getResources().getString(R.string.pin_code_retry_after_prefix), seconds)
                        + (seconds <= 1 ?
                        getResources().getString(R.string.pin_code_retry_after_suffix_for_one_second) :
                        getResources().getString(R.string.pin_code_retry_after_suffix)));
            }

            @Override
            public void onFinish() {
                resetAttemptCount();
                mResetPasswordEntrance.assignStateTrigger(ResetTextView.Trigger.toVerify);
            }
        };

        mResetPasswordEntrance = (ResetTextView) findViewById(R.id.pin_code_reset_password);
        mResetPasswordEntrance.attachVerifyAndReenterClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyClick();
            }
        }, new OnClickListener() {
            @Override
            public void onClick(View v) {
                reenterClick();
            }
        });
        mResetPasswordEntrance.assignStateTrigger(ResetTextView.Trigger.toVerify);

        mRetrieveStub = (ViewStub) findViewById(R.id.retrieve_password_stub);
    }

    private RetrievePasswordView mRetrievePasswordView;

    private void verifyClick() {
        if (!PhoneStatusU.isNetworkAvailable(mContext)) {
            mPasswordHint.setText(getResources().getString(R.string.retrieve_password_network_down));
            return;
        }

        if (mRetrieveView == null) mRetrieveView = mRetrieveStub.inflate();

        if (mRetrievePasswordView == null) {
            mRetrievePasswordView = (RetrievePasswordView) mRetrieveView.findViewById(R.id.retrieve_password_view);
            mRetrievePasswordView.attachKeyEventCallback(new GuideViewInterface.KeyEventCallback() {
                @Override
                public void onBackPressed() {
                    clearView();
                }
            });

            mRetrievePasswordView.attachVerifyCallback(new VerifyCallback() {
                @Override
                public void cancel() {
                    clearView();
                }

                @Override
                public void bingo() {
                    mHandler.sendEmptyMessage(RETRIEVE_PASSWORD_VERIFY_OK);
                }
            });
        }

//        mRetrievePasswordView.checkIfHasNoAvailableAccount();

        ViewU.show(mRetrieveView);
    }

    private static final int RETRIEVE_PASSWORD_VERIFY_OK = 427;

    private Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {
                case RETRIEVE_PASSWORD_VERIFY_OK:
                    clearView();
                    enterRetrieveMode();

                    mResetPasswordEntrance.assignStateTrigger(ResetTextView.Trigger.toFirstInput);

                    mRetrievePasswordView.resetToInitializedState();
                    break;
                default:
                    break;
            }
        }
    };

    private void reenterClick() {
        mResetPasswordEntrance.assignStateTrigger(ResetTextView.Trigger.toFirstInput);
        mFirstEnterRetrievePassword = "";
        mPasswordHint.setText(getResources().getString(R.string.retrieve_password_set_password));
        emptyPinCode();
    }

    private void enterRetrieveMode() {
        mRetrieveModeEnable = true;
        mFirstEnterRetrievePassword = "";
        mPasswordHint.setText(getResources().getString(R.string.retrieve_password_set_password));
    }

    private void clearView() {
        ViewU.hide(mRetrieveView);
        mRetrieveView.setAlpha(1);

        emptyPinCode();
    }

    @Override
    public void reset(@ResetType int type) {
        emptyPinCode();

        String hint = "";

        exitRetrievePasswordMode();

        switch (type) {
            case ResetType.INCORRECT:
                if (mAttemptCount == ATTEMPT_TIMES_ALLOWED) {
                    mClickable = false;
                    mCountDownTimer.start();
                    mResetPasswordEntrance.assignStateTrigger(ResetTextView.Trigger.toHide);
                } else
                    hint = mContext.getString(R.string.pin_code_password_incorrect);
                break;
            case ResetType.GENERAL_ACCOUNT:
                resetAttemptCount();
                break;
            case ResetType.CLEAR_INPUT:
                hideRetrievePasswordEntrance();
                break;
            case ResetType.GUEST:
                return;
            case ResetType.RETRIEVE_OK:
                return;
            default:
                break;
        }

        if (attemptNotReachLimit()) setHintAndShow(hint);

        if (shouldAnimPinCodeRoundView(hint)) {
            startLeftToRightAnimResponse(mPinCodeRoundView);
            mResetPasswordEntrance.assignStateTrigger(ResetTextView.Trigger.toVerify);
            ViewU.show(mResetPasswordEntrance);
        }
    }

    protected void emptyPinCode() {
        mPinCode = "";
        mOldPinCode = "";

        mPinCodeRoundView.refresh(0);
    }

    private void exitRetrievePasswordMode() {
        mRetrieveModeEnable = false;
        mFirstEnterRetrievePassword = "";
        mResetPasswordEntrance.assignStateTrigger(ResetTextView.Trigger.toVerify);
    }

    private void hideRetrievePasswordEntrance() {
        ViewU.invisible(mResetPasswordEntrance);
    }

    public void resetAttemptCount() {
        mPasswordHint.setText("");
        mClickable = true;
        mAttemptCount = 0;
        mCountDownTimer.cancel();

        hideRetrievePasswordEntrance();
    }

    private boolean shouldAnimPinCodeRoundView(String hint) {
        return hint.equals(mContext.getString(R.string.pin_code_password_incorrect)) && attemptNotReachLimit();
    }

    protected boolean attemptNotReachLimit() {
        return mAttemptCount < ATTEMPT_TIMES_ALLOWED;
    }

    protected void startLeftToRightAnimResponse(final View view) {

        if (mLeftRightAnimation == null) {
            mLeftRightAnimation = getTranAnim(mTranAnimPoints[0][0], mTranAnimPoints[0][1]);
            mLeftRightAnimation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {

                    TranslateAnimation secondAnim = getTranAnim(mTranAnimPoints[1][0], mTranAnimPoints[1][1]);
                    secondAnim.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            TranslateAnimation thirdAnim = getTranAnim(mTranAnimPoints[2][0], mTranAnimPoints[2][1]);
                            thirdAnim.setDuration(CELL_DURATION);
                            view.startAnimation(thirdAnim);
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });
                    secondAnim.setDuration(CELL_DURATION << 1);
                    secondAnim.setInterpolator(new LinearInterpolator());
                    view.startAnimation(secondAnim);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            mLeftRightAnimation.setDuration(CELL_DURATION);
        }

        view.startAnimation(mLeftRightAnimation);
    }

    protected TranslateAnimation getTranAnim(float startX, float startY) {
        return new TranslateAnimation(Animation.RELATIVE_TO_SELF, startX,
                Animation.RELATIVE_TO_SELF, startY, Animation.RELATIVE_TO_SELF,
                0, Animation.RELATIVE_TO_SELF, 0);
    }

    private void setToDefaultOrHideThisDepends(String defaultHint) {
        mPasswordHint.setText(defaultHint);
        ViewU.invisible(mPasswordHint);
    }

    private void setHintAndShow(String hint) {
        mPasswordHint.setText(hint);
        ViewU.show(mPasswordHint);
    }

    @Override
    public void onPress(@NumberKeyboardSingleButton.ButtonContent String key) {
        onKeyboardClick(PinCodeAdapter.convertToOld(key));
        onRippleAnimationEnd();
    }

    @Override
    public void onKeyboardClick(@KeyboardButtonView.KeyType int keyType) {
        if (mPinCode.length() >= this.getPinLength()) return;

        if (keyType == KeyboardButtonView.KeyType.K_BACKSPACE)
            setPinCode(!mPinCode.isEmpty() ? mPinCode.substring(0, mPinCode.length() - 1) : "");
        else if (keyType == KeyboardButtonView.KeyType.K_BACK) mUnlockRuler.onBack();
        else setPinCode(mPinCode + keyType);
    }

    @Override
    public void onRippleAnimationEnd() {
        if (mPinCode.length() < getPinLength()) return;
        else if (mPinCode.length() > getPinLength())
            mPinCode = mPinCode.substring(0, getPinLength() - 1);

        if (mAttemptCount > 0 && !mRetrieveModeEnable) {
            ViewU.show(mResetPasswordEntrance);
        }

        if (!mRetrieveModeEnable) {
            mUnlockRuler.onUnlock(mPinCode);
            mAttemptCount++;

            if (mAttemptCount > ATTEMPT_TIMES_ALLOWED) mAttemptCount = ATTEMPT_TIMES_ALLOWED;
        } else {
            if (TextUtils.isEmpty(mFirstEnterRetrievePassword)) {
                boolean newPasswordAvailable = mChangeMyselfPassword.isAvailable(mPinCode);
                if (!newPasswordAvailable) {
                    mFirstEnterRetrievePassword = "";
                    mPasswordHint.setText(getResources().getString(R.string.retrieve_password_already_exist));
                    mResetPasswordEntrance.assignStateTrigger(ResetTextView.Trigger.toFirstInput);
                } else {
                    mFirstEnterRetrievePassword = mPinCode;
                    mPasswordHint.setText(getResources().getString(R.string.retrieve_password_reenter_password));
                    mResetPasswordEntrance.assignStateTrigger(ResetTextView.Trigger.toReenter);
                }
            } else {
                if (mFirstEnterRetrievePassword.equals(mPinCode)) {
                    mChangeMyselfPassword.onChange(mPinCode);
                    mUnlockRuler.onUnlock(mPinCode);
                    hideRetrievePasswordEntrance();
                    mFirstEnterRetrievePassword = "";
                    exitRetrievePasswordMode();
                } else {
                    startLeftToRightAnimResponse(mPinCodeRoundView);
                    mPasswordHint.setText(getResources().getString(R.string.retrieve_password_do_not_match));
                    mFirstEnterRetrievePassword = "";
                    mResetPasswordEntrance.assignStateTrigger(ResetTextView.Trigger.toFirstInput);
                }
            }
            emptyPinCode();
        }

    }

    public void setPinCode(String pinCode) {
        mPinCode = pinCode;
        mPinCodeRoundView.refresh(mPinCode.length());
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {

        switch (v.getId()) {
            case R.id.pin_code_password_hint:

                break;
            default:
                break;
        }

        return false;
    }

    public int getPinLength() {
        return mPinLength;
    }

    @Override
    public boolean clickable() {
        return mClickable;
    }
}
