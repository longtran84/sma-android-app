package com.sma.mobile.launcher;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.sma.mobile.MainActivity;
import com.sma.mobile.R;
import com.sma.mobile.SMAApplication;
import com.sma.mobile.activities.AbstractAppCompatActivity;
import com.sma.mobile.loginsignup.SignupActivity;

import org.apache.commons.lang3.StringUtils;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by longtran on 14/01/2017.
 */

public class SplashScreen extends AbstractAppCompatActivity {

    /**
     * The package name of your app
     */
    private int currentVersion = 0;
    private int responseVersion = 0;
    private SMAApplication mSMAApplication;
//    @BindView(R.id.toolbar_container)
//    Toolbar toolbar;

    @Override
    public int getFragmentContainerViewId() {
        return 0;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
//        ButterKnife.bind(this);
//        toolbar.refreshDrawableState();
//        toolbar.setTitle("");
//        toolbar.setTitleTextColor(Color.BLACK);
//        toolbar.invalidate();// restore toolbar
//        setSupportActionBar(toolbar);
//        this.getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        mSMAApplication = (SMAApplication) getApplication();
        new CountDownTimer(5 * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                redirectSignUp(SplashScreen.this);
            }
        }.start();

//        SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
//        String registrationToken = pref.getString(Config.REGISTRATION_TOKENS, null);
    }

    /****
     * @return
     */
    public MaterialDialog getMaterialDialogAlert(Activity activity, String title, String content) {
        return new MaterialDialog.Builder(activity)
                .title(title)
                .content(content)
                .positiveText(R.string.ok)
                .positiveColorRes(R.color.color_black)
                .onAny(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                        finish();
                    }
                })
                .build();
    }

    /***
     *
     */
    public void redirectHome(Activity activity) {
        Intent intent = new Intent();
        intent.setClassName(activity, MainActivity.class.getName());
        activity.startActivity(intent);
        activity.finish();
    }

    /***
     * @param activity
     */
    public void redirectSignUp(Activity activity) {
        Intent intent = new Intent();
        intent.setClassName(activity, SignupActivity.class.getName());
        activity.startActivity(intent);
        activity.finish();
    }

}
