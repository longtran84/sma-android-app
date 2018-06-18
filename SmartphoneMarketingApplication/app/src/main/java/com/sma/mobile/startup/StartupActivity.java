package com.sma.mobile.startup;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.fintechviet.android.sdk.FintechvietSdk;
import com.google.firebase.messaging.FirebaseMessaging;
import com.sma.mobile.launcher.SplashScreen;
import com.sma.mobile.notification.AdViewActivity;
import com.sma.mobile.notification.FirebaseMessagingResponse;
import com.sma.mobile.startup.services.RegistrationIntentService;
import com.sma.mobile.utils.firebasenotifications.Config;

/**
 * Created by longtran on 14/01/2017.
 */

public class StartupActivity extends Activity {
    private static final String CLICK_URL = "clickUrl";
    private static final String TRACKING_URL = "trackingUrl";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startService(new Intent(StartupActivity.this, RegistrationIntentService.class));
        subscribeToPushService();
        // Retrieve sent bundle
        Bundle bundle = this.getIntent().getExtras();
        if (null != bundle) {
            FirebaseMessagingResponse firebaseMessaging = bundle.getParcelable(FirebaseMessagingResponse.class.getName());
            if (null != firebaseMessaging) {
                Intent intentClick = new Intent(this, AdViewActivity.class);
                intentClick.putExtra(CLICK_URL, firebaseMessaging.getClickUrl());
                startActivity(intentClick);
                SharedPreferences pref = getSharedPreferences(Config.SHARED_PREF, 0);
                String registrationToken = pref.getString(Config.REGISTRATION_TOKENS, "ERROR");
                FintechvietSdk.getInstance().saveAdActivity(firebaseMessaging.getTrackingUrl() + "&deviceToken=" + registrationToken);
                finish();
            } else {
                Intent intent = new Intent();
                intent.setClassName(this, SplashScreen.class.getName());
                startActivity(intent);
                finish();
            }

        } else {
            Intent intent = new Intent();
            intent.setClassName(this, SplashScreen.class.getName());
            startActivity(intent);
            finish();
        }
    }

    /**
     *
     */
    private void subscribeToPushService() {
        FirebaseMessaging.getInstance().subscribeToTopic("ads");
    }
}
