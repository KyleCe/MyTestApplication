package com.ce.game.myapplication.view.pincode;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.annotation.IntDef;
import android.support.annotation.StringRes;
import android.util.AttributeSet;
import android.widget.TextView;

import com.ce.game.myapplication.R;

/**
 * Created by KyleCe on 2016/7/23.
 *
 * @author: KyleCe
 */
public class ResetTextView extends TextView {

    private StateEssentials mVerifyEssentials;
    private StateEssentials mFirstInputEssentials;
    private StateEssentials mReenterEssentials;
    private StateEssentials mHideEssentials;

    private Context mContext;

    private static final int EMPTY_IT = -1;

    @IntDef({Trigger.toVerify, Trigger.toFirstInput, Trigger.toReenter, Trigger.toHide})
    public @interface Trigger {
        int toVerify = 0;
        int toFirstInput = 1;
        int toReenter = 2;
        int toHide = 3;
    }

    public ResetTextView(Context context) {
        this(context, null);
    }

    public ResetTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ResetTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mContext = context.getApplicationContext();

        mVerifyEssentials = new StateEssentials(R.drawable.lockscreen_icon_forgetpassword
                , R.string.retrieve_password_forgot_password, null);
        mFirstInputEssentials = new StateEssentials(EMPTY_IT, EMPTY_IT, null);
        mReenterEssentials = new StateEssentials(R.drawable.lockscreen_icon_reenter_password
                , R.string.retrieve_password_reset_password, null);
        mHideEssentials = new StateEssentials(EMPTY_IT, EMPTY_IT, null);
    }

    public void attachVerifyAndReenterClickListener(OnClickListener verifyClickListener, OnClickListener reenterClickListener) {
        mVerifyEssentials.setListener(verifyClickListener);
        mReenterEssentials.setListener(reenterClickListener);
    }

    public void assignStateTrigger(@Trigger int trigger) {
        switch (trigger) {
            case Trigger.toVerify:
                implementStateEssentials(mContext, mVerifyEssentials);
                break;
            case Trigger.toFirstInput:
                implementStateEssentials(mContext, mFirstInputEssentials);
                break;
            case Trigger.toReenter:
                implementStateEssentials(mContext, mReenterEssentials);
                break;
            case Trigger.toHide:
                implementStateEssentials(mContext, mHideEssentials);
                break;
            default:
                break;
        }
    }

    private void implementStateEssentials(Context context, StateEssentials essentials) {
        if (essentials.mStringResId != EMPTY_IT) setText(essentials.mStringResId);
        else setText("");

        if (essentials.mIconResId != EMPTY_IT)
            setCompoundDrawablesWithIntrinsicBounds(context.getResources().getDrawable(essentials.mIconResId), null, null, null);
        else setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);

        setOnClickListener(essentials.mListener);
    }

    class StateEssentials {
        @DrawableRes
        public int mIconResId;
        @StringRes
        public int mStringResId;
        public OnClickListener mListener;

        public StateEssentials(@DrawableRes int iconResId, @StringRes int stringResId, OnClickListener listener) {
            mIconResId = iconResId;
            mStringResId = stringResId;
            mListener = listener;
        }

        public void setListener(OnClickListener listener) {
            mListener = listener;
        }
    }
}
