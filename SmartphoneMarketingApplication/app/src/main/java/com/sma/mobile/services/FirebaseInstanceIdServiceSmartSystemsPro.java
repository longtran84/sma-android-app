package com.sma.mobile.services;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.content.LocalBroadcastManager;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.sma.mobile.utils.LogHelper;


/**
 * Created by Ravi Tamada on 08/08/16.
 * www.androidhive.info
 */
public class FirebaseInstanceIdServiceSmartSystemsPro extends FirebaseInstanceIdService {
    private static final String TAG = FirebaseInstanceIdServiceSmartSystemsPro.class.getSimpleName();

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        System.err.println("--------------refreshedToken-----------" + refreshedToken);
        // Saving reg id to shared preferences
        storeRegistrationTokenSharedPreferences(refreshedToken);
        // sending reg id to your server
        sendRegistrationToServer(refreshedToken);
        // Notify UI that registration has completed, so the progress indicator can be hidden.
//        Intent registrationComplete = new Intent(Config.REGISTRATION_COMPLETE);
//        registrationComplete.putExtra("token", refreshedToken);
//        LocalBroadcastManager.getInstance(this).sendBroadcast(registrationComplete);
    }

    /****
     *
     * @param token
     */
    private void sendRegistrationToServer(final String token) {
        // sending gcm token to server
        LogHelper.e(TAG, "sendRegistrationToServer: " + token);
    }

    /***
     *
     * @param token
     */
    private void storeRegistrationTokenSharedPreferences(String token) {
//        SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
//        SharedPreferences.Editor editor = pref.edit();
//        editor.putString(Config.REGISTRATION_TOKENS, token);
//        editor.commit();
    }
}
