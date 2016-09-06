package com.ce.game.myapplication.view;

import android.support.annotation.ColorRes;
import android.support.annotation.StringRes;

/**
 * Created by KyleCe on 2016/9/1.
 *
 * @author: KyleCe
 */

public interface TextCustomInterface {
    void setContent(String str);

    void setContent(@StringRes int strId);

    void setBgColor(@ColorRes int colorId);

    void setContentSizeUnitSp(float size);
}
