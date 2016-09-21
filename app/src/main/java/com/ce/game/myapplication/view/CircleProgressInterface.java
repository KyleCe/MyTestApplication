package com.ce.game.myapplication.view;


/**
 * Created on 2016/9/20
 * in BlaBla by Kyle
 */

public interface CircleProgressInterface {
    void setStartProgress(int progress);

    void onSetStartEndProcess(int startProgress, int endProcess);

    void showScanPointer();

    void setDisplayCompleteIcon(boolean displayCompleteIcon);

    void setCompleteCallback(CompleteCallback completeCallback);
}
