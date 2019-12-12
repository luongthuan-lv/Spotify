package vn.edu.poly.spotify;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.media.session.MediaSessionCompat;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.bumptech.glide.Glide;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import vn.edu.poly.spotify.Services.NotificationActionService;
import vn.edu.poly.spotify.ui.music.Music;
import wseemann.media.FFmpegMediaMetadataRetriever;

public class CreateNotification {

    public static final String CHANNEL_ID = "channel1";
    public static final String ACTION_PREVIUOS = "actionprevious";
    public static final String ACTION_PLAY = "actionplay";
    public static final String ACTION_NEXT = "actionnext";
    public static Notification notification;


    public static void createNotification(Context context, Music music, int playbutton, int pos, int size) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
            MediaSessionCompat mediaSessionCompat = new MediaSessionCompat(context, "tag");


            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), Uri.parse(music.getImage()));

                Bitmap myBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ms);
                Log.e("555555555", bitmap + "");

                PendingIntent pendingIntentPrevious;
                int drw_previous;
                if (pos == 0) {
                    pendingIntentPrevious = null;
                    drw_previous = 0;
                } else {
                    Intent intentPrevious = new Intent(context, NotificationActionService.class)
                            .setAction(ACTION_PREVIUOS);
                    pendingIntentPrevious = PendingIntent.getBroadcast(context, 0,
                            intentPrevious, PendingIntent.FLAG_UPDATE_CURRENT);
                    drw_previous = R.drawable.previous;
                }

                Intent intentPlay = new Intent(context, NotificationActionService.class)
                        .setAction(ACTION_PLAY);
                PendingIntent pendingIntentPlay = PendingIntent.getBroadcast(context, 0,
                        intentPlay, PendingIntent.FLAG_UPDATE_CURRENT);

                PendingIntent pendingIntentNext;
                int drw_next;
                if (pos == size) {
                    pendingIntentNext = null;
                    drw_next = 0;
                } else {
                    Intent intentNext = new Intent(context, NotificationActionService.class)
                            .setAction(ACTION_NEXT);
                    pendingIntentNext = PendingIntent.getBroadcast(context, 0,
                            intentNext, PendingIntent.FLAG_UPDATE_CURRENT);
                    drw_next = R.drawable.next;
                }

                //create notification
                notification = new NotificationCompat.Builder(context, CHANNEL_ID)
                        .setSmallIcon(R.drawable.favorite1)
                        .setContentTitle(music.getNamesong())
                        .setContentText(music.getNameartist())
                        .setLargeIcon(bitmap)
                        .setOnlyAlertOnce(true)//show notification for only first time
                        .setShowWhen(false)
                        .addAction(drw_previous, "Previous", pendingIntentPrevious)
                        .addAction(playbutton, "Play", pendingIntentPlay)
                        .addAction(drw_next, "Next", pendingIntentNext)
                        .setStyle(new androidx.media.app.NotificationCompat.MediaStyle()
                                .setShowActionsInCompactView(0, 1, 2)
                                .setMediaSession(mediaSessionCompat.getSessionToken()))
                        .setPriority(NotificationCompat.PRIORITY_LOW)
                        .build();

                notificationManagerCompat.notify(1, notification);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
