package com.sma.mobile.settings;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fintechviet.android.sdk.FintechvietSdk;
import com.fintechviet.android.sdk.listener.JCallback;
import com.fintechviet.android.sdk.user.User;
import com.sma.mobile.R;
import com.sma.mobile.activities.AbstractAppCompatActivity;
import com.sma.mobile.home.HomeActivity;
import com.sma.mobile.settings.reward.RewardInfoActivity;
import com.sma.mobile.utils.firebasenotifications.Config;
import com.thefinestartist.finestwebview.FinestWebView;

import org.apache.commons.lang3.StringUtils;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by QLong on 10/4/2017.
 */

public class SettingActivity extends AbstractAppCompatActivity implements View.OnClickListener {

    @Override
    public int getFragmentContainerViewId() {
        return 0;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_fragment);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // add back arrow to toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        getSupportActionBar().setTitle("Cài đặt");
        findViewById(R.id.tv_my_info).setOnClickListener(this);
        findViewById(R.id.tv_notice).setOnClickListener(this);
        findViewById(R.id.tv_qa).setOnClickListener(this);
        findViewById(R.id.tv_tutorial).setOnClickListener(this);
        findViewById(R.id.tv_customer_service).setOnClickListener(this);
        findViewById(R.id.tv_contact).setOnClickListener(this);
        findViewById(R.id.tv_term_use).setOnClickListener(this);
        findViewById(R.id.tv_security_policy).setOnClickListener(this);
        findViewById(R.id.tv_delete_account).setOnClickListener(this);
        findViewById(R.id.tv_invite_friends).setOnClickListener(this);
        findViewById(R.id.tv_point_history).setOnClickListener(this);
        findViewById(R.id.tv_voucher_term_use).setOnClickListener(this);
        final TextView textViewtvGMG = (TextView) findViewById(R.id.tvGMG);
        SharedPreferences pref = getSharedPreferences(Config.SHARED_PREF, 0);
        String registrationToken = pref.getString(Config.REGISTRATION_TOKENS, "ERROR");
        FintechvietSdk.getInstance().getUserInfo(registrationToken, new JCallback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.code() == 200) {
                    User user = response.body();
                    if (null != user && !StringUtils.isBlank(user.getInviteCode())) {
                        textViewtvGMG.setText(user.getInviteCode());
                    }
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private String m10682A(String str) {
        int length = str.length();
        char[] cArr = new char[length];
        length--;
        int i = length;
        while (length >= 0) {
            int i2 = i - 1;
            cArr[i] = (char) (str.charAt(i) ^ 66);
            if (i2 < 0) {
                break;
            }
            length = i2 - 1;
            cArr[i2] = (char) (str.charAt(i2) ^ 91);
            i = length;
        }
        return new String(cArr);
    }

    private String m7255a(String str) {
        int length = str.length();
        char[] cArr = new char[length];
        length--;
        int i = length;
        while (length >= 0) {
            int i2 = i - 1;
            cArr[i] = (char) (str.charAt(i) ^ 83);
            if (i2 < 0) {
                break;
            }
            length = i2 - 1;
            cArr[i2] = (char) (str.charAt(i2) ^ 49);
            i = length;
        }
        return new String(cArr);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_contact:
                showWebView(m10682A("$2.>xtmt#5&)-2&\u0004#(1>6t#+2\u0004*//7m8-56:!/l366."));
                return;
            case R.id.tv_my_info:
                startActivity(new Intent(this, MyInfoActivity.class));
                return;
            case R.id.tv_point_history:
                //C2919a.m12559a(m1050l(), PointHistoryActivity.class);
                startActivity(new Intent(this, RewardInfoActivity.class));
                return;
            case R.id.tv_invite_friends:
                //C2919a.m12559a(m1047k(), InviteFriendsActivity.class);
                startActivity(new Intent(this, InviteYourFriendsActivity.class));
                return;
            case R.id.tv_notice:
                showWebView(m10682A("=+7'amtm:,?04+?\u001d:1('/m:2+\u001d366.t,462!>l366."));
                return;
            case R.id.tv_qa:
                showWebView(m7255a("W:]6\u000b|\u001e|P=U!^:U\fP B6E|P#A\fY'\\?\u001e\"P}Y'\\?"));
                return;
            case R.id.tv_tutorial:
                //C2919a.m12559a(m1047k(), TutorialActivity.class);
                return;
            case R.id.tv_term_use:
                showWebView(m7255a("W:]6\u000b|\u001e|P=U!^:U\fP B6E|P#A\fY'\\?\u001e'T!\\\fD T}Y'\\?"));
                return;
            case R.id.tv_voucher_term_use:
                showWebView(m7255a("W:]6\u000b|\u001e|P=U!^:U\fP B6E|P#A\fY'\\?\u001e%^&R;T!n'T!\\\fD T}Y'\\?"));
                return;
            case R.id.tv_security_policy:
                showWebView(m10682A("$2.>xtmt#5&)-2&\u0004#(1>6t#+2\u0004*//7m('87)+/;\u000424.2!\"l366."));
                return;
            case R.id.tv_delete_account:
                //new DeleteAccountDialogFragment().mo1562a(m1055o(), DeleteAccountDialogFragment.class.getSimpleName());
                return;
            default:
                return;
        }
    }

    /***
     *
     * @param resource
     */
    private void showWebView(String resource) {
        new FinestWebView.Builder(this).theme(R.style.FinestWebViewTheme)
                .titleDefault("SMA")
                .toolbarScrollFlags(0)
                .statusBarColorRes(R.color.blackPrimaryDark)
                .toolbarColorRes(R.color.blackPrimary)
                .titleColorRes(R.color.finestWhite)
                .urlColorRes(R.color.blackPrimaryLight)
                .iconDefaultColorRes(R.color.finestWhite)
                .progressBarColorRes(R.color.finestWhite)
                .swipeRefreshColorRes(R.color.blackPrimaryDark)
                .menuSelector(R.drawable.selector_light_theme)
                .menuTextGravity(Gravity.CENTER_VERTICAL | Gravity.RIGHT)
                .menuTextPaddingRightRes(R.dimen.defaultMenuTextPaddingLeft)
                .dividerHeight(0)
                .gradientDivider(false)
                //                    .setCustomAnimations(R.anim.slide_up, R.anim.hold, R.anim.hold, R.anim.slide_down)
                .setCustomAnimations(R.anim.slide_left_in, R.anim.hold, R.anim.hold,
                        R.anim.slide_right_out)
                //                    .setCustomAnimations(R.anim.fade_in_fast, R.anim.fade_out_medium, R.anim.fade_in_medium, R.anim.fade_out_fast)
                .disableIconBack(false)
                .disableIconClose(false)
                .disableIconForward(true)
                .disableIconMenu(true)
                .show(resource);
    }
}