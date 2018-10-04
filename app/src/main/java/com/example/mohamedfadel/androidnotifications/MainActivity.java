package com.example.mohamedfadel.androidnotifications;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import static com.example.mohamedfadel.androidnotifications.App.CHANNEL_1_ID;
import static com.example.mohamedfadel.androidnotifications.App.CHANNEL_2_ID;

public class MainActivity extends AppCompatActivity {

    NotificationManagerCompat notificationManager;
    EditText titleEditText;
    EditText messageEditText;
    MediaSessionCompat mediaSession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        notificationManager = NotificationManagerCompat.from(this);
        titleEditText = findViewById(R.id.edt_title);
        messageEditText = findViewById(R.id.edt_message);
        mediaSession = new MediaSessionCompat(this, "media");
    }

    public void sendOnChannel1(View view) {
        String title = titleEditText.getText().toString().trim();
        String message = messageEditText.getText().toString().trim();

        Intent activityIntent = new Intent(this, MainActivity.class);
        PendingIntent activityPendingIntent = PendingIntent.getActivity(this, 0, activityIntent, 0);

        Intent receiverIntent = new Intent(this, MyReciever.class);
        receiverIntent.putExtra("toastMessage", message);
        PendingIntent receiverPendingIntent = PendingIntent.getBroadcast(this, 0, receiverIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Bitmap logo = BitmapFactory.decodeResource(getResources(), R.drawable.logo);

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_one)
                .setContentText(message)
                .setContentTitle(title)
                .setLargeIcon(logo)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(getString(R.string.cotent))
                        .setSummaryText("Notification summery")
                        .setBigContentTitle("Big content title"))
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_ALARM)
                .setColor(Color.CYAN)
                .setContentIntent(activityPendingIntent)
                .setAutoCancel(true)
                .setOnlyAlertOnce(true)
                .addAction(R.drawable.ic_toast, "Toast", receiverPendingIntent)
                .build();
        notificationManager.notify(1, notification);
    }

    public void sendOnChannel2(View view) {
        String title = titleEditText.getText().toString().trim();
        String message = messageEditText.getText().toString().trim();
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_2_ID)
                .setSmallIcon(R.drawable.ic_two)
                .setContentText(message)
                .setContentTitle(title)
                .setStyle(new NotificationCompat.InboxStyle()
                        .addLine("This is line 1")
                        .addLine("This is line 2")
                        .addLine("This is line 3")
                        .addLine("This is line 4")
                        .addLine("This is line 5")
                        .addLine("This is line 6")
                        .addLine("This is line 7")
                        .setSummaryText("Notification summery")
                        .setBigContentTitle("Big content title"))
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .build();
        notificationManager.notify(2, notification);
    }

    public void sendOnChannel1Big(View view) {
        String title = titleEditText.getText().toString().trim();
        String message = messageEditText.getText().toString().trim();

        Intent activityIntent = new Intent(this, MainActivity.class);
        PendingIntent activityPendingIntent = PendingIntent.getActivity(this, 0, activityIntent, 0);

        Bitmap bigPicture = BitmapFactory.decodeResource(getResources(), R.drawable.big_logo);

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_one)
                .setContentText(message)
                .setContentTitle(title)
                .setLargeIcon(bigPicture)
                .setStyle(new NotificationCompat.BigPictureStyle()
                        .bigPicture(bigPicture)
                        .bigLargeIcon(null))
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_ALARM)
                .setColor(Color.CYAN)
                .setContentIntent(activityPendingIntent)
                .setAutoCancel(true)
                .setOnlyAlertOnce(true)
                .build();
        notificationManager.notify(1, notification);
    }

    public void sendMediaNotification(View view) {
        String title = titleEditText.getText().toString().trim();
        String message = messageEditText.getText().toString().trim();
        Bitmap cover = BitmapFactory.decodeResource(getResources(), R.drawable.cover);

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_2_ID)
                .setSmallIcon(R.drawable.ic_two)
                .setContentText("It's Party Time")
                .setContentTitle("JOE JONAS")
                .setLargeIcon(cover)
                .addAction(R.drawable.ic_dislike, "Dislike", null)
                .addAction(R.drawable.ic_previous, "Previous", null)
                .addAction(R.drawable.ic_pause, "Pause", null)
                .addAction(R.drawable.ic_next, "Next", null)
                .addAction(R.drawable.ic_like, "Like", null)
                .setStyle(new android.support.v4.media.app.NotificationCompat.MediaStyle()
                        .setShowActionsInCompactView(1, 2, 3)
                .setMediaSession(mediaSession.getSessionToken()))
                .setSubText("Hotel Transylvania 3")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .build();
        notificationManager.notify(2, notification);
    }

    public void showDownloadingNotification(View view) {
        final int MAX_VALUE = 100;

        final NotificationCompat.Builder notification = new NotificationCompat.Builder(this, CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_two)
                .setContentTitle("Download")
                .setContentText("Download in progress")
                .setOngoing(true)
                .setOnlyAlertOnce(true)
                .setProgress(MAX_VALUE, 0, false);

        notificationManager.notify(3, notification.build());

        new Thread(new Runnable() {
            @Override
            public void run() {
                SystemClock.sleep(2000);
                for (int i = 0; i < MAX_VALUE; i+= 10){
                    notification.setProgress(MAX_VALUE, i, false);
                    notificationManager.notify(3, notification.build());
                }
                notification.setProgress(0, 0, false);
                notification.setContentText("Download finished");
                notification.setOngoing(false);
                notificationManager.notify(3, notification.build());
            }
        }).start();

    }
}
