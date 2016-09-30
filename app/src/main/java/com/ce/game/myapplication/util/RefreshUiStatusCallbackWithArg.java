package com.ce.game.myapplication.util;

/**
 * Created by KyleCe on 2016/8/17.
 *
 * @author: KyleCe
 */

public interface RefreshUiStatusCallbackWithArg {
    void onRefreshUi(Object obj);

    RefreshUiStatusCallbackWithArg NULL = new RefreshUiStatusCallbackWithArg() {
        @Override
        public void onRefreshUi(Object obj) {

        }
    };
}
