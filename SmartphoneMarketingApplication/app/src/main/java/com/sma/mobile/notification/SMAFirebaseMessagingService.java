package com.sma.mobile.notification;

/**
 * Created by AndroidBash on 20-Aug-16.
 */

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.fintechviet.android.sdk.FintechvietSdk;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.sma.mobile.R;
import com.sma.mobile.home.HomeActivity;
import com.sma.mobile.startup.StartupActivity;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Random;


public class SMAFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "FirebaseMessaging";
    private static final String MESSAGE = "message";
    private static final String IMAGE = "image";
    private static final String IMPRESSION_URL = "impressionUrl";
    private static final String CLICK_URL = "clickUrl";
    private static final String TRACKING_URL = "trackingUrl";
    private Bitmap bitmap;

    /**
     * Called when message is received.
     *
     * @param remoteMessage Object representing the message received from Firebase Cloud Messaging.
     */
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // There are two types of messages data messages and notification messages. Data messages are handled
        // here in onMessageReceived whether the app is in the foreground or background. Data messages are the type
        // traditionally used with GCM. Notification messages are only received here in onMessageReceived when the app
        // is in the foreground. When the app is in the background an automatically generated notification is displayed.
        // When the user taps on the notification they are returned to the app. Messages containing both notification
        // and data payloads are treated as notification messages. The Firebase console always sends notification
        // messages. For more see: https://firebase.google.com/docs/cloud-messaging/concept-options
        //
        Log.d(TAG, "Data: " + remoteMessage.getData());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }

        //The message which i send will have keys named [message, image, AnotherActivity] and corresponding values.
        //You can change as per the requirement.

        //message will contain the Push Message
        String message = remoteMessage.getData().get(MESSAGE);
        //imageUri will contain URL of the image to be displayed with Notification
        String id = remoteMessage.getData().get("id");
        String imageUri = remoteMessage.getData().get(IMAGE);
        String impressionUrl = remoteMessage.getData().get(IMPRESSION_URL);
        String clickUrl = remoteMessage.getData().get(CLICK_URL);
        String trackingUrl = remoteMessage.getData().get(TRACKING_URL);
        double latitude = 0.0;
        double longitude = 0.0;
        try {
            latitude = Double.parseDouble(remoteMessage.getData().get("latitude"));
            longitude = Double.parseDouble(remoteMessage.getData().get("longitude"));
        } catch (Exception exception) {

        }
        //To get a Bitmap image from the URL received
        bitmap = getBitmapfromUrl(imageUri);
        FirebaseMessagingResponse firebaseMessaging = new FirebaseMessagingResponse();
        firebaseMessaging.setId(id);
        firebaseMessaging.setLatitude(latitude);
        firebaseMessaging.setLongitude(longitude);
        firebaseMessaging.setImpressionUrl(impressionUrl);
        firebaseMessaging.setClickUrl(clickUrl);
        firebaseMessaging.setTrackingUrl(trackingUrl);
        firebaseMessaging.setType(remoteMessage.getData().get("type"));
        sendNotification(message, bitmap, firebaseMessaging);
    }


    /**
     * Create and show a simple notification containing the received FCM message.
     */

    private void sendNotification(String messageBody, Bitmap image, FirebaseMessagingResponse firebaseMessaging) {
        Intent intent = new Intent(this, HomeActivity.class);
        if (firebaseMessaging.getType().equalsIgnoreCase("AD_NOTIFICATION")) {
            intent = new Intent(this, StartupActivity.class);
        } else {
            intent = new Intent(this, HomeActivity.class);
        }
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        Bundle bundle = new Bundle();
        bundle.putInt("KEY_NAME_RESULT", 0);
        bundle.putParcelable(FirebaseMessagingResponse.class.getName(), firebaseMessaging);
        intent.putExtras(bundle);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.BigTextStyle bigText = new NotificationCompat.BigTextStyle();
        bigText.bigText(messageBody);
        bigText.setBigContentTitle("SMA");
        //bigText.setSummaryText(messageBody);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setLargeIcon(image)/*Notification icon image*/
                .setSmallIcon(R.drawable.icon_sma)
                .setContentTitle("SMA")
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);
        notificationBuilder.setStyle(bigText);
        notificationBuilder.setAutoCancel(true);
        notificationBuilder.setLights(Color.BLUE, 500, 500);
        long[] pattern = {500, 500, 500, 500, 500, 500, 500, 500, 500};
        notificationBuilder.setVibrate(pattern);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        notificationBuilder.setSound(alarmSound);
        //notificationBuilder.setStyle(new NotificationCompat.InboxStyle());
        // Save ad impression
        FintechvietSdk.getInstance().saveAdActivity(firebaseMessaging.getImpressionUrl());
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(new Random().nextInt(), notificationBuilder.build());
    }

    /*
    *To get a Bitmap image from the URL received
    * */
    public Bitmap getBitmapfromUrl(String imageUrl) {
        try {
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap bitmap = BitmapFactory.decodeStream(input);
            return bitmap;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            return null;
        }
    }
}


