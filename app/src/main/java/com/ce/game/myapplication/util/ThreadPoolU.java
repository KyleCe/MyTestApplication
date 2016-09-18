package com.ce.game.myapplication.util;

import android.os.Handler;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * Created by KyleCe on 2016/8/1.
 *
 * @author: KyleCe
 */
public class ThreadPoolU {

    /**
     * thread pool trick
     */
    private static ExecutorService sCachedThreadPoolExecutor;

    /**
     * get cached thread pool service
     *
     * @return thread pool service executor
     */
    public static ExecutorService getThreadPool() {
        if (sCachedThreadPoolExecutor == null)
            synchronized (DU.class) {
                return sCachedThreadPoolExecutor == null ?
                        Executors.newCachedThreadPool() : sCachedThreadPoolExecutor;
            }
        return sCachedThreadPoolExecutor;
    }

    /**
     * execute runnable with thread pool
     *
     * @param runnable to execute
     * @see #getThreadPool()
     */
    public static void execute(Runnable runnable) {
        if (runnable == null) return;

        getThreadPool().execute(runnable);
    }

    /**
     * @see #post(long, Handler, Runnable)
     */
    public static void post(Handler handler, Runnable runnable) {
        post(0, handler, runnable);
    }

    /**
     * @param delay    delay to schedule
     * @param handler  handler to handle runnable
     * @param runnable to run
     */
    public static void post(long delay, Handler handler, Runnable runnable) {
        if (handler == null) return;
        if (runnable == null) return;
        if (delay < 0) delay = Math.abs(delay);

        handler.postDelayed(runnable, delay);
    }


    /**
     * scheduled executor
     */
    private static final int MAX_SCHEDULE_COUNT = 5;
    private static ScheduledExecutorService sScheduledExecutor;


    /**
     * get schedule executor service
     *
     * @return executor
     */
    public static ScheduledExecutorService getScheduledExecutor() {
        if (sScheduledExecutor == null)
            synchronized (DU.class) {
                return sScheduledExecutor == null ? Executors.newScheduledThreadPool(MAX_SCHEDULE_COUNT)
                        : sScheduledExecutor;
            }

        return sScheduledExecutor;
    }

    /**
     * schedule a runnable of the period right now (in unit milliseconds)
     *
     * @param period   period
     * @param runnable
     */
    public static ScheduledFuture schedule(long period, final Runnable runnable) {
        return schedule(0, period, TimeUnit.MILLISECONDS, runnable);
    }

    /**
     * schedule a runnable of the period after delay (in unit milliseconds)
     *
     * @param delay    delay to post
     * @param period   period
     * @param runnable
     */
    public static ScheduledFuture schedule(long delay, long period, final Runnable runnable) {
        return schedule(delay, period, TimeUnit.MILLISECONDS, runnable);
    }

    /**
     * schedule a runnable of the period right now (in unit milliseconds)
     *
     * @param delay    delay to post
     * @param period   period
     * @param unit     time unit
     * @param runnable
     */
    public static ScheduledFuture schedule(long delay, long period, TimeUnit unit, final Runnable runnable) {
        if (runnable == null) return null;

        return getScheduledExecutor().scheduleAtFixedRate(runnable, delay, period, unit);
    }

}
