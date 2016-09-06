package com.ce.game.myapplication.view;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.ce.game.myapplication.R;

/**
 * Created by KyleCe on 2016/5/23.
 *
 * @author: KyleCe
 */
public class PhoneView extends FrameLayout {

    public LinearLayout mFrameParent;
    public FrameLayout mContainer;
    protected Context mContext;


    public static final int[] PHONE_ROLE_ARRAY = new int[]{
            R.string.guide_view_5_myself,
            R.string.guide_view_5_lover,
            R.string.guide_view_5_colleague,
            R.string.guide_view_5_guest,
    };

    public static final int[] PHONE_BG_ARRAY = new int[]{
            R.color.guide_view_5_myself,
            R.color.guide_view_5_lover,
            R.color.guide_view_5_colleague,
            R.color.guide_view_5_guest,
    };

    public PhoneView(final Context context) {
        this(context, null);
    }

    public PhoneView(final Context context, final AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PhoneView(final Context context, final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mContext = context;

        setBackgroundColor(Color.TRANSPARENT);

        inflate(context, R.layout.phone_view_layout,this);

        mFrameParent = (LinearLayout) findViewById(R.id.phone_frame_parent);
        mContainer = (FrameLayout) findViewById(R.id.phone_frame_container);
    }
}
