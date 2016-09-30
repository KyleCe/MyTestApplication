package com.ce.game.myapplication.util;

/**
 * Created by KyleCe on 2016/8/15.
 *
 * @author: KyleCe
 */
public interface ButtonClickCallback {
    void onOk();

    void onCancel();

    ButtonClickCallback NULL = new ButtonClickCallback() {
        @Override
        public void onOk() {

        }

        @Override
        public void onCancel() {

        }
    };
}

