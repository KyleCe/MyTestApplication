package com.ce.game.myapplication.view;

/**
 * Created on 2016/9/20
 * in BlaBla by Kyle
 */

public interface CircleProgressInterface {
    void setStartProgress(int progress);

    void showScanPointer();

    void setCompleteCallback(CompleteCallback completeCallback);
}
