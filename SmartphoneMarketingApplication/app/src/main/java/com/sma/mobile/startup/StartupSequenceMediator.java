package com.sma.mobile.startup;

import android.content.Intent;

import com.sma.mobile.launcher.SplashScreen;

/**
 * Created by longtran on 14/01/2017.
 */

public class StartupSequenceMediator {

    private final StartupActivity mStartupActivity;

    public StartupSequenceMediator(final StartupActivity paramStartupActivity) {
        this.mStartupActivity = paramStartupActivity;
    }

    public void start() {
        Intent intent = new Intent();
        intent.setClassName(mStartupActivity, SplashScreen.class.getName());
        mStartupActivity.startActivity(intent);
        mStartupActivity.finish();
    }
}