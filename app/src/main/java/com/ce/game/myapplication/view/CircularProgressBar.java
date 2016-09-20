package com.ce.game.myapplication.view;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import com.ce.game.myapplication.R;
import com.ce.game.myapplication.util.DisplayUtil;
import com.ce.game.myapplication.util.FontMaster;


/**
 * Created by KyleCe on 2016/3/3.
 *
 * @author: KyleCe
 */
public class CircularProgressBar extends View {

    // Properties
    private float progress = 0;
    private float strokeWidth = getResources().getDimension(R.dimen.default_stroke_width);
    private float backgroundStrokeWidth = getResources().getDimension(R.dimen.default_background_stroke_width);
    // foreground color
    private int color = Color.WHITE;
    private int backgroundColor = 0x71ffffff;
    private int edgeColor = 0x9eFFFFFF;

    private int textColor = Color.WHITE;
    private float textSize = 55;//unit: sp
    private float sufTextSize = 20;//unit: sp

    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.LINEAR_TEXT_FLAG);

    private Shader shader;

    // Object used to draw
    private int startAngle = 90;
    private RectF rectF, edgeRF, innerEdgeRf;
    private Paint backgroundPaint, foregroundPaint, edgePaint;
    private String progressHint;

    // font
    private Typeface font;

    private int[] colors4Alpha = {// 40% --- 90% alpha
            0xdeffffff/*87%*/
            , 0xe5ffffff/*90%*/
            , 0x71ffffff/*40%*/
            , 0x9effffff/*62%*/
            , 0xbfffffff/*75%*/
            , 0xdeffffff/*87%*/
            , 0xe5ffffff/*90%*/
    };
    private float[] positions4Alpha = {
            0
            , 83f / 360f
            , 90f / 360f
            , 180f / 360f
            , 270f / 360f
            , 360f / 360f
            , 89f / 360f
    };

    // % first draw x point, avoid its signal position changing
    private int[] percentFirstDrawPoint = {0, 0};

    //region Constructor & Init Method
    public CircularProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        rectF = new RectF();
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CircularProgressBar, 0, 0);
        //Reading values from the XML layout
        try {
            // Value
            progress = typedArray.getFloat(R.styleable.CircularProgressBar_cpb_progress, progress);
            // StrokeWidth
            strokeWidth = typedArray.getDimension(R.styleable.CircularProgressBar_cpb_progressbar_width, strokeWidth);
            backgroundStrokeWidth = typedArray.getDimension(R.styleable.CircularProgressBar_cpb_background_progressbar_width, backgroundStrokeWidth);
            // Color
            color = typedArray.getInt(R.styleable.CircularProgressBar_cpb_progressbar_color, color);
            backgroundColor = typedArray.getInt(R.styleable.CircularProgressBar_cpb_background_progressbar_color, backgroundColor);
        } finally {
            typedArray.recycle();
        }

        // Init Background
        backgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        backgroundPaint.setColor(backgroundColor);
        backgroundPaint.setStyle(Paint.Style.STROKE);
        backgroundPaint.setStrokeWidth(backgroundStrokeWidth);

        // edge paint, set the edge
        edgePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        edgePaint.setColor(edgeColor);
        edgePaint.setStyle(Paint.Style.STROKE);
        edgePaint.setStrokeWidth(1);

        // Init Foreground
        foregroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        foregroundPaint.setColor(color);
        foregroundPaint.setStyle(Paint.Style.STROKE);
        foregroundPaint.setStrokeWidth(strokeWidth);
        // set round head and tail
        foregroundPaint.setStrokeCap(Paint.Cap.ROUND);

        // set percent text size, fit different screen density
        textSize = DisplayUtil.dp2Pix(textSize);
        sufTextSize = DisplayUtil.dp2Pix(sufTextSize);
    }
    //endregion

    //region Draw Method
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // oval, background
        canvas.drawOval(rectF, backgroundPaint);

        // edge rectF, must place here after draw oval
        //cause just after that ,the rectF can have a l/t/r/b value
        if (edgeRF == null) {
            float distance = backgroundStrokeWidth / 2;
            edgeRF = new RectF(rectF.left - distance  /*l*/, rectF.top - distance /*t*/
                    , rectF.right + distance /*r*/, rectF.bottom + distance /*b*/);
        }
        // oval, outer edge
        canvas.drawOval(edgeRF, edgePaint);
        // inner edge
        if (innerEdgeRf == null) {
            float distance = backgroundStrokeWidth / 2;
            innerEdgeRf = new RectF(rectF.left + distance  /*l*/, rectF.top + distance /*t*/
                    , rectF.right - distance /*r*/, rectF.bottom - distance /*b*/);
        }
        canvas.drawOval(innerEdgeRf, edgePaint);

        // arc, percentage
        float angle = 360 * progress / 100;
        // set gradient alpha
        SweepGradient gradient = new SweepGradient(getWidth() >> 1, getHeight() >> 1, colors4Alpha, positions4Alpha);
        foregroundPaint.setShader(gradient);
        canvas.drawArc(rectF, startAngle, angle, false, foregroundPaint);

        // number, percentage
        int canvasWidth = canvas.getWidth();
        int canvasHeight = canvas.getHeight();
        progressHint = String.valueOf((int) progress);
        paint.setColor(textColor);
        paint.setTextSize(textSize);
        paint.setStrokeWidth(28);

        // font
        if (font == null) font = FontMaster.with(getContext()).getCustomFont();
        // bold mode
        paint.setTypeface(Typeface.create(font, Typeface.BOLD));

        // measure bounds, width and height
        Rect bounds = new Rect();
        // use "99" instead of true progress, avoid num changing position
        if (progress < 10) paint.getTextBounds("9", 0, "9".length(), bounds);
        else if (progress == 100) paint.getTextBounds("100", 0, "100".length(), bounds);
        else paint.getTextBounds("99", 0, "99".length(), bounds);
        // bounds width and height
        int textWidth = bounds.width();
        int textHeight = bounds.height();

        // process percentage
        int startX = (canvasWidth - textWidth) >> 1;
        int startY = (canvasHeight + textHeight) >> 1;
        // set color change
        if (shader == null)
            shader = new LinearGradient(startX, (canvasHeight - textHeight) >> 1, startX, (canvasHeight + textHeight) >> 1,
                    0xbfffffff, 0xffffffff, Shader.TileMode.CLAMP);
//            shader = new LinearGradient(startX, startY, startX + textWidth, startY + textHeight,
//                    0xffffffff, 0x30ffffff, Shader.TileMode.MIRROR);
        paint.setShader(shader);

        canvas.drawText(progressHint, startX, startY, paint);

        // draw "%", coreX + w/2, coreY + h/4, pay attention to the operator priority
        // get holding position,avoid its signal position changing
        if (percentFirstDrawPoint[0] == 0) {
            paint.getTextBounds("99", 0, "99".length(), bounds);
            percentFirstDrawPoint[0] = bounds.width();
            percentFirstDrawPoint[1] = bounds.height();
        }
        paint.setTextSize(sufTextSize);
        String suffix = " %";
        paint.getTextBounds(suffix, 0, suffix.length(), bounds);
        int sufHeight = bounds.height();
        canvas.drawText(suffix, (canvasWidth + percentFirstDrawPoint[0]) >> 1,
                (canvasHeight >> 1) - (percentFirstDrawPoint[1] >> 1) + sufHeight, paint);

    }
    //endregion

    //region Measure Method
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        final int height = getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec);
        final int width = getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec);
        final int min = Math.min(width, height);
        setMeasuredDimension(min, min);
        float highStroke = (strokeWidth > backgroundStrokeWidth) ? strokeWidth : backgroundStrokeWidth;
        rectF.set(0 + highStroke / 2, 0 + highStroke / 2, min - highStroke / 2, min - highStroke / 2);
    }
    //endregion

    //region Method Get/Set
    public float getProgress() {
        return progress;
    }

    public void setProgress(float progress) {
        this.progress = (progress <= 100) ? progress : 100;
        invalidate();
    }

    public float getProgressBarWidth() {
        return strokeWidth;
    }

    public void setProgressBarWidth(float strokeWidth) {
        this.strokeWidth = strokeWidth;
        foregroundPaint.setStrokeWidth(strokeWidth);
        requestLayout();//Because it should recalculate its bounds
        invalidate();
    }

    public float getBackgroundProgressBarWidth() {
        return backgroundStrokeWidth;
    }

    public void setBackgroundProgressBarWidth(float backgroundStrokeWidth) {
        this.backgroundStrokeWidth = backgroundStrokeWidth;
        backgroundPaint.setStrokeWidth(backgroundStrokeWidth);
        requestLayout();//Because it should recalculate its bounds
        invalidate();
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
        foregroundPaint.setColor(color);
        invalidate();
        requestLayout();
    }

    public int getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
        backgroundPaint.setColor(backgroundColor);
        invalidate();
        requestLayout();
    }
    //endregion

    //region Other Method

    /**
     * Set the progress with an animation.
     * Note that the {@link ObjectAnimator} Class automatically set the progress
     * so don't call the {@link CircularProgressBar#setProgress(float)} directly within this method.
     *
     * @param progress The progress it should animate to it.
     */
    public void setProgressWithAnimation(float progress) {
        setProgressWithAnimation(progress, null);
    }

    public void setProgressWithAnimation(float progress, Animator.AnimatorListener animatorListener) {
        if (100 < progress) progress = 100;
        if (progress < 0) progress = 0;

        setProgressWithAnimation(progress, 200, animatorListener);
    }

    /**
     * Set the progress with an animation.
     * Note that the {@link ObjectAnimator} Class automatically set the progress
     * so don't call the {@link CircularProgressBar#setProgress(float)} directly within this method.
     *
     * @param progress The progress it should animate to it.
     * @param duration The length of the animation, in milliseconds.
     */
    public void setProgressWithAnimation(float progress, int duration, Animator.AnimatorListener animatorListener) {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(this, "progress", progress);
        objectAnimator.setDuration(duration);
        objectAnimator.setInterpolator(new DecelerateInterpolator());
        if (animatorListener != null) {
            objectAnimator.addListener(animatorListener);
        }
        objectAnimator.start();
    }
    //endregion
}