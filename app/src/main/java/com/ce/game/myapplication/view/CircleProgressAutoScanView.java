package com.ce.game.myapplication.view;

import android.animation.Animator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.AttributeSet;

/**
 * Created by KyleCe on 2016/8/10.
 *
 * @author: KyleCe
 */
public class CircleProgressAutoScanView extends CircleProgressView implements Handler.Callback {

    protected Handler mHandler;
    private final int START_ANIMATION = 786;
    private int mRepeatCount = 0;
    private int mMaxRepeatCount = 3;
    protected long mRepeatPeriod = 200;
    protected boolean mComplete = false;
    protected int mTargetProgress = 6;
    protected CompleteCallback mScanCompleteCallback = CompleteCallback.NULL;
    protected boolean mShowProgressText = true;
    protected CircleProgressScanLoopCheckingCallback mCheckingCallback = CircleProgressScanLoopCheckingCallback.NULL;
    protected boolean mScanAnimationStarted = false;
    protected boolean mShowScanner = false;
    protected float mCurrentProgress = 0;

    public CircleProgressAutoScanView(Context context) {
        this(context, null);
    }

    public CircleProgressAutoScanView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleProgressAutoScanView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mHandler = new Handler(Looper.getMainLooper(), this);
    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {
        float baseRadius = (getWidth() >> 1) - mPadding - mBoundsWidth;
        float cx = getWidth() >> 1;
        float cy = getHeight() >> 1;
        float alter = (baseRadius - mBoundsWidth) * EDGE_RATIO;

        int deciderProgress = mComplete ? (int) mProgress : (int) mCurrentProgress;

        // background
        mShaderPaint.setStyle(Paint.Style.FILL);
        int[] gradientColor = mCircleProcessSlaver.parseColorToDraw(deciderProgress);
        LinearGradient shader = new LinearGradient(cx - alter, cy - alter, cx + alter, cy + alter,
                gradientColor[0], gradientColor[1], Shader.TileMode.CLAMP);
        mShaderPaint.setShader(shader);

        canvas.drawCircle(cx, cy, (baseRadius - mBoundsWidth), mShaderPaint);

        // the inner board
        mLinePaint.setColor(mSubCircleColor);
        mLinePaint.setStyle(Paint.Style.STROKE);
        mLinePaint.setStrokeWidth(mDefaultStokeWidth);
        canvas.drawCircle(cx, cy, baseRadius * FIRST_SUB_CIRCLE_RATIO, mLinePaint);
        canvas.drawCircle(cx, cy, baseRadius * SECOND_SUB_CIRCLE_RATIO, mLinePaint);
        float crossRadius = baseRadius - mBoundsWidth;
        canvas.drawLine(cx - crossRadius, cy, cx + crossRadius, cy, mLinePaint);
        canvas.drawLine(cx, cy + crossRadius, cx, cy - crossRadius, mLinePaint);

        float endAngle = 3.6f * mProgress;

        // scanner
        if (mShowScanner) {
            SweepGradient gradient = new SweepGradient(cx, cy, mColors4Alpha, null);
            float rotate = endAngle + mStartAngle;
            Matrix gradientMatrix = new Matrix();
            gradientMatrix.preRotate(rotate, cx, cy);
            gradient.setLocalMatrix(gradientMatrix);
            mScanPointerPaint.setStyle(Paint.Style.FILL);
            mScanPointerPaint.setShader(gradient);
            canvas.drawCircle(cx, cy, baseRadius, mScanPointerPaint);
        }

        // progress transparent outer edge
        mLinePaint.setStrokeWidth(mBoundsWidth);
        float boundsRadius = baseRadius - (mBoundsWidth >> 1);
        canvas.drawCircle(cx, cy, boundsRadius, mLinePaint);

        // draw text
        // measure bounds, width and height
        mTextPaint.setColor(mTextColor);
        if (mShowProgressText) {
            int canvasWidth = canvas.getWidth();
            int canvasHeight = canvas.getHeight();
            Rect bounds = new Rect();
            mTextPaint.setTextSize(mTextSize);
            // use "99" instead of true mProgress, avoid num changing position
            if (deciderProgress < 10) mTextPaint.getTextBounds("9", 0, "9".length(), bounds);
            else if (deciderProgress == 100)
                mTextPaint.getTextBounds("000", 0, "000".length(), bounds);
            else mTextPaint.getTextBounds("99", 0, "99".length(), bounds);
            // bounds width and height
            int textWidth = bounds.width();
            int textHeight = bounds.height();
            int startX = (canvasWidth - textWidth) >> 1;
            int startY = (canvasHeight + textHeight) >> 1;
            canvas.drawText(String.valueOf(deciderProgress), startX, startY, mTextPaint);
        }

        // progress circle
        final RectF oval = new RectF();
        oval.set(cx - boundsRadius, cy - boundsRadius, cx + boundsRadius, cy + boundsRadius);
        Path path = new Path();
        path.arcTo(oval, mStartAngle, !mComplete ? 360 - .1f : endAngle, true);
        canvas.drawPath(path, mProgressCirclePaint);

        if (!mComplete || (mComplete && mProgress != 100)) {
            double e_x = cx + boundsRadius * Math.cos(Math.toRadians(endAngle + mStartAngle));
            double e_y = cy + boundsRadius * Math.sin(Math.toRadians(endAngle + mStartAngle));
            canvas.drawCircle((float) e_x, (float) e_y, (mBoundsWidth << 1), mTextPaint);
            mLinePaint.setColor(Color.parseColor("#50ffffff"));
            canvas.drawCircle((float) e_x, (float) e_y, (mBoundsWidth << 1), mLinePaint);
        }
    }

    public void setShowScanner(boolean showScanner) {
        mShowScanner = showScanner;
    }

    public void setRepeatCount(int repeatCount) {
        mRepeatCount = repeatCount;
    }

    public void setCheckingCallback(CircleProgressScanLoopCheckingCallback checkingCallback) {
        mCheckingCallback = checkingCallback;
    }

    public void setShowProgressText(boolean show) {
        mShowProgressText = show;
    }

    public void startScanAnimation() {
        if (!mScanAnimationStarted) {
            mScanAnimationStarted = true;
            mHandler.sendEmptyMessage(START_ANIMATION);
        }
    }

    public void setMaxRepeatCount(int maxRepeatCount) {
        mMaxRepeatCount = maxRepeatCount;
    }

    public void setRepeatPeriod(long repeatPeriod) {
        mRepeatPeriod = repeatPeriod;
    }

    public void setCurrentProgress(int progress) {
        mCurrentProgress = progress;
    }

    public float getCurrentProgress() {
        return mCurrentProgress;
    }

    public void setTargetProgress(int targetProgress) {
        mTargetProgress = targetProgress;
    }

    @Deprecated
    @Override
    public void setCompleteCallback(CompleteCallback completeCallback) {
        mCompleteCallback = CompleteCallback.NULL;
    }

    public void setScanCompleteCallback(CompleteCallback scanCompleteCallback) {
        mScanCompleteCallback = scanCompleteCallback;
    }

    @Override
    public void onSetStartEndProcess(int startProgress, int endProcess) {
        mCurrentProgress = startProgress;
        mEndProcess = endProcess;
    }

    public void onIncreaseProgress(int increment) {
        mCurrentProgress += increment;
        if (mCurrentProgress > CircleProcessSlaver.DEFAULT_COMPLETE_PROCESS)
            mCurrentProgress = CircleProcessSlaver.DEFAULT_COMPLETE_PROCESS;
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        if (hasWindowFocus) startScanAnimation();
        else if (mComplete) mHandler.removeCallbacksAndMessages(null);
    }

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case START_ANIMATION:
                setStartProgress(0);
                setProgressWithAnimation(CircleProcessSlaver.DEFAULT_COMPLETE_PROCESS
                        , (int) mRepeatPeriod, new Animator.AnimatorListener() {
                            @Override
                            public void onAnimationStart(Animator animation) {
                                mRepeatCount++;
                            }

                            @Override
                            public void onAnimationEnd(Animator animation) {
                                mCheckingCallback.onCheckStatus();

                                if (mRepeatCount < mMaxRepeatCount)
                                    mHandler.sendEmptyMessageDelayed(START_ANIMATION, mRepeatPeriod);
                                else endingProcess();
                            }

                            @Override
                            public void onAnimationCancel(Animator animation) {

                            }

                            @Override
                            public void onAnimationRepeat(Animator animation) {

                            }
                        });

                break;
            default:
                break;
        }

        return false;
    }

    public void endingProcess() {
        if (mComplete) return;

        mComplete = true;
        setStartProgress((int) mCurrentProgress);
        int repeatFactor = 0 < mTargetProgress && mTargetProgress < CircleProcessSlaver.DEFAULT_COMPLETE_PROCESS
                ? (CircleProcessSlaver.DEFAULT_COMPLETE_PROCESS - mTargetProgress) / 20 : 3;
        setProgressWithAnimation(mTargetProgress, (int) mRepeatPeriod * repeatFactor, new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                mScanCompleteCallback.onComplete();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        mHandler.removeCallbacksAndMessages(null);
    }
}
