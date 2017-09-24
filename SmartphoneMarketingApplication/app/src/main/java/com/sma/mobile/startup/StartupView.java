package com.sma.mobile.startup;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

/**
 * Created by longtran on 14/01/2017.
 */

public class StartupView extends RelativeLayout {

    public StartupView(Context context) {
        this(context, null);
    }

    public StartupView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StartupView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        Context localContext = getContext();
        if ((localContext instanceof StartupActivity)) {
            ((StartupActivity) localContext).getStartupMediator().start();
        }
    }
}
