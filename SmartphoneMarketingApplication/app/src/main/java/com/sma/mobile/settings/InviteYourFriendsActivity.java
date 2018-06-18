package com.sma.mobile.settings;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Telephony;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.fintechviet.android.sdk.FintechvietSdk;
import com.fintechviet.android.sdk.listener.JCallback;
import com.fintechviet.android.sdk.user.User;
import com.sma.mobile.R;
import com.sma.mobile.activities.AbstractAppCompatActivity;
import com.sma.mobile.utils.firebasenotifications.Config;

import org.apache.commons.lang3.StringUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by QLong on 10/4/2017.
 */

public class InviteYourFriendsActivity extends AbstractAppCompatActivity implements View.OnClickListener {

    @BindView(R.id.tv_spinner_province_hint)
    TextView tv_spinner_province_hint;

    @BindView(R.id.image_view_invite_skype)
    ImageView image_view_invite_skype;

    @BindView(R.id.image_view_menu_facebook)
    ImageView image_view_menu_facebook;

    @BindView(R.id.image_view_menu_zalo)
    ImageView image_view_menu_zalo;

    @BindView(R.id.image_view_invite_viber)
    ImageView image_view_invite_viber;

    @BindView(R.id.image_view_sms)
    ImageView image_view_menu_sms;

    @Override
    public int getFragmentContainerViewId() {
        return 0;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.invite_your_friends_activity);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // add back arrow to toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        getSupportActionBar().setTitle("Mời bạn bè");

        image_view_invite_skype.setOnClickListener(this);
        image_view_menu_facebook.setOnClickListener(this);
        image_view_menu_zalo.setOnClickListener(this);
        image_view_invite_viber.setOnClickListener(this);
        image_view_menu_sms.setOnClickListener(this);
        FintechvietSdk.getInstance().getUserInfo(getDeviceSerialNumber(), new JCallback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                User user = response.body();
                if (null != user && !StringUtils.isBlank(user.getInviteCode())) {
                    tv_spinner_province_hint.setText(user.getInviteCode());
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });

    }

    @Override
    public void onClick(View v) {
        FintechvietSdk.getInstance().getUserInfo(getDeviceSerialNumber(), new JCallback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.code() == 200) {
                    User user = response.body();
                    tv_spinner_province_hint.setText(user.getInviteCode());
                    if (v.getId() == image_view_menu_sms.getId()) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                            String defaultSmsPackageName = Telephony.Sms.getDefaultSmsPackage(getApplicationContext()); //Need to change the build to API 19
                            Intent sendIntent = new Intent(Intent.ACTION_SEND);
                            sendIntent.setType("text/plain");
                            sendIntent.putExtra(Intent.EXTRA_TEXT, String.format("Đọc tin được điểm thưởng với SMA. Nhớ dùng mã giới thiệu %s nhé!", user.getInviteCode()));
                            if (defaultSmsPackageName != null) {
                                sendIntent.setPackage(defaultSmsPackageName);
                            }
                            startActivity(sendIntent);
                        } else {
                            Intent sendIntent = new Intent(Intent.ACTION_VIEW);
                            sendIntent.setData(Uri.parse("sms:"));
                            sendIntent.putExtra("sms_body", String.format("Đọc tin được điểm thưởng với SMA. Nhớ dùng mã giới thiệu %s nhé!", user.getInviteCode()));
                            startActivity(sendIntent);
                        }
                    } else {
                        Intent shareIntent = new Intent(Intent.ACTION_SEND);
                        shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        shareIntent.setType("text/plain");
                        shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, String.format("Đọc tin được điểm thưởng với SMA. Nhớ dùng mã giới thiệu %s nhé!", user.getInviteCode()));
                        startActivity(shareIntent);
                    }
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
