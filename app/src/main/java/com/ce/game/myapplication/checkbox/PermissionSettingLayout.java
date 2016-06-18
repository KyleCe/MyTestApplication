package com.ce.game.myapplication.checkbox;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import com.ce.game.myapplication.R;

/**
 * Created by KyleCe on 2016/6/17.
 *
 * @author: KyleCe
 */
public class PermissionSettingLayout extends CheckableFrameLayout {
    private FrameLayout mLayout;

    public PermissionSettingLayout(Context context) {
        this(context,null);
    }

    public PermissionSettingLayout(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public PermissionSettingLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        View inflater = inflate(context, R.layout.permission_layout, this);
        mLayout = (FrameLayout) inflater.findViewById(R.id.parent);
    }


}
