package com.ce.game.myapplication.notification;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class NotificationWatcherService extends Service {
    public NotificationWatcherService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return null;
    }
}
