package com.pixelart.week3daily2services;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;


public class MusicPlayerService extends Service implements MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener, MediaPlayer.OnErrorListener {
    private static final String TAG = "MusicPlayerService";
    private static final String CHANNEL_ID = "myNotificationChannel";

    private String STREAM_URL = "https://www.mfiles.co.uk/mp3-downloads/";

    private MediaPlayer mediaPlayer;
    private String audioUrl;

    public MusicPlayerService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();

        mediaPlayer = new MediaPlayer();
        mediaPlayer.setOnPreparedListener(this);
        mediaPlayer.setOnCompletionListener(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        audioUrl = intent.getStringExtra("url");

        mediaPlayer.reset();
        if (!mediaPlayer.isPlaying())
        {
            try {
                mediaPlayer.setDataSource(STREAM_URL+audioUrl);
                mediaPlayer.prepareAsync();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        initNotification(intent);
        if (intent.getAction() != null)
        {
            switch (intent.getAction()) {
                case "stopMusic":
                    mediaPlayer.stop();
                    mediaPlayer.release();
                    stopSelf();
                    break;
            }
        }

        Log.d(TAG, "onStartCommand");

        return START_STICKY;
    }

    private void initNotification(Intent intent)
    {
        CharSequence contentTitle = "";
        CharSequence contentText = "";

        switch (intent.getAction())
        {
            case "audio1":
                contentTitle = "Playing Toccata and Fugue";
                contentText = "Toccata and Fugue in Dm for Organ";
                break;

            case "audio2":
                contentTitle = "Playing book1 Fugue no.24";
                contentText = "Fugue No. 24 - arranged for String Quartet";
                break;

        }



        Intent notificationIntent = new Intent(this, MusicPlayerService.class);
        notificationIntent.putExtra("stopMusic", "stopMusic");
        notificationIntent.setAction("stopMusic");
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0, notificationIntent, 0);

        PendingIntent stopMusicIntent = PendingIntent.getActivity(this, 0 , notificationIntent, 0);

        Notification notificationBuilder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_play)
                .setContentTitle(contentTitle).setContentText(contentText)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent).addAction(0, "Stop Music", stopMusicIntent)
                .setAutoCancel(true).build();

        startForeground(1,notificationBuilder);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null)
        {
            if (mediaPlayer.isPlaying())
                mediaPlayer.stop();
        }
        mediaPlayer.release();

        Log.d(TAG, "onDestroy");
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        Log.d(TAG, "onCompletion");
        if (mediaPlayer.isPlaying())
            mediaPlayer.stop();
        stopSelf();
    }

    @Override
    public void onPrepared(MediaPlayer mp) {

        Log.d(TAG, "onPrepared");
        if (!mediaPlayer.isPlaying()) {
            mediaPlayer.start();
        }
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {

        switch (what) {
            case MediaPlayer.MEDIA_ERROR_NOT_VALID_FOR_PROGRESSIVE_PLAYBACK:
                Toast.makeText(this,
                        "MEDIA ERROR NOT VALID FOR PROGRESSIVE PLAYBACK " + extra,
                        Toast.LENGTH_SHORT).show();
                break;
            case MediaPlayer.MEDIA_ERROR_SERVER_DIED:
                Toast.makeText(this, "MEDIA ERROR SERVER DIED " + extra,
                        Toast.LENGTH_SHORT).show();
                break;
            case MediaPlayer.MEDIA_ERROR_UNKNOWN:
                Toast.makeText(this, "MEDIA ERROR UNKNOWN " + extra,
                        Toast.LENGTH_SHORT).show();
                break;
        }
        return false;
    }
}
