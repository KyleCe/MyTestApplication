package com.ce.game.myapplication.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.ServiceConnection;
import android.util.Log;

import junit.framework.Assert;

import java.io.Closeable;
import java.io.IOException;
import java.util.concurrent.ExecutorService;

/**
 * Created by KyleCe on 2016/8/1.
 *
 * @author: KyleCe
 */
public class EndingU {

    public static void closeSilently(Closeable... closeables) {
        for (Closeable c : closeables) closeSilently(c);
    }

    public static void closeSilently(Closeable c) {
        if (c == null) return;
        try {
            c.close();
        } catch (IOException t) {
            Log.w("close fail ", t);
        }
    }

    public static <B extends BroadcastReceiver> void unregisterReceiverSafelyAndSetToNull(Context ctx, B b) {
        if (b == null) return;

        Assert.assertNotNull(ctx);

        try {
            ctx.unregisterReceiver(b);
            b = null;
        } catch (IllegalArgumentException lae) {
            lae.printStackTrace();
        }
    }

    public static <S extends ServiceConnection> void unbindServiceSafelyAndSetToNull(Context ctx, S s) {
        if (s == null) return;

        Assert.assertNotNull(ctx);

        try {
            ctx.unbindService(s);
            s = null;
        } catch (IllegalArgumentException lae) {
            lae.printStackTrace();
        }
    }

    public static <E extends ExecutorService> void shutDownExecutorServiceSafely(E executor) {
        try {
            if (!executor.isShutdown())
                executor.shutdown();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static <B extends BroadcastReceiver> void abortBroadcastSafely(B b) {
        Assert.assertNotNull(b);

        try {
            b.abortBroadcast();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }
}
