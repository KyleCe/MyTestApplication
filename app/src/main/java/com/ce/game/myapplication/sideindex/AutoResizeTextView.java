package com.ce.game.myapplication.sideindex;

import android.content.Context;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.TextView;

/**
 * Created by KyleCe on 2016/5/26.
 *
 * @author: KyleCe
 */
//
//public class AutoResizeTextView extends TextView {
//    private interface SizeTester {
//        /**
//         *
//         * @param suggestedSize
//         *            Size of text to be tested
//         * @param availableSpace
//         *            available space in which text must fit
//         * @return an integer < 0 if after applying {@code suggestedSize} to
//         *         text, it takes less space than {@code availableSpace}, > 0
//         *         otherwise
//         */
//        public int onTestSize(int suggestedSize, RectF availableSpace);
//    }
//
//    private RectF mTextRect = new RectF();
//
//    private RectF mAvailableSpaceRect;
//
//    private SparseIntArray mTextCachedSizes;
//
//    private TextPaint mPaint;
//
//    private float mMaxTextSize;
//
//    private float mSpacingMult = 1.0f;
//
//    private float mSpacingAdd = 0.0f;
//
//    private float mMinTextSize = 20;
//
//    private int mWidthLimit;
//
//    private static final int NO_LINE_LIMIT = -1;
//    private int mMaxLines;
//
//    private boolean mEnableSizeCache = true;
//    private boolean mInitiallized;
//
//    public AutoResizeTextView(Context context) {
//        super(context);
//        initialize();
//    }
//
//    public AutoResizeTextView(Context context, AttributeSet attrs) {
//        super(context, attrs);
//        initialize();
//    }
//
//    public AutoResizeTextView(Context context, AttributeSet attrs, int defStyle) {
//        super(context, attrs, defStyle);
//        initialize();
//    }
//
//    private void initialize() {
//        mPaint = new TextPaint(getPaint());
//        mMaxTextSize = getTextSize();
//        mAvailableSpaceRect = new RectF();
//        mTextCachedSizes = new SparseIntArray();
//        if (mMaxLines == 0) {
//            // no value was assigned during construction
//            mMaxLines = NO_LINE_LIMIT;
//        }
//        mInitiallized = true;
//    }
//
//    @Override
//    public void setText(final CharSequence text, BufferType mType) {
//        super.setText(text, mType);
//        adjustTextSize(text.toString());
//    }
//
//    @Override
//    public void setTextSize(float size) {
//        mMaxTextSize = size;
//        mTextCachedSizes.clear();
//        adjustTextSize(getText().toString());
//    }
//
//    @Override
//    public void setMaxLines(int maxlines) {
//        super.setMaxLines(maxlines);
//        mMaxLines = maxlines;
//        reAdjust();
//    }
//
//    public int getMaxLines() {
//        return mMaxLines;
//    }
//
//    @Override
//    public void setSingleLine() {
//        super.setSingleLine();
//        mMaxLines = 1;
//        reAdjust();
//    }
//
//    @Override
//    public void setSingleLine(boolean singleLine) {
//        super.setSingleLine(singleLine);
//        if (singleLine) {
//            mMaxLines = 1;
//        } else {
//            mMaxLines = NO_LINE_LIMIT;
//        }
//        reAdjust();
//    }
//
//    @Override
//    public void setLines(int lines) {
//        super.setLines(lines);
//        mMaxLines = lines;
//        reAdjust();
//    }
//
//    @Override
//    public void setTextSize(int unit, float size) {
//        Context c = getContext();
//        Resources r;
//
//        if (c == null)
//            r = Resources.getSystem();
//        else
//            r = c.getResources();
//        mMaxTextSize = TypedValue.applyDimension(unit, size,
//                r.getDisplayMetrics());
//        mTextCachedSizes.clear();
//        adjustTextSize(getText().toString());
//    }
//
//    @Override
//    public void setLineSpacing(float add, float mult) {
//        super.setLineSpacing(add, mult);
//        mSpacingMult = mult;
//        mSpacingAdd = add;
//    }
//
//    /**
//     * Set the lower text size limit and invalidate the mView
//     *
//     * @param minTextSize
//     */
//    public void setMinTextSize(float minTextSize) {
//        mMinTextSize = minTextSize;
//        reAdjust();
//    }
//
//    private void reAdjust() {
//        adjustTextSize(getText().toString());
//    }
//
//    private void adjustTextSize(String string) {
//        if (!mInitiallized) {
//            return;
//        }
//        int startSize = (int) mMinTextSize;
//        int heightLimit = getMeasuredHeight() - getCompoundPaddingBottom()
//                - getCompoundPaddingTop();
//        mWidthLimit = getMeasuredWidth() - getCompoundPaddingLeft()
//                - getCompoundPaddingRight();
//        mAvailableSpaceRect.right = mWidthLimit;
//        mAvailableSpaceRect.bottom = heightLimit;
//        super.setTextSize(
//                TypedValue.COMPLEX_UNIT_PX,
//                efficientTextSizeSearch(startSize, (int) mMaxTextSize,
//                        mSizeTester, mAvailableSpaceRect));
//    }
//
//    private final SizeTester mSizeTester = new SizeTester() {
//        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
//        @Override
//        public int onTestSize(int suggestedSize, RectF availableSPace) {
//            mPaint.setTextSize(suggestedSize);
//            String text = getText().toString();
//            boolean singleline = getMaxLines() == 1;
//            if (singleline) {
//                mTextRect.bottom = mPaint.getFontSpacing();
//                mTextRect.right = mPaint.measureText(text);
//            } else {
//                StaticLayout layout = new StaticLayout(text, mPaint,
//                        mWidthLimit, Layout.Alignment.ALIGN_NORMAL, mSpacingMult,
//                        mSpacingAdd, true);
//                // return early if we have more lines
//                if (getMaxLines() != NO_LINE_LIMIT
//                        && layout.getLineCount() > getMaxLines()) {
//                    return 1;
//                }
//                mTextRect.bottom = layout.getHeight();
//                int maxWidth = -1;
//                for (int i = 0; i < layout.getLineCount(); i++) {
//                    if (maxWidth < layout.getLineWidth(i)) {
//                        maxWidth = (int) layout.getLineWidth(i);
//                    }
//                }
//                mTextRect.right = maxWidth;
//            }
//
//            mTextRect.offsetTo(0, 0);
//            if (availableSPace.contains(mTextRect)) {
//                // may be too small, don't worry we will find the best match
//                return -1;
//            } else {
//                // too big
//                return 1;
//            }
//        }
//    };
//
//    /**
//     * Enables or disables size caching, enabling it will improve performance
//     * where you are animating a value inside TextView. This stores the font
//     * size against getText().length() Be careful though while enabling it as 0
//     * takes more space than 1 on some fonts and so on.
//     *
//     * @param enable
//     *            enable font size caching
//     */
//    public void enableSizeCache(boolean enable) {
//        mEnableSizeCache = enable;
//        mTextCachedSizes.clear();
//        adjustTextSize(getText().toString());
//    }
//
//    private int efficientTextSizeSearch(int start, int end,
//                                        SizeTester sizeTester, RectF availableSpace) {
//        if (!mEnableSizeCache) {
//            return binarySearch(start, end, sizeTester, availableSpace);
//        }
//        String text = getText().toString();
//        int key = text == null ? 0 : text.length();
//        int size = mTextCachedSizes.get(key);
//        if (size != 0) {
//            return size;
//        }
//        size = binarySearch(start, end, sizeTester, availableSpace);
//        mTextCachedSizes.put(key, size);
//        return size;
//    }
//
//    private static int binarySearch(int start, int end, SizeTester sizeTester,
//                                    RectF availableSpace) {
//        int lastBest = start;
//        int lo = start;
//        int hi = end - 1;
//        int mid = 0;
//        while (lo <= hi) {
//            mid = (lo + hi) >>> 1;
//            int midValCmp = sizeTester.onTestSize(mid, availableSpace);
//            if (midValCmp < 0) {
//                lastBest = lo;
//                lo = mid + 1;
//            } else if (midValCmp > 0) {
//                hi = mid - 1;
//                lastBest = hi;
//            } else {
//                return mid;
//            }
//        }
//        // make sure to return last best
//        // this is what should always be returned
//        return lastBest;
//
//    }
//
//    @Override
//    protected void onTextChanged(final CharSequence text, final int start,
//                                 final int before, final int after) {
//        super.onTextChanged(text, start, before, after);
//        reAdjust();
//    }
//
//    @Override
//    protected void onSizeChanged(int width, int height, int oldwidth,
//                                 int oldheight) {
//        mTextCachedSizes.clear();
//        super.onSizeChanged(width, height, oldwidth, oldheight);
//        if (width != oldwidth || height != oldheight) {
//            reAdjust();
//        }
//    }
//}

public class AutoResizeTextView extends TextView {

    // Minimum text size for this text mView
    public static final float MIN_TEXT_SIZE = 20;

    // Interface for resize notifications
    public interface OnTextResizeListener {
        public void onTextResize(TextView textView, float oldSize, float newSize);
    }

    // Our ellipse string
    private static final String mEllipsis = "...";

    // Registered resize mListener
    private OnTextResizeListener mTextResizeListener;

    // Flag for text and/or size changes to force a resize
    private boolean mNeedsResize = false;

    // Text size that is set from code. This acts as a starting point for resizing
    private float mTextSize;

    // Temporary upper bounds on the starting text size
    private float mMaxTextSize = 0;

    // Lower bounds for text size
    private float mMinTextSize = MIN_TEXT_SIZE;

    // Text mView line spacing multiplier
    private float mSpacingMult = 1.0f;

    // Text mView additional line spacing
    private float mSpacingAdd = 0.0f;

    // Add ellipsis to text that overflows at the smallest text size
    private boolean mAddEllipsis = true;

    // Default constructor override
    public AutoResizeTextView(Context context) {
        this(context, null);
    }

    // Default constructor when inflating from XML file
    public AutoResizeTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    // Default constructor override
    public AutoResizeTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mTextSize = getTextSize();
    }

    /**
     * When text changes, set the force resize flag to true and reset the text size.
     */
    @Override
    protected void onTextChanged(final CharSequence text, final int start, final int before, final int after) {
        mNeedsResize = true;
        // Since this mView may be reused, it is good to reset the text size
        resetTextSize();
    }

    /**
     * If the text mView size changed, set the force resize flag to true
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        if (w != oldw || h != oldh) {
            mNeedsResize = true;
        }
    }

    /**
     * Register mListener to receive resize notifications
     * @param listener
     */
    public void setOnResizeListener(OnTextResizeListener listener) {
        mTextResizeListener = listener;
    }

    /**
     * Override the set text size to update our internal reference values
     */
    @Override
    public void setTextSize(float size) {
        super.setTextSize(size);
        mTextSize = getTextSize();
    }

    /**
     * Override the set text size to update our internal reference values
     */
    @Override
    public void setTextSize(int unit, float size) {
        super.setTextSize(unit, size);
        mTextSize = getTextSize();
    }

    /**
     * Override the set line spacing to update our internal reference values
     */
    @Override
    public void setLineSpacing(float add, float mult) {
        super.setLineSpacing(add, mult);
        mSpacingMult = mult;
        mSpacingAdd = add;
    }

    /**
     * Set the upper text size limit and invalidate the mView
     * @param maxTextSize
     */
    public void setMaxTextSize(float maxTextSize) {
        mMaxTextSize = maxTextSize;
        requestLayout();
        invalidate();
    }

    /**
     * Return upper text size limit
     * @return
     */
    public float getMaxTextSize() {
        return mMaxTextSize;
    }

    /**
     * Set the lower text size limit and invalidate the mView
     * @param minTextSize
     */
    public void setMinTextSize(float minTextSize) {
        mMinTextSize = minTextSize;
        requestLayout();
        invalidate();
    }

    /**
     * Return lower text size limit
     * @return
     */
    public float getMinTextSize() {
        return mMinTextSize;
    }

    /**
     * Set flag to add ellipsis to text that overflows at the smallest text size
     * @param addEllipsis
     */
    public void setAddEllipsis(boolean addEllipsis) {
        mAddEllipsis = addEllipsis;
    }

    /**
     * Return flag to add ellipsis to text that overflows at the smallest text size
     * @return
     */
    public boolean getAddEllipsis() {
        return mAddEllipsis;
    }

    /**
     * Reset the text to the original size
     */
    public void resetTextSize() {
        if (mTextSize > 0) {
            super.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTextSize);
            mMaxTextSize = mTextSize;
        }
    }

    /**
     * Resize text after measuring
     */
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        if (changed || mNeedsResize) {
            int widthLimit = (right - left) - getCompoundPaddingLeft() - getCompoundPaddingRight();
            int heightLimit = (bottom - top) - getCompoundPaddingBottom() - getCompoundPaddingTop();
            resizeText(widthLimit, heightLimit);
        }
        super.onLayout(changed, left, top, right, bottom);
    }

    /**
     * Resize the text size with default width and height
     */
    public void resizeText() {

        int heightLimit = getHeight() - getPaddingBottom() - getPaddingTop();
        int widthLimit = getWidth() - getPaddingLeft() - getPaddingRight();
        resizeText(widthLimit, heightLimit);
    }

    /**
     * Resize the text size with specified width and height
     * @param width
     * @param height
     */
    public void resizeText(int width, int height) {
        CharSequence text = getText();
        // Do not resize if the mView does not have dimensions or there is no text
        if (text == null || text.length() == 0 || height <= 0 || width <= 0 || mTextSize == 0) {
            return;
        }

        if (getTransformationMethod() != null) {
            text = getTransformationMethod().getTransformation(text, this);
        }

        // Get the text mView's paint object
        TextPaint textPaint = getPaint();

        // Store the current text size
        float oldTextSize = textPaint.getTextSize();
        // If there is a max text size set, use the lesser of that and the default text size
        float targetTextSize = mMaxTextSize > 0 ? Math.min(mTextSize, mMaxTextSize) : mTextSize;

        // Get the required text height
        int textHeight = getTextHeight(text, textPaint, width, targetTextSize);

        // Until we either fit within our text mView or we had reached our min text size, incrementally try smaller sizes
        while (textHeight > height && targetTextSize > mMinTextSize) {
            targetTextSize = Math.max(targetTextSize - 2, mMinTextSize);
            textHeight = getTextHeight(text, textPaint, width, targetTextSize);
        }

        // If we had reached our minimum text size and still don't fit, append an ellipsis
        if (mAddEllipsis && targetTextSize == mMinTextSize && textHeight > height) {
            // Draw using a static layout
            // modified: use a copy of TextPaint for measuring
            TextPaint paint = new TextPaint(textPaint);
            // Draw using a static layout
            StaticLayout layout = new StaticLayout(text, paint, width, Layout.Alignment.ALIGN_NORMAL, mSpacingMult, mSpacingAdd, false);
            // Check that we have a least one line of rendered text
            if (layout.getLineCount() > 0) {
                // Since the line at the specific vertical position would be cut off,
                // we must trim up to the previous line
                int lastLine = layout.getLineForVertical(height) - 1;
                // If the text would not even fit on a single line, clear it
                if (lastLine < 0) {
                    setText("");
                }
                // Otherwise, trim to the previous line and add an ellipsis
                else {
                    int start = layout.getLineStart(lastLine);
                    int end = layout.getLineEnd(lastLine);
                    float lineWidth = layout.getLineWidth(lastLine);
                    float ellipseWidth = textPaint.measureText(mEllipsis);

                    // Trim characters off until we have enough room to draw the ellipsis
                    while (width < lineWidth + ellipseWidth) {
                        lineWidth = textPaint.measureText(text.subSequence(start, --end + 1).toString());
                    }
                    setText(text.subSequence(0, end) + mEllipsis);
                }
            }
        }

        // Some devices try to auto adjust line spacing, so force default line spacing
        // and invalidate the layout as a side effect
        setTextSize(TypedValue.COMPLEX_UNIT_PX, targetTextSize);
        setLineSpacing(mSpacingAdd, mSpacingMult);

        // Notify the mListener if registered
        if (mTextResizeListener != null) {
            mTextResizeListener.onTextResize(this, oldTextSize, targetTextSize);
        }

        // Reset force resize flag
        mNeedsResize = false;
    }

    // Set the text size of the text paint object and use a static layout to render text off screen before measuring
    private int getTextHeight(CharSequence source, TextPaint paint, int width, float textSize) {
        // modified: make a copy of the original TextPaint object for measuring
        // (apparently the object gets modified while measuring, see also the
        // docs for TextView.getPaint() (which states to access it read-only)
        TextPaint paintCopy = new TextPaint(paint);
        // Update the text paint object
        paintCopy.setTextSize(textSize);
        // Measure using a static layout
        StaticLayout layout = new StaticLayout(source, paintCopy, width, Layout.Alignment.ALIGN_NORMAL, mSpacingMult, mSpacingAdd, true);
        return layout.getHeight();
    }

}