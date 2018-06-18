/*
 * Copyright 2015 Google Inc. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.sourceforge.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.IBinder;

import com.floatingwidget.FloatingWidgetService;

/**
 * Created by QLong on 10/6/2017.
 * <p>
 * This is a service registers a broadcast receiver to listen for screen on/off events.
 * It is a very unfortunate service that must exist because we can't register for screen on/off
 * in the manifest.
 */
public class ScreenListenerService extends Service {
    /**
     * @param context
     * @return
     */
    private String checkNetworkStatus(Context context) {
        String networkStatus = "";
        final ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        //Check Wifi
        final android.net.NetworkInfo wifi = manager.getActiveNetworkInfo();
        //Check for mobile data
        final android.net.NetworkInfo mobile = manager.getActiveNetworkInfo();
        if (wifi.getType() == ConnectivityManager.TYPE_WIFI) {
            networkStatus = "wifi";
        } else if (mobile.getType() == ConnectivityManager.TYPE_MOBILE) {
            networkStatus = "mobileData";
        } else {
            networkStatus = "noNetwork";
        }
        return networkStatus;
    }

    /**
     *
     */
    private BroadcastReceiver mScreenStateBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Intent discoveryIntent = new Intent(context, FloatingWidgetService.class);
            if (intent.getAction().equals(Intent.ACTION_SCREEN_ON)) {
                context.stopService(discoveryIntent);
                SharedPreferences pref = getSharedPreferences("sma_application", 0);
                final int haftScreen = pref.getInt("haft_screen", 0);
                final int wifi = pref.getInt("wifi", 0);
                final int connect3g = pref.getInt("3g", 0);
                if (haftScreen > 0) {
                    if ((wifi > 0 && checkNetworkStatus(getApplicationContext()).equalsIgnoreCase("wifi"))
                            || connect3g > 0 && checkNetworkStatus(getApplicationContext()).equalsIgnoreCase("mobileData")) {
                        context.startService(discoveryIntent);
                    } else {
                        context.stopService(discoveryIntent);
                    }
                } else {
                    context.stopService(discoveryIntent);
                }
            } else if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF) ||
                    intent.getAction().equals(Intent.ACTION_USER_PRESENT) ||
                    intent.getAction().equals(Intent.ACTION_SHUTDOWN)) {
                context.stopService(discoveryIntent);
            }
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_SCREEN_ON);
        intentFilter.addAction(Intent.ACTION_SCREEN_OFF);
        intentFilter.addAction(Intent.ACTION_USER_PRESENT);
        intentFilter.addAction(Intent.ACTION_SHUTDOWN);
        registerReceiver(mScreenStateBroadcastReceiver, intentFilter);
    }

    @Override
    public void onDestroy() {
        unregisterReceiver(mScreenStateBroadcastReceiver);
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // Nothing should bind to this service
        return null;
    }
}
