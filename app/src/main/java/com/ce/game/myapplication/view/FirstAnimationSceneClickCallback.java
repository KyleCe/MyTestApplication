package com.ce.game.myapplication.view;

/**
 * Created by KyleCe on 2016/9/6.
 *
 * @author: KyleCe
 */

public interface FirstAnimationSceneClickCallback {
    void onOk();
    void onMenuOne();
    void onMenuTwo();

    FirstAnimationSceneClickCallback NULL = new FirstAnimationSceneClickCallback() {
        @Override
        public void onOk() {

        }

        @Override
        public void onMenuOne() {

        }

        @Override
        public void onMenuTwo() {

        }
    };
}
