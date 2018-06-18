package com.sma.mobile.jobs;



import android.app.Notification;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.NotificationCompat;
import android.widget.Toast;

/**
 * Created by longtran on 08/04/2018.
 *
 * AlarmReceiver handles the broadcast message and generates Notification
 */

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent intentService = new Intent(context, LocationUpdatesService.class);
        context.startService(intentService);
    }
}
