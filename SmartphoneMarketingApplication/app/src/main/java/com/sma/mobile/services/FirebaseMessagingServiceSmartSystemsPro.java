package com.sma.mobile.services;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.content.LocalBroadcastManager;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.sma.mobile.utils.LogHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;


/**
 * Created by Ravi Tamada on 08/08/16.
 * www.androidhive.info
 */
public class FirebaseMessagingServiceSmartSystemsPro extends FirebaseMessagingService {

    private static final String TAG = FirebaseMessagingServiceSmartSystemsPro.class.getSimpleName();
//    private NotificationUtils notificationUtils;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        LogHelper.e(TAG, "From: " + remoteMessage.getFrom());
        if (remoteMessage == null) {
            return;
        }
        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            LogHelper.e(TAG, "Notification Body: " + remoteMessage.getNotification().getBody());
            handleNotification(remoteMessage.getNotification().getBody());
        }
        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            LogHelper.e(TAG, "Data Payload: " + remoteMessage.getData().toString());
            try {
                JSONObject json = new JSONObject(remoteMessage.getData().toString());
                handleDataMessage(json);
            } catch (Exception e) {
                LogHelper.e(TAG, "Exception: " + e.getMessage());
            }
        }
        sendBroadcastBadge();
    }

    /***
     *
     */
    private void sendBroadcastBadge() {
//        Intent intent = new Intent();
//        intent.setAction(RS3.action.BADGE_ACTION);
//        sendBroadcast(intent);
    }

    /***
     *
     * @param message
     */
    private void handleNotification(String message) {
//        if (!NotificationUtils.isAppIsInBackground(getApplicationContext())) {
//            // app is in foreground, broadcast the push message
//            Intent pushNotification = new Intent(Config.PUSH_NOTIFICATION);
//            pushNotification.putExtra("message", message);
//            LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);
//            // play notification sound
//            NotificationUtils notificationUtils = new NotificationUtils(getApplicationContext());
//            notificationUtils.playNotificationSound();
//        } else {
//            // If the app is in background, firebase itself handles the notification
//        }
    }

    /***
     *
     * @param data
     */
    private void handleDataMessage(JSONObject data) {
//        LogHelper.e(TAG, "push json: " + data.toString());
//        try {
//            JSONObject payload = data.getJSONObject("payload");
//            LogHelper.e(TAG, "payload: " + payload.toString());
//            String title = payload.isNull("Header") ? "SMART Systems Pro" : payload.getString("Header");
//            String message = payload.isNull("Content") ? "SMART Systems Pro" : payload.getString("Content");
//            String timestamp = payload.isNull("CreatedDate") ? "SMART Systems Pro" : payload.getString("CreatedDate");
//            LogHelper.e(TAG, "title: " + title);
//            LogHelper.e(TAG, "message: " + message);
//            LogHelper.e(TAG, "timestamp: " + timestamp);
////            if (!NotificationUtils.isAppIsInBackground(getApplicationContext())) {
//            // app is in foreground, broadcast the push message
//            Intent resultIntent = new Intent(getApplicationContext(), ConversationDetailedActivity.class);
//            resultIntent.setAction(Config.PUSH_NOTIFICATION);
//            resultIntent.putExtra(RS3.intent.RESPONSE_PAYLOAD, payload.toString());
//            LocalBroadcastManager.getInstance(this).sendBroadcast(resultIntent);
//            // play notification sound
//            NotificationUtils notificationUtils = new NotificationUtils(getApplicationContext());
//            notificationUtils.playNotificationSound();
//            showNotificationMessage(getApplicationContext(), title, message, timestamp, resultIntent);
////            } else {
////                // app is in background, show the notification in notification tray
////                Intent resultIntent = new Intent(getApplicationContext(), GamePipepanic.class);
////                resultIntent.putExtra("message", message);
////                // check for image attachment
////                if (TextUtils.isEmpty(imageUrl)) {
////                    showNotificationMessage(getApplicationContext(), title, message, timestamp, resultIntent);
////                } else {
////                    // image is present, show notification with image
////                    showNotificationMessageWithBigImage(getApplicationContext(), title, message, timestamp, resultIntent, imageUrl);
////                }
////            }
//        } catch (JSONException e) {
//            LogHelper.e(TAG, "Json Exception: " + e.getMessage());
//        } catch (Exception e) {
//            LogHelper.e(TAG, "Exception: " + e.getMessage());
//        }
    }

    private Intent getPreviousIntent() {
        Intent newIntent = null;
        final ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            final List<ActivityManager.AppTask> recentTaskInfos = activityManager.getAppTasks();
            if (!recentTaskInfos.isEmpty()) {
                for (ActivityManager.AppTask appTaskTaskInfo : recentTaskInfos) {
                    if (appTaskTaskInfo.getTaskInfo().baseIntent.getComponent().getPackageName().equals("com.smartsystemspro.sspschedule")) {
                        newIntent = appTaskTaskInfo.getTaskInfo().baseIntent;
                        newIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    }
                }
            }
        } else {
            final List<ActivityManager.RecentTaskInfo> recentTaskInfos = activityManager.getRecentTasks(1024, 0);
            if (!recentTaskInfos.isEmpty()) {
                for (ActivityManager.RecentTaskInfo recentTaskInfo : recentTaskInfos) {
                    if (recentTaskInfo.baseIntent.getComponent().getPackageName().equals("com.smartsystemspro.sspschedule")) {
                        newIntent = recentTaskInfo.baseIntent;
                        newIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    }
                }
            }
        }
        if (newIntent == null) newIntent = new Intent();
        return newIntent;
    }

    /**
     * Showing notification with text only
     */
    private void showNotificationMessage(Context context, String title, String message, String timeStamp, Intent intent) {
//        notificationUtils = new NotificationUtils(context);
//        Intent resultIntent = getPreviousIntent();
//        resultIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
//        notificationUtils.showNotificationMessage(title, message, timeStamp, resultIntent);
    }

    /**
     * Showing notification with text and image
     */
    private void showNotificationMessageWithBigImage(Context context, String title, String message, String timeStamp, Intent intent, String imageUrl) {
//        notificationUtils = new NotificationUtils(context);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        notificationUtils.showNotificationMessage(title, message, timeStamp, intent, imageUrl);
    }
}
