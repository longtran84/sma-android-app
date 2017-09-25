package com.sma.mobile.startup;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.fintechviet.android.sdk.FintechvietSdk;
import com.google.firebase.messaging.FirebaseMessaging;
import com.sma.mobile.notification.AdViewActivity;

/**
 * Created by longtran on 14/01/2017.
 */

public class StartupActivity extends Activity {

    private final StartupSequenceMediator startupSequenceMediator;

    private static final String CLICK_URL = "clickUrl";

    private static final String TRACKING_URL = "trackingUrl";

    public StartupActivity() {
        startupSequenceMediator = new StartupSequenceMediator(this);
    }

    public StartupSequenceMediator getStartupMediator() {
        return this.startupSequenceMediator;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new StartupView(this));
        if (getIntent().getExtras() != null) {
            for (String key : getIntent().getExtras().keySet()) {
                String value = getIntent().getExtras().getString(key);
                if (key.equals(CLICK_URL)) {
                    Intent intent = new Intent(this, AdViewActivity.class);
                    intent.putExtra(CLICK_URL, value);
                    startActivity(intent);
                    // Save ad click event
                    String clickTrackingUrlValue = getIntent().getExtras().getString(TRACKING_URL);
                    FintechvietSdk.getInstance().saveAdActivity(clickTrackingUrlValue + "&deviceToken=" + "token_android");
                    finish();
                }

            }
        }
    }
}
