package com.ce.game.myapplication.util;

/**
 * Created on 2016/9/19
 * in BlaBla by Kyle
 */

public interface MessageProcessViewInterface {
    void onStartProcess(int progress);

    void onSetStartEndProcess(int startProgress, int endProcess);

    void onStartScanAnimation();

    void onUpdateProcess(int progress);

    void onUpdateDescription(String description);

    void onEndProcess(int progress);

    void onShowUpResultPage(int resultProgress, String resultTitle, String resultDescription
            , String buttonContent, Runnable buttonClickCallback);

    MessageProcessViewInterface NULL = new MessageProcessViewInterface() {
        @Override
        public void onStartProcess(int progress) {

        }

        @Override
        public void onSetStartEndProcess(int startProgress, int endProcess) {

        }

        @Override
        public void onStartScanAnimation() {

        }

        @Override
        public void onUpdateProcess(int progress) {

        }

        @Override
        public void onUpdateDescription(String description) {

        }

        @Override
        public void onEndProcess(int progress) {

        }

        @Override
        public void onShowUpResultPage(int resultProgress, String resultTitle, String resultDescription, String buttonContent, Runnable buttonClickCallback) {

        }
    };
}
