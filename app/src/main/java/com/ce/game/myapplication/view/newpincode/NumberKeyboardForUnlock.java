package com.ce.game.myapplication.view.newpincode;

import android.content.Context;
import android.support.annotation.IdRes;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import com.ce.game.myapplication.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


/**
 * Created by KyleCe on 2016/8/10.
 *
 * @author: KyleCe
 */

public class NumberKeyboardForUnlock extends FrameLayout {

    protected List<KeyboardInterface> mKeyList;
    protected View mItemParent;
    protected final int KEYBOARD_SCALE = 12;

    public NumberKeyboardForUnlock(Context context) {
        this(context, null);
    }

    public NumberKeyboardForUnlock(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NumberKeyboardForUnlock(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        inflate(context, R.layout.first_anim_showcase_number_keyboard_all_keyboard, this);

        mItemParent = findViewById(R.id.single_button_parent);

        setForUnlock();
    }

    protected void setForUnlock() {
        mKeyList = new ArrayList<>(KEYBOARD_SCALE);
        addButtonToList(mKeyList, R.id.number_keyboard_0);
        addButtonToList(mKeyList, R.id.number_keyboard_1);
        addButtonToList(mKeyList, R.id.number_keyboard_2);
        addButtonToList(mKeyList, R.id.number_keyboard_3);
        addButtonToList(mKeyList, R.id.number_keyboard_4);
        addButtonToList(mKeyList, R.id.number_keyboard_5);
        addButtonToList(mKeyList, R.id.number_keyboard_6);
        addButtonToList(mKeyList, R.id.number_keyboard_7);
        addButtonToList(mKeyList, R.id.number_keyboard_8);
        addButtonToList(mKeyList, R.id.number_keyboard_9);
        addButtonToList(mKeyList, R.id.number_keyboard_10);
        addButtonToList(mKeyList, R.id.number_keyboard_11);
    }

    protected <N extends KeyboardInterface> void addButtonToList(Collection<N> c, @IdRes int id) {
        c.add((N) findViewById(id));
    }

    public void setPressCallback(PressCallback pressCallback) {
        pressCallback = pressCallback == null ? PressCallback.NULL : pressCallback;
        for (KeyboardInterface n : mKeyList)
            n.setPressCallback(pressCallback);
    }
}
