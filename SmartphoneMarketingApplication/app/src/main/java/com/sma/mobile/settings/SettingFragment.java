package com.sma.mobile.settings;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.fintechviet.android.sdk.FintechvietSdk;
import com.fintechviet.android.sdk.listener.JCallback;
import com.fintechviet.android.sdk.user.User;
import com.sma.mobile.FavouriteActivity;
import com.sma.mobile.R;
import com.sma.mobile.activities.AbstractFragment;
import com.sma.mobile.article.GameShowActivity;
import com.sma.mobile.cashout.CashOutMainActivity;
import com.sma.mobile.settings.reward.RewardInfoActivity;
import com.sma.mobile.utils.firebasenotifications.Config;
import com.thefinestartist.finestwebview.FinestWebView;

import org.apache.commons.lang3.StringUtils;

import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by QLong on 10/4/2017.
 */

public class SettingFragment extends AbstractFragment implements View.OnClickListener {

    private EditText passwordInput = null;
    private TextView textViewtvGMG;
    private SharedPreferences pref;

    public static SettingFragment newInstance() {
        return new SettingFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.settings_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        pref = getActivity().getSharedPreferences(Config.SHARED_PREF, 0);
        ((Switch) getActivity().findViewById(R.id.swHaftScreen)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                SharedPreferences.Editor editor = pref.edit();
                editor.putInt("haft_screen", b ? 1 : 0);
                editor.commit();
            }
        });
        ((Switch) getActivity().findViewById(R.id.swWifi)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                SharedPreferences.Editor editor = pref.edit();
                editor.putInt("wifi", b ? 1 : 0);
                editor.commit();
            }
        });
        ((Switch) getActivity().findViewById(R.id.sw3G)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                SharedPreferences.Editor editor = pref.edit();
                editor.putInt("3g", b ? 1 : 0);
                editor.commit();
            }
        });
        getActivity().findViewById(R.id.tv_my_info).setOnClickListener(this);
        getActivity().findViewById(R.id.tv_cash_out).setOnClickListener(this);
        getActivity().findViewById(R.id.tv_notice).setOnClickListener(this);
        getActivity().findViewById(R.id.tv_qa).setOnClickListener(this);
        getActivity().findViewById(R.id.tv_tutorial).setOnClickListener(this);
        getActivity().findViewById(R.id.tv_customer_service).setOnClickListener(this);
        getActivity().findViewById(R.id.tv_contact).setOnClickListener(this);
        getActivity().findViewById(R.id.tv_term_use).setOnClickListener(this);
        getActivity().findViewById(R.id.tv_security_policy).setOnClickListener(this);
        getActivity().findViewById(R.id.tv_delete_account).setOnClickListener(this);
        getActivity().findViewById(R.id.tv_invite_friends).setOnClickListener(this);
        getActivity().findViewById(R.id.tv_point_history).setOnClickListener(this);
        getActivity().findViewById(R.id.tv_voucher_term_use).setOnClickListener(this);
        getActivity().findViewById(R.id.tv_hobby).setOnClickListener(this);
        getActivity().findViewById(R.id.tv_game).setOnClickListener(this);
        getActivity().findViewById(R.id.tv_invite_code).setOnClickListener(this);
        textViewtvGMG = (TextView) getActivity().findViewById(R.id.tvGMG);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            if (null == pref) {
                pref = getActivity().getSharedPreferences(Config.SHARED_PREF, 0);
            }
            final int haftScreen = pref.getInt("haft_screen", 0);
            final int wifi = pref.getInt("wifi", 0);
            final int connect3g = pref.getInt("3g", 0);
            ((Switch) getActivity().findViewById(R.id.swHaftScreen)).setChecked(haftScreen > 0 ? true : false);
            ((Switch) getActivity().findViewById(R.id.swWifi)).setChecked(wifi > 0 ? true : false);
            ((Switch) getActivity().findViewById(R.id.sw3G)).setChecked(connect3g > 0 ? true : false);
            FintechvietSdk.getInstance().getUserInfo(getDeviceSerialNumber(), new JCallback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    if (response.code() == 200) {
                        User user = response.body();
                        if (null != user && !StringUtils.isBlank(user.getInviteCode())) {
                            textViewtvGMG.setText(user.getInviteCode());
                        }
                        if (null != user && !StringUtils.isBlank(user.getInviteCodeUsed())) {
                            getActivity().findViewById(R.id.tv_invite_code).setVisibility(View.GONE);
                        } else {
                            getActivity().findViewById(R.id.tv_invite_code).setVisibility(View.VISIBLE);
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

    /**
     *
     */
    private void showInvitationCodes() {
        MaterialDialog dialog =
                new MaterialDialog.Builder(getActivity())
                        .title("Mã giới thiệu")
                        .customView(R.layout.dialog_invitation_codes, false)
                        .positiveText("Xác nhận")
                        .cancelable(false)
                        .autoDismiss(false)
                        .negativeText("Hủy")
                        .onNegative((dialog1, which) -> {
                            dialog1.dismiss();
                        })
                        .onPositive(
                                (dialog1, which) -> {
                                    if (!StringUtils.isBlank(passwordInput.getText().toString())) {
                                        FintechvietSdk.getInstance().updateInviteCode(getDeviceSerialNumber(), passwordInput.getText().toString(), new JCallback<String>() {
                                            @Override
                                            public void onResponse(Call<String> call, Response<String> response) {
                                                if (response.code() == 200 && !response.body().equalsIgnoreCase("InviteCodeInvalid")) {
                                                    dialog1.dismiss();
                                                } else {
                                                    passwordInput.setError("Mã giới thiệu không đúng");
                                                }
                                            }

                                            @Override
                                            public void onFailure(Call<String> call, Throwable t) {
                                                passwordInput.setError("Mã giới thiệu không đúng");
                                            }
                                        });
                                    } else {
                                        passwordInput.setError("Mã giới thiệu không đúng");
                                    }
                                })
                        .build();
        passwordInput = dialog.getCustomView().findViewById(R.id.password);
        passwordInput.setImeOptions(EditorInfo.IME_ACTION_DONE);
        dialog.show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_contact:
                showWebView(m10682A("$2.>xtmt#5&)-2&\u0004#(1>6t#+2\u0004*//7m8-56:!/l366."));
                return;
            case R.id.tv_my_info:
                startActivity(new Intent(getActivity(), MyInfoActivity.class));
                return;
            case R.id.tv_point_history:
                //C2919a.m12559a(m1050l(), PointHistoryActivity.class);
                startActivity(new Intent(getActivity(), RewardInfoActivity.class));
                return;
            case R.id.tv_invite_friends:
                //C2919a.m12559a(m1047k(), InviteFriendsActivity.class);
                startActivity(new Intent(getActivity(), InviteYourFriendsActivity.class));
                return;
            case R.id.tv_notice:
                showWebView(m10682A("=+7'amtm:,?04+?\u001d:1('/m:2+\u001d366.t,462!>l366."));
                return;
            case R.id.tv_qa:
                showWebView(m7255a("W:]6\u000b|\u001e|P=U!^:U\fP B6E|P#A\fY'\\?\u001e\"P}Y'\\?"));
                return;
            case R.id.tv_invite_code:
                showInvitationCodes();
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
            case R.id.tv_hobby:
                startActivityForResult(new Intent(getActivity(), FavouriteActivity.class), 84);
                //new DeleteAccountDialogFragment().mo1562a(m1055o(), DeleteAccountDialogFragment.class.getSimpleName());
                return;
            case R.id.tv_game:
                startActivityForResult(new Intent(getActivity(), GameShowActivity.class), 84);
                return;
            case R.id.tv_cash_out:
                startActivityForResult(new Intent(getActivity(), CashOutMainActivity.class), 84);
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
        new FinestWebView.Builder(getActivity()).theme(R.style.FinestWebViewTheme)
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