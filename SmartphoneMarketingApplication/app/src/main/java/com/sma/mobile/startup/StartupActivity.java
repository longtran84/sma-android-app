package com.sma.mobile.startup;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by longtran on 14/01/2017.
 */

public class StartupActivity extends Activity {

    private final StartupSequenceMediator startupSequenceMediator;

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
    }
}
