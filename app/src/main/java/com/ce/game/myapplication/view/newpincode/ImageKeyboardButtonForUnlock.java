package com.ce.game.myapplication.view.newpincode;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import com.ce.game.myapplication.R;
import com.ce.game.myapplication.util.ViewU;


/**
 * Created by KyleCe on 2016/8/10.
 *
 * @author: KyleCe
 */

public class ImageKeyboardButtonForUnlock extends NumberKeyboardSingleButtonForUnlock {

    protected ImageView mImageView;
    protected final float IMAGE_SCALE_RATIO = .35f;

    public ImageKeyboardButtonForUnlock(Context context) {
        this(context, null);
    }

    public ImageKeyboardButtonForUnlock(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ImageKeyboardButtonForUnlock(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        setForImage();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int measureSpec =(Math.min(widthMeasureSpec,heightMeasureSpec)  );
        int measureSize = measureSpec & View.MEASURED_SIZE_MASK;
        measureSize *= IMAGE_SCALE_RATIO;

        mImageView.setMinimumWidth(measureSize);
        mImageView.setMinimumHeight(measureSize);
        mImageView.setMaxWidth(measureSize);
        mImageView.setMaxHeight(measureSize);

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    private void setForImage() {
        mImageView = (ImageView) findViewById(R.id.keyboard_button_image);

        ViewU.hideAndShow(mTextView, mImageView);

        if (ButtonContent.K_BACK.equals(mText))
            mImageView.setImageResource(R.drawable.unlockscreen_icon_return);
        else if (ButtonContent.K_BACKSPACE.equals(mText))
            mImageView.setImageResource(R.drawable.unlockscreen_icon_delete);
        else
            mImageView.setImageAlpha(255);

        ViewU.disClick(mImageView);
    }

    @Override
    public View decideViewToResponse(){
        return this;
    }
}
