package com.bbc.agsolutions;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;


public class MyFirebaseMessagingService extends FirebaseMessagingService {

//    Utils utils;

    @TargetApi(Build.VERSION_CODES.O)
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        sendMyNotification1(remoteMessage.getNotification().getBody(), remoteMessage.getNotification().getTitle());

    }
    @Override
    public void onNewToken(@NonNull String token) {
//        utils=new Utils(this);
//        String refreshedToken = token;
//        utils.ed.putString("device_token",refreshedToken).commit();
        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // FCM registration token to your app server.
//        sendRegistrationToServer(token);
    }
    private void sendMyNotification1(String message, String title) {
        int notification_id = 1;
        AudioManager manager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        manager.setStreamVolume(AudioManager.STREAM_MUSIC, 10, 0);

        Intent intent = new Intent(this, SplashScreen.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) {
            pendingIntent = PendingIntent.getActivity
                    (this, 0, intent, PendingIntent.FLAG_MUTABLE);
        } else {
            pendingIntent = PendingIntent.getActivity
                    (this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        }
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            String CHANNEL_ID = "my_channel_01";// The id of the channel.
            CharSequence name = getResources().getString(R.string.app_name);// The user-visible name of the channel.
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
            mChannel.setShowBadge(true);
            Notification notification1 = new Notification.Builder(getApplicationContext())
                    .setContentTitle(title)
                    .setContentText(message)
                    .setStyle(new Notification.BigTextStyle().bigText(message))
                    .setSmallIcon(R.mipmap.bbc_logo)
//                    .setColor(getResources().getColor(R.color.colorPrimary))
                    .setChannelId(CHANNEL_ID)
                    .setSound(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.sample))
                    .setContentIntent(pendingIntent)
//                    .setAutoCancel(true)
//                    .setDefaults(NotificationCompat.PRIORITY_HIGH)
                    .build();
//            notificationBuilder.setVibrate(new long[]{1000, 1000, 1000, 1000, 1000});
            notificationBuilder.setSound(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.sample));
            NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            mNotificationManager.createNotificationChannel(mChannel);
            mNotificationManager.notify(notification_id, notification1);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            String CHANNEL_ID = "my_channel_01";// The id of the channel.
            CharSequence name = getResources().getString(R.string.app_name);// The user-visible name of the channel.
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
            mChannel.setShowBadge(true);
            Notification notification1 = new Notification.Builder(getApplicationContext())
                    .setContentTitle(title)
                    .setContentText(message)
                    .setStyle(new Notification.BigTextStyle().bigText(message))
                    .setSmallIcon(R.mipmap.bbc_logo)
//                    .setColor(getResources().getColor(R.color.colorPrimary))
                    .setChannelId(CHANNEL_ID)
                    .setSound(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.sample))
                    .setContentIntent(pendingIntent)
//                    .setAutoCancel(true)
//                    .setDefaults(NotificationCompat.PRIORITY_HIGH)
                    .build();
//            notificationBuilder.setVibrate(new long[]{1000, 1000, 1000, 1000, 1000});
            notificationBuilder.setSound(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.sample));
            NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            mNotificationManager.createNotificationChannel(mChannel);
            mNotificationManager.notify(notification_id, notification1);
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                notificationBuilder.setSmallIcon(R.mipmap.bbc_logo)
                        .setContentTitle(title)
                        .setContentText(message)
                        .setStyle(new NotificationCompat.BigTextStyle().bigText(message))
//                        .setColor(getResources().getColor(R.color.colorPrimary))
                        .setAutoCancel(true)
//                        .setPriority(NotificationCompat.PRIORITY_HIGH)
//                        .setDefaults(NotificationCompat.PRIORITY_HIGH)
//                        .setNumber(0)
                        .setSound(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.sample))
                        .setContentIntent(pendingIntent);
//                notificationBuilder.setVibrate(new long[]{1000, 1000, 1000, 1000, 1000});
            } else {
                notificationBuilder.setSmallIcon(R.mipmap.bbc_logo)
//                        .setColor(getResources().getColor(R.color.colorPrimary))
                        .setContentTitle(title)
                        .setContentText(message)
                        .setStyle(new NotificationCompat.BigTextStyle().bigText(message))
                        .setAutoCancel(true)
//                        .setPriority(NotificationCompat.PRIORITY_HIGH)
//                        .setDefaults(NotificationCompat.PRIORITY_HIGH)
                        .setSound(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.sample))
//                        .setNumber(0)
                        .setContentIntent(pendingIntent);
//                notificationBuilder.setVibrate(new long[]{1000, 1000, 1000, 1000, 1000});
            }
            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(1, notificationBuilder.build());
        }
    }
}