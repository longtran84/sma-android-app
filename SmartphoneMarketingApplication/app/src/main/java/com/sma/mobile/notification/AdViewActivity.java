package com.sma.mobile.notification;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

import com.sma.mobile.R;

/**
 * Created by tungn on 9/23/2017.
 */

public class AdViewActivity extends Activity {
    private WebView webView;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adview);
        String clickUrl = getIntent().getExtras().getString("clickUrl");
        webView = (WebView) findViewById(R.id.adView);
        webView.loadUrl(clickUrl);

    }
}
