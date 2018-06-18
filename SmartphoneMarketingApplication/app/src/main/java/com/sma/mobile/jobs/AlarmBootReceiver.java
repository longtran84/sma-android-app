package com.sma.mobile.jobs;



import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by longtran on 08/04/2018.
 */

public class AlarmBootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
            //only enabling one type of notifications for demo purposes
            NotificationHelper.scheduleRepeatingElapsedNotification(context);
        }
    }
}
