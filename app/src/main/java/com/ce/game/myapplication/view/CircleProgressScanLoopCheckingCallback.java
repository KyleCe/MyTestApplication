package com.ce.game.myapplication.view;

/**
 * Created on 2016/9/24
 * in BlaBla by Kyle
 */

public interface CircleProgressScanLoopCheckingCallback {
    void onCheckStatus();

    CircleProgressScanLoopCheckingCallback NULL = new CircleProgressScanLoopCheckingCallback() {
        @Override
        public void onCheckStatus() {

        }
    };
}
