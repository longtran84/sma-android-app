package com.sma.mobile.jobs;

import android.app.IntentService;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.location.Location;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import com.fintechviet.android.sdk.FintechvietSdk;
import com.fintechviet.android.sdk.listener.JCallback;
import com.sma.mobile.R;
import com.sma.mobile.home.HomeActivity;
import com.sma.mobile.utils.LogHelper;
import com.sma.mobile.utils.firebasenotifications.Config;

import io.nlopez.smartlocation.OnLocationUpdatedListener;
import io.nlopez.smartlocation.SmartLocation;
import io.nlopez.smartlocation.location.providers.LocationGooglePlayServicesWithFallbackProvider;
import retrofit2.Call;
import retrofit2.Response;

/**
 * A bound and started service that is promoted to a foreground service when location updates have
 * been requested and all clients unbind.
 * <p>
 * For apps running in the background on "O" devices, location is computed only once every 10
 * minutes and delivered batched every 30 minutes. This restriction applies even to apps
 * targeting "N" or lower which are run on "O" devices.
 * <p>
 * This sample show how to use a long-running service for location updates. When an activity is
 * bound to this service, frequent location updates are permitted. When the activity is removed
 * from the foreground, the service promotes itself to a foreground service, and location updates
 * continue. When the activity comes back to the foreground, the foreground service stops, and the
 * notification assocaited with that service is removed.
 */
public class LocationUpdatesService extends Service {
    private static final String TAG = LocationUpdatesService.class.getSimpleName();
    /**
     * The name of the channel for notifications.
     */
    private static final String CHANNEL_ID = "channel_01";
    /**
     * The identifier for the notification displayed for the foreground service.
     */
    private static final String PACKAGE_NAME =
            "com.sma.mobile.jobs";
    private static final int NOTIFICATION_ID = 12345678;

    static final String ACTION_BROADCAST = PACKAGE_NAME + ".broadcast";

    static final String EXTRA_LOCATION = PACKAGE_NAME + ".location";
    private static final String EXTRA_STARTED_FROM_NOTIFICATION = PACKAGE_NAME +
            ".started_from_notification";

    private LocationGooglePlayServicesWithFallbackProvider provider;
    private SmartLocation smartLocation;

    /**
     * Class used for the client Binder.  Since this service runs in the same process as its
     * clients, we don't need to deal with IPC.
     */
    public class LocalBinder extends Binder {
        LocationUpdatesService getService() {
            return LocationUpdatesService.this;
        }
    }

    /**
     * Used to check whether the bound activity has really gone away and not unbound as part of an
     * orientation change. We create a foreground service notification only if the former takes
     * place.
     */
    private boolean mChangingConfiguration = false;

    private final IBinder mBinder = new LocalBinder();

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mChangingConfiguration = true;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // Called when a client (MainActivity in case of this sample) comes to the foreground
        // and binds with this service. The service should cease to be a foreground service
        // when that happens.
        LogHelper.i(TAG, "in onBind()");
        //stopForeground(true);
        mChangingConfiguration = false;
        return mBinder;
    }

    @Override
    public void onRebind(Intent intent) {
        // Called when a client (MainActivity in case of this sample) returns to the foreground
        // and binds once again with this service. The service should cease to be a foreground
        // service when that happens.
        LogHelper.i(TAG, "in onRebind()");
        //stopForeground(true);
        mChangingConfiguration = false;
        super.onRebind(intent);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        LogHelper.i(TAG, "Last client unbound from service");

        // Called when the last client (MainActivity in case of this sample) unbinds from this
        // service. If this method is called due to a configuration change in MainActivity, we
        // do nothing. Otherwise, we make this service a foreground service.
        if (!mChangingConfiguration /*&& Utils.requestingLocationUpdates(this)*/) {
            LogHelper.i(TAG, "Starting foreground service");
            /*
            // TODO(developer). If targeting O, use the following code.
            if (Build.VERSION.SDK_INT == Build.VERSION_CODES.O) {
                mNotificationManager.startServiceInForeground(new Intent(this,
                        LocationUpdatesService.class), NOTIFICATION_ID, getNotification());
            } else {
                startForeground(NOTIFICATION_ID, getNotification());
            }
             */
            startForeground(NOTIFICATION_ID, getNotification());
        }
        return true; // Ensures onRebind() is called when a client re-binds.
    }

    @Override
    public void onCreate() {
        super.onCreate();
        LogHelper.i(TAG, "onCreate");
        SmartLocation.with(getApplicationContext()).location().stop();
        LogHelper.d(TAG, "Location stopped!");
        SmartLocation.with(getApplicationContext()).activity().stop();
        LogHelper.d(TAG, "Activity Recognition stopped!");
        SmartLocation.with(getApplicationContext()).geofencing().stop();
        LogHelper.d(TAG, "Geofencing stopped!");
        provider = new LocationGooglePlayServicesWithFallbackProvider(getApplicationContext());
        smartLocation = new SmartLocation.Builder(getApplicationContext()).logging(true).build();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LogHelper.i(TAG, "onStartCommand flags startId", flags, startId);
        Location lastLocation = SmartLocation.with(getApplicationContext()).location().getLastLocation();
        if (null == lastLocation) {
            lastLocation = new Location("");
            lastLocation.setLatitude(21.03187);
            lastLocation.setLongitude(105.78797);
        }
        SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
        String registrationToken = pref.getString(Config.REGISTRATION_TOKENS, "ERROR");
        String deviceToken = pref.getString(Config.DEVICE_SERIAL, "ERROR");
        smartLocation.location(provider).start(new OnLocationUpdatedListener() {
            @Override
            public void onLocationUpdated(Location location) {
//                Toast.makeText(getApplicationContext(), "onLocationUpdated " + location,
//                        Toast.LENGTH_LONG).show();
            }
        });
        if (SmartLocation.with(getApplicationContext()).location().state().isNetworkAvailable()) {
            FintechvietSdk.getInstance().checkAdLocationsNearBy(deviceToken, registrationToken, lastLocation.getLongitude(), lastLocation.getLatitude(), new JCallback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    if (response.code() == 200) {

                    }
//                    Toast.makeText(getApplicationContext(), response + "----" + lastLocation,
//                            Toast.LENGTH_LONG).show();
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    t.printStackTrace();
                }
            });
        }
//        Toast.makeText(getApplicationContext(), "onStartCommand",
//                Toast.LENGTH_LONG).show();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        SmartLocation.with(getApplicationContext()).location().stop();
        LogHelper.d(TAG, "Location stopped!");
        SmartLocation.with(getApplicationContext()).activity().stop();
        LogHelper.d(TAG, "Activity Recognition stopped!");
        SmartLocation.with(getApplicationContext()).geofencing().stop();
        LogHelper.d(TAG, "Geofencing stopped!");
    }

    /**
     * Returns the {@link android.support.v4.app.NotificationCompat} used as part of the foreground service.
     */
    private Notification getNotification() {
        Intent intent = new Intent(this, LocationUpdatesService.class);

        CharSequence text = "Utils.getLocationText(mLocation)";

        // Extra to help us figure out if we arrived in onStartCommand via the notification or not.
        intent.putExtra(EXTRA_STARTED_FROM_NOTIFICATION, true);

        // The PendingIntent that leads to a call to onStartCommand() in this service.
        PendingIntent servicePendingIntent = PendingIntent.getService(this, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        // The PendingIntent to launch activity.
        PendingIntent activityPendingIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, HomeActivity.class), 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .addAction(R.mipmap.ic_launcher, getString(R.string.app_name),
                        activityPendingIntent)
                .addAction(R.drawable.billing_icon, getString(R.string.app_name),
                        servicePendingIntent)
                .setContentText(text)
                .setContentTitle("Utils.getLocationTitle(this)")
                .setOngoing(true)
                .setPriority(Notification.PRIORITY_HIGH)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setTicker(text)
                .setWhen(System.currentTimeMillis());

        // Set the Channel ID for Android O.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder.setChannelId(CHANNEL_ID); // Channel ID
        }

        return builder.build();
    }
}
