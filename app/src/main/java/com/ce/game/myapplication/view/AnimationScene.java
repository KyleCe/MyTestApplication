package com.ce.game.myapplication.view;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.Animation;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ce.game.myapplication.AnimatorU;
import com.ce.game.myapplication.R;
import com.ce.game.myapplication.util.DisplayUtil;
import com.ce.game.myapplication.util.FontMaster;
import com.ce.game.myapplication.util.ViewU;

/**
 * Created by KyleCe on 2016/9/2.
 *
 * @author: KyleCe
 */

public class AnimationScene extends FrameLayout implements AnimationSceneInterface {

    Context mContext;

    private static final int START_ANIMATION = 0;
    private static final int TRANSFER_TO_PIN_CODE_CUT = 1;
    private static final int BUTTON_TO_START = 2;
    private static final int PREPARE = 3;
    private static final int PIN_CODE_SCENE_COMPLETE = 4;
    private static final int LAST_OK_BUTTON_CLICKED = 5;
    private static final int HOLD_ON_BEFORE_TRANSFORM_TO_PIN_CODE = 6;

    private static final int SHOW = 714;

    protected final long DEFAULT_MOVING_DURATION = 500;
    protected final long DEFAULT_ALPHA_DURATION = 500;
    protected final long DEFAULT_FINISH_MERGE_SNAP_DURATION = 1000;

    final float DEFAULT_DESCRIPTION_UNDER_PHONE_LARGER_SIZE_SP = 22.5f;

    protected CompleteCallback mCompleteCallback = CompleteCallback.NULL;

    final float HOLDING_RATIO = .435f;

    PhoneViewWithText mGuestPhone;
    PhoneViewWithText mColleaguePhone;
    PhoneViewWithList mMyselfPhone;
    PhoneViewWithText mLoverPhone;
    int COLLEAGUE_INDEX = 2;

    LinearLayout mSceneArea;

    RelativeLayout mCenterParent;

    protected TextView mTextDescription;
    protected TextView mTextButton;
    protected TextView mTextTermsOfService;
    protected TextView mTextPrivacy;
    protected View mTextLastLineParent;

    // must use this trick, the animate() api has a bug @ 2016-9-2 18:25:27
    protected int mGuestToX;
    protected int mGuestToY;

    protected FirstAnimationSceneClickCallback mSceneClickCallback = FirstAnimationSceneClickCallback.NULL;

    public AnimationScene(final Context context) {
        this(context, null);
    }

    public AnimationScene(final Context context, final AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AnimationScene(final Context context, final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mContext = context;

        inflate(context, R.layout.first_animation_scene, this);

        init();
    }

    private void init() {

        mSceneArea = (LinearLayout) findViewById(R.id.scene_area);

        mCenterParent = (RelativeLayout) findViewById(R.id.center_parent);

        mTextDescription = (TextView) findViewById(R.id.text_description);
        mTextButton = (TextView) findViewById(R.id.text_button);
        mTextTermsOfService = (TextView) findViewById(R.id.text_term_of_service);
        mTextPrivacy = (TextView) findViewById(R.id.text_privacy);
        mTextLastLineParent = findViewById(R.id.text_last_line_parent);
        mTextTermsOfService.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mSceneClickCallback.onMenuOne();
            }
        });
        mTextPrivacy.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mSceneClickCallback.onMenuTwo();
            }
        });


        mGuestPhone = (PhoneViewWithText) findViewById(R.id.phone_guest);
        mColleaguePhone = (PhoneViewWithText) findViewById(R.id.phone_colleague);
        mMyselfPhone = (PhoneViewWithList) findViewById(R.id.phone_myself);
        mLoverPhone = (PhoneViewWithText) findViewById(R.id.phone_lover);

        mTextButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mHandler.sendEmptyMessage(BUTTON_TO_START);
            }
        });

        FontMaster.with(getContext()).font(FontMaster.Type.Kautiva).set(mTextDescription);
    }

    public void setCompleteCallback(CompleteCallback completeCallback) {
        mCompleteCallback = completeCallback;
    }

    public void setSceneClickCallback(FirstAnimationSceneClickCallback sceneClickCallback) {
        mSceneClickCallback = sceneClickCallback;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        mHandler.sendEmptyMessage(PREPARE);
    }

    Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case PREPARE:
                    int containerHeight = mCenterParent.getMeasuredHeight();
                    customPhone(mGuestPhone, 3, containerHeight);
                    customPhone(mColleaguePhone, 2, containerHeight);
                    customPhone(mLoverPhone, 1, containerHeight);
                    customPhone(mMyselfPhone, 0, containerHeight);
                    break;
                case BUTTON_TO_START:
                    sendEmptyMessage(START_ANIMATION);
                    break;
                case START_ANIMATION:
                    AnimatorU.alphaOut(mTextButton, DEFAULT_ALPHA_DURATION, new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                            mTextButton.setClickable(false);
                            mTextDescription.setTextSize(TypedValue.COMPLEX_UNIT_SP, DEFAULT_DESCRIPTION_UNDER_PHONE_LARGER_SIZE_SP);

                            ViewU.hideAndShow(mTextButton, mTextDescription);
                            startAnimationOperation();
                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });
                    break;
                case SHOW:
                    float scaleX = (float) mCenterParent.getWidth() / mMyselfPhone.getWidth();
                    float scaleY = (float) mCenterParent.getHeight() / mMyselfPhone.getHeight();

                    float scale = Math.min(scaleX, scaleY);

                    mMyselfPhone.animate().scaleX(scale).scaleY(scale)
                            .withStartAction(new Runnable() {
                                @Override
                                public void run() {
                                    mMyselfPhone.getListContentParent().setBackgroundColor(Color.WHITE);
                                }
                            })
                            .withEndAction(new Runnable() {
                                @Override
                                public void run() {
                                    mHandler.sendEmptyMessageDelayed(HOLD_ON_BEFORE_TRANSFORM_TO_PIN_CODE,DEFAULT_FINISH_MERGE_SNAP_DURATION);
                                }
                            })
                            .setDuration(DEFAULT_MOVING_DURATION).start();
                    break;
                case HOLD_ON_BEFORE_TRANSFORM_TO_PIN_CODE:
                    mHandler.sendEmptyMessage(TRANSFER_TO_PIN_CODE_CUT);

                    AnimatorU.alphaOut(mTextDescription);
                    mMyselfPhone.mPinCodeScene.onPrepare();
                    mTextDescription.setText(mContext.getResources().getString(R.string.guide_view_5_text_below_phone_second));
                    AnimatorU.alphaIn(mTextDescription);
                    break;
                case TRANSFER_TO_PIN_CODE_CUT:

                    AnimatorU.alphaIn(mMyselfPhone.mPinCodeScene, DEFAULT_ALPHA_DURATION, new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            ViewU.hide(mMyselfPhone.mRecyclerView);

                            mMyselfPhone.mPinCodeScene.setCompleteCallback(new CompleteCallback() {
                                @Override
                                public void onComplete() {
                                    sendEmptyMessage(PIN_CODE_SCENE_COMPLETE);
                                }
                            });
                            mMyselfPhone.mPinCodeScene.onStartAnim();
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });

                    break;
                case PIN_CODE_SCENE_COMPLETE:
                    AnimatorU.alphaOut(mTextDescription, DEFAULT_ALPHA_DURATION, new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {
                            mTextButton.setText(getResources().getString(R.string.guide_view_5_let_go_button));
                            mTextButton.setOnClickListener(new OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    mSceneClickCallback.onOk();
                                }
                            });
                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            ViewU.hide(mTextDescription);

                            mTextButton.setClickable(true);
                            DisplayUtil.setMargin(mTextButton
                                    , getResources().getDimensionPixelSize(R.dimen.small_phone_lets_go_button_margin_x_alias)
                                    , 0
                                    , getResources().getDimensionPixelSize(R.dimen.small_phone_lets_go_button_margin_x_alias)
                                    , getResources().getDimensionPixelSize(R.dimen.small_phone_lets_go_button_margin_bottom_second_scene));
                            mTextButton.invalidate();

                            AnimatorU.alphaIn(mTextButton, DEFAULT_ALPHA_DURATION, new Animation.AnimationListener() {
                                @Override
                                public void onAnimationStart(Animation animation) {

                                }

                                @Override
                                public void onAnimationEnd(Animation animation) {
                                    AnimatorU.alphaIn(mTextLastLineParent);
                                    ViewU.show(mTextLastLineParent);
                                }

                                @Override
                                public void onAnimationRepeat(Animation animation) {

                                }
                            });
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });
                    break;
                default:
                    break;
            }
        }

        private void startAnimationOperation() {
            final int toX = (mSceneArea.getWidth() - mMyselfPhone.getWidth()) >> 1;
            mMyselfPhone.animate().translationX(toX)
                    .withStartAction(new Runnable() {
                        @Override
                        public void run() {
                            mLoverPhone.animate().translationX(-toX)
                                    .setDuration(DEFAULT_MOVING_DURATION).start();
                        }
                    })
                    .withEndAction(new Runnable() {
                        @Override
                        public void run() {
                            ViewU.hide(mLoverPhone);
                            mMyselfPhone.showItemCount(1);

                            final int toX = (mColleaguePhone.getRight() - mMyselfPhone.getLeft() - mMyselfPhone.getWidth()) >> 1;
                            final int toY = (mMyselfPhone.getBottom() - mColleaguePhone.getTop() - mMyselfPhone.getHeight()) >> 1;

                            mGuestToX = toX;
                            mGuestToY = toY;

                            mMyselfPhone.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    mMyselfPhone.animate()
                                            .translationY(-toY)
                                            .withStartAction(new Runnable() {
                                                @Override
                                                public void run() {
                                                    mColleaguePhone.animate()
                                                            .translationX(-toX)
                                                            .translationY(toY)
                                                            .setDuration(DEFAULT_MOVING_DURATION).start();
                                                }
                                            })
                                            .withEndAction(new Runnable() {
                                                @Override
                                                public void run() {
                                                    ViewU.hide(mColleaguePhone);
                                                    mMyselfPhone.showItemCount(2);

                                                    mMyselfPhone.postDelayed(new Runnable() {
                                                        @Override
                                                        public void run() {

                                                            mGuestPhone.animate()
                                                                    .translationX(mGuestToX)
                                                                    .translationY(mGuestToY)
                                                                    .withEndAction(new Runnable() {
                                                                        @Override
                                                                        public void run() {
                                                                            ViewU.hide(mGuestPhone);
                                                                            mMyselfPhone.showItemCount(3);

                                                                            mHandler.sendEmptyMessageDelayed(SHOW, mMyselfPhone.getAnimationDuration());
                                                                        }
                                                                    })
                                                                    .setDuration(DEFAULT_MOVING_DURATION).start();
                                                        }
                                                    }, mMyselfPhone.getAnimationDuration());

                                                }
                                            })
                                            .setDuration(DEFAULT_MOVING_DURATION).start();
                                }
                            }, mMyselfPhone.getAnimationDuration());

                        }
                    })
                    .setDuration(DEFAULT_MOVING_DURATION).start();
        }
    };

    private <P extends PhoneViewWithText> void customPhone(P p, int i, int containerH) {
        setAsSmallPhone(containerH, p);
        p.setContent(PhoneView.PHONE_ROLE_ARRAY[i]);
        p.setBgColor(PhoneView.PHONE_BG_ARRAY[i]);

        if (i == COLLEAGUE_INDEX)
            p.setContentSizeUnitSp(PhoneViewWithText.DEFAULT_MINIMUM_TEXT_SIZE_SP);
    }

    private <P extends PhoneView> void setAsSmallPhone(int containerH, P... ps) {
        float targetH = containerH * HOLDING_RATIO;
        float ratio = targetH / getResources().getDimensionPixelOffset(R.dimen.small_phone_height);

        for (P p : ps)
            p.mFrameParent.setLayoutParams(new LayoutParams(
                    (int) (getResources().getDimensionPixelOffset(R.dimen.small_phone_width) * ratio)
                    , (int) (getResources().getDimensionPixelOffset(R.dimen.small_phone_height) * ratio)
            ));
    }

    @Override
    public void onStartPlay() {
        mHandler.sendEmptyMessage(START_ANIMATION);
    }

    @Override
    public void onEndPlay() {

    }

}
