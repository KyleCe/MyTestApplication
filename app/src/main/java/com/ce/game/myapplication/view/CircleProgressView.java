package com.ce.game.myapplication.view;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import com.ce.game.myapplication.R;

import static com.ce.game.myapplication.R.color.privacy_detect_base_green;

/**
 * Created by KyleCe on 2016/8/10.
 *
 * @author: KyleCe
 */
public class CircleProgressView extends View implements CircleProgressInterface {

    private float mProgress = 0;
    protected final int INTERPOLATOR_UNSET = 0;
    private float mInterpolator = INTERPOLATOR_UNSET;
    protected static final float TEXT_SCALE_RATIO = .5f;
    protected static final float FIRST_SUB_CIRCLE_RATIO = .73f;
    protected static final float SECOND_SUB_CIRCLE_RATIO = .37f;
    protected static final float EDGE_RATIO = 0.70675f;

    private int[] mColors4Alpha = {0x00000000, 0x00000000, 0x00000000, 0x50000000,};

    protected RectF mRectF;
    private int mStartAngle = -90;

    protected float mTextSize;

    protected Paint mLinePaint;
    protected Paint mShaderPaint;
    protected Paint mTextPaint;
    protected Paint mProgressCirclePaint;
    protected Paint mScanPointerPaint;
    protected boolean mDisplayScanPointer = false;

    protected int mBaseColor;
    protected int[] COLOR_RANGE = {60, 85};
    protected int[] STAGE_COLOR = {
            getResources().getColor(R.color.privacy_detect_progress_control_color_stage1),
            getResources().getColor(R.color.privacy_detect_progress_control_color_stage1_end),
            getResources().getColor(R.color.privacy_detect_progress_control_color_stage2),
            getResources().getColor(R.color.privacy_detect_progress_control_color_stage2_end),
            getResources().getColor(R.color.privacy_detect_progress_control_color_stage3),
            getResources().getColor(R.color.privacy_detect_progress_control_color_stage3_end)};
    protected final int DEFAULT_SIZE = (int) getResources().getDimension(R.dimen.default_progress_size);
    protected int mSubCircleColor = getResources().getColor(R.color.privacy_detect_sub_circle_color);
    protected int mDefaultStokeWidth = getResources().getDimensionPixelOffset(R.dimen.default_progress_sub_circle_stroke_width);
    protected int mBoundsWidth = getResources().getDimensionPixelOffset(R.dimen.default_progress_bounds_stroke_width);
    protected int mPadding = getResources().getDimensionPixelOffset(R.dimen.default_progress_padding);
    protected int mTextColor;
    protected Bitmap mCompleteBitmap;
    protected float BITMAP_X_Y_RATIO = .6765f;

    protected CompleteCallback mCompleteCallback = CompleteCallback.NULL;

    public CircleProgressView(Context context) {
        this(context, null);
    }

    public CircleProgressView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleProgressView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CircularProgressView);
        mProgress = typedArray.getFloat(R.styleable.CircularProgressView_progress, mProgress);
        mBaseColor = typedArray.getColor(R.styleable.CircularProgressView_progress_base_color
                , getResources().getColor(privacy_detect_base_green));
        mTextColor = typedArray.getColor(R.styleable.CircularProgressView_progress_text_color
                , getResources().getColor(R.color.white));
        typedArray.recycle();

        init();
    }

    protected void init() {
        mLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mLinePaint.setTextSize(50);

        mShaderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mShaderPaint.setTextSize(50);

        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.LINEAR_TEXT_FLAG);

        // Init Foreground
        mProgressCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mProgressCirclePaint.setColor(mTextColor);
        mProgressCirclePaint.setStyle(Paint.Style.STROKE);
        mProgressCirclePaint.setStrokeWidth(mBoundsWidth);
        // set round head and tail
        mProgressCirclePaint.setStrokeCap(Paint.Cap.ROUND);
        mProgressCirclePaint.setStrokeJoin(Paint.Join.ROUND);

        mRectF = new RectF();

        // Init Foreground
        mScanPointerPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mScanPointerPaint.setStyle(Paint.Style.FILL);

        mCompleteBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.circle_progress_complete);
    }

    @Override
    protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int width;
        int height;

        if (widthMode == MeasureSpec.EXACTLY) width = widthSize;
        else if (widthMode == MeasureSpec.AT_MOST) width = Math.min(DEFAULT_SIZE, widthSize);
        else width = DEFAULT_SIZE;

        if (heightMode == MeasureSpec.EXACTLY) height = heightSize;
        else if (heightMode == MeasureSpec.AT_MOST) height = Math.min(DEFAULT_SIZE, heightSize);
        else height = DEFAULT_SIZE;

        int minSize = Math.min(width, height);
        setMeasuredDimension(minSize, minSize);

        mTextSize = minSize * TEXT_SCALE_RATIO;
        mCompleteBitmap = Bitmap.createScaledBitmap(mCompleteBitmap, minSize >> 1, (int) ((minSize >> 1) * BITMAP_X_Y_RATIO), true);
    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float baseRadius = (getWidth() >> 1) - mPadding - mBoundsWidth;
        float cx = getWidth() >> 1;
        float cy = getHeight() >> 1;
        float alter = (baseRadius - mBoundsWidth) * EDGE_RATIO;

        // background
        mShaderPaint.setStyle(Paint.Style.FILL);
        int[] gradientColor = parseColorToDraw(mProgress);
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
        canvas.drawLine(cx - alter, cy - alter, cx + alter, cy + alter, mLinePaint);
        canvas.drawLine(cx - alter, cy + alter, cx + alter, cy - alter, mLinePaint);

        float endAngle = 360 * mProgress / 100;

        if (mDisplayScanPointer && mProgress != 100) {
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

        // progress text
        int canvasWidth = canvas.getWidth();
        int canvasHeight = canvas.getHeight();

        // draw text
        // measure bounds, width and height
        Rect bounds = new Rect();
        mTextPaint.setTextSize(mTextSize);
        mTextPaint.setColor(mTextColor);
        // use "99" instead of true mProgress, avoid num changing position
        if (mProgress < 10) mTextPaint.getTextBounds("9", 0, "9".length(), bounds);
        else if (mProgress == 100) mTextPaint.getTextBounds("000", 0, "000".length(), bounds);
        else mTextPaint.getTextBounds("99", 0, "99".length(), bounds);
        // bounds width and height
        int textWidth = bounds.width();
        int textHeight = bounds.height();
        int startX = (canvasWidth - textWidth) >> 1;
        int startY = (canvasHeight + textHeight) >> 1;
        if (mInterpolator != INTERPOLATOR_UNSET) mTextPaint.setTextSize(0);
        canvas.drawText(String.valueOf((int) mProgress), startX, startY, mTextPaint);

        // progress circle
        final RectF oval = new RectF();
        oval.set(cx - boundsRadius, cy - boundsRadius, cx + boundsRadius, cy + boundsRadius);
        Path path = new Path();
        path.arcTo(oval, mStartAngle, mProgress != 100 ? endAngle : endAngle - .1f, true);
        canvas.drawPath(path, mProgressCirclePaint);

        if (mProgress != 100) {
            double e_x = cx + boundsRadius * Math.cos(Math.toRadians(endAngle + mStartAngle));
            double e_y = cy + boundsRadius * Math.sin(Math.toRadians(endAngle + mStartAngle));
            canvas.drawCircle((float) e_x, (float) e_y, (mBoundsWidth << 1), mTextPaint);
            mLinePaint.setColor(Color.parseColor("#50ffffff"));
            canvas.drawCircle((float) e_x, (float) e_y, (mBoundsWidth << 1), mLinePaint);
        }

        if (mInterpolator != INTERPOLATOR_UNSET) {
            float ratio = mInterpolator / 100;
            Matrix matrix = new Matrix();
            matrix.setScale(ratio, ratio);
            matrix.postTranslate(cx - (ratio * mCompleteBitmap.getWidth()) / 2, cy - (ratio * mCompleteBitmap.getHeight()) / 2);

            canvas.drawBitmap(mCompleteBitmap, matrix, null);
        }
    }

    protected int[] parseColorToDraw(float progress) {
        if (progress < COLOR_RANGE[0]) return new int[]{STAGE_COLOR[0], STAGE_COLOR[1]};
        else if (COLOR_RANGE[0] <= progress && progress <= COLOR_RANGE[1])
            return new int[]{STAGE_COLOR[2], STAGE_COLOR[3]};
        else return new int[]{STAGE_COLOR[4], STAGE_COLOR[5]};
    }

    @Override
    public void setStartProgress(int progress) {
        mProgress = progress;
        invalidate();
    }

    @Override
    public void showScanPointer() {
        setDisplayScanPointer(true);
        invalidate();
    }

    @Override
    public void setCompleteCallback(CompleteCallback completeCallback) {
        mCompleteCallback = completeCallback;
    }

    public void setDisplayScanPointer(boolean displayScanPointer) {
        mDisplayScanPointer = displayScanPointer;
    }

    /**
     * the key to the Object Animator,do not change name or delete,do not use
     */
    @Deprecated
    public float getInterpolator() {
        return mInterpolator;
    }

    /**
     * the key to the Object Animator,do not change name or delete,do not use
     */
    @Deprecated
    public void setInterpolator(float interpolator) {
        mInterpolator = interpolator;
        invalidate();
    }

    /**
     * the key to the Object Animator,do not change name or delete,do not use
     */
    @Deprecated
    public float getProgress() {
        return mProgress;
    }

    /**
     * the key to the Object Animator,do not change name or delete,do not use
     */
    @Deprecated
    public void setProgress(float progress) {
        this.mProgress = (progress <= 100) ? progress : 100;
        invalidate();
    }

    /**
     * Set the mProgress with an animation.
     * Note that the {@link ObjectAnimator} Class automatically set the mProgress
     * so don't call the {@link #setProgress(float)} directly within this method.
     *
     * @param progress The mProgress it should animate to it.
     */
    public void setProgressWithAnimation(float progress) {
        setProgressWithAnimation(progress, null);
    }

    public void setProgressWithAnimation(float progress, Animator.AnimatorListener animatorListener) {
        setProgressWithAnimation(progress, 200, animatorListener);
        invalidate();
    }

    /**
     * Set the mProgress with an animation.
     * Note that the {@link ObjectAnimator} Class automatically set the mProgress
     * so don't call the {@link #setProgress(float)} directly within this method.
     *
     * @param progress The mProgress it should animate to it.
     * @param duration The length of the animation, in milliseconds.
     */
    public void setProgressWithAnimation(float progress, int duration, Animator.AnimatorListener animatorListener) {
        if (100 < progress) progress = 100;
        if (progress < 0) progress = 0;

        duration *= (int) progress / 20;

        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(this, "progress", progress);
        objectAnimator.setDuration(duration);
        objectAnimator.setInterpolator(new DecelerateInterpolator());

        if (animatorListener != null) {
            objectAnimator.addListener(animatorListener);
        }
        objectAnimator.start();
        if (progress == 100) addScaleCompleteAnimator(this, progress, duration, objectAnimator);
    }

    private void addScaleCompleteAnimator(final Object o, float progress, final int duration, ObjectAnimator objectAnimator) {
        final float fProgress = progress;

        objectAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                ObjectAnimator anim = ObjectAnimator.ofFloat(o, "interpolator", fProgress);
                anim.setDuration(duration);
                anim.setInterpolator(new DecelerateInterpolator());
                anim.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        mCompleteCallback.onComplete();
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });
                anim.start();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }
}
