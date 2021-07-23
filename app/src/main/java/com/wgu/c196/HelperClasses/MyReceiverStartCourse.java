package com.wgu.c196.HelperClasses;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import com.wgu.c196.R;

import androidx.core.app.NotificationCompat;

public class MyReceiverStartCourse extends BroadcastReceiver {

    private String channel_id = "test";
    private static int notificationID;

    @Override
    public void onReceive(Context context, Intent intent) {
        //String startNotificationText = intent.getStringExtra("courseTitle")
        //Toast.makeText(context, intent.getStringExtra("key"), Toast.LENGTH_LONG).show();
        createNotificationChannel(context, channel_id);
        Notification myNotification = new NotificationCompat.Builder(context, channel_id)
                .setSmallIcon(R.drawable.schoolnotification)
                .setContentTitle("Important Course Notification")
                .setContentText(intent.getStringExtra("courseTitle") + " is starting today!")
//                .setStyle(new NotificationCompat.BigTextStyle()
//                        .bigText("Much longer text that cannot fit one line..."))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT).build();

        //Log.i("title", intent.getStringExtra("courseTitle"));
        NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(notificationID++, myNotification);
        // TODO: This method is called when the BroadcastReceiver is receiving
    }


    private void createNotificationChannel(Context context, String CHANNEL_ID) {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = context.getResources().getString(R.string.channel_name);
            String description = context.getResources().getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}