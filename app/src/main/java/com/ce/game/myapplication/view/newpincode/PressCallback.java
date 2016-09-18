package com.ce.game.myapplication.view.newpincode;

import com.ce.game.myapplication.view.NumberKeyboardSingleButton;

/**
 * Created on 2016/9/14
 * in BlaBla by Kyle
 */

public interface PressCallback {
    void onPress(@NumberKeyboardSingleButton.ButtonContent String key);

    PressCallback NULL = new PressCallback() {
        @Override
        public void onPress(@NumberKeyboardSingleButton.ButtonContent String key) {
        }
    };
}
