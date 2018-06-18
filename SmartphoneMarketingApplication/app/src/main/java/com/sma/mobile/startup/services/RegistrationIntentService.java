package com.sma.mobile.startup.services;

import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;

import com.google.firebase.iid.FirebaseInstanceId;
import com.sma.mobile.utils.LogHelper;
import com.sma.mobile.utils.firebasenotifications.Config;

/**
 * Created by longtran on 21/04/2017.
 */

public class RegistrationIntentService extends IntentService {

    private static final String TAG = "RegIntentService";

    /***
     *
     */
    public RegistrationIntentService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String token = FirebaseInstanceId.getInstance().getToken();
        // Saving reg id to shared preferences
        //todo
        storeRegistrationTokenSharedPreferences(token);
        LogHelper.i(TAG, "FCM Registration Token: " + token);
    }

    /***
     *
     * @param token
     */
    private void storeRegistrationTokenSharedPreferences(String token) {
        SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(Config.REGISTRATION_TOKENS, token);
        editor.commit();
    }
}
