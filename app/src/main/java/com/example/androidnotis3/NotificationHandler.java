package com.example.androidnotis3;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class NotificationHandler {
    private Context context;
    private static int counter;


    private NotificationCompat.Builder mBuilder;

    //1. Notification channel
    //2. Notification builder
    //3. Notification Manager

    private static final String CHANNEL_ID = "Mozooni_coding";
    private static final String CHANNEL_NAME = "Mozooni coding";
    private static final String CHANNEL_DESC = "Mozooni coding Notiser";


    public NotificationHandler(Context context)
    {
        this.context = context;
        //Used by runnable to show several notifications at once. -> Used as ID for notificationManager
        counter = 0;

        initNotificationChannel();
    }

    public void sendNotification(String title, String contentText)
    {
        mBuilder =
                new NotificationCompat.Builder(context, CHANNEL_ID)
                        .setSmallIcon(R.drawable.ic_notis)
                        .setContentTitle(title)
                        .setContentText(contentText)
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat mNotificationMgr = NotificationManagerCompat.from(context);
        //First in parameter has to be unique, if two notifications with 1 are sent, only one will show.
        mNotificationMgr.notify(counter++, mBuilder.build());
    }

    private boolean initNotificationChannel()
    {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription(CHANNEL_DESC);
            NotificationManager manager = context.getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
            return true;
        } else return false;
    }
}
