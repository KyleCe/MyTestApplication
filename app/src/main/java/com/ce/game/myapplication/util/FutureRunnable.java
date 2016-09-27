package com.ce.game.myapplication.util;

import java.util.concurrent.Future;

/**
 * Created by KyleCe on 2016/8/16.
 *
 * @author: KyleCe
 */
public abstract class FutureRunnable implements Runnable {

    private Future<?> future;

    public Future<?> getFuture() {
        return future;
    }

    public void setFuture(Future<?> future) {
        this.future = future;
    }
}

