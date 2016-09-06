package com.ce.game.myapplication.view;

/**
 * Created by KyleCe on 2016/9/2.
 *
 * @author: KyleCe
 */

public interface CompleteCallback {
    void onComplete();

    CompleteCallback NULL = new CompleteCallback() {
        @Override
        public void onComplete() {
        }
    };
}