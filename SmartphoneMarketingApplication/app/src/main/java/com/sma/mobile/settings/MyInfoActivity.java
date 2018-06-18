package com.sma.mobile.settings;

import android.content.SharedPreferences;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.fintechviet.android.sdk.FintechvietSdk;
import com.fintechviet.android.sdk.listener.JCallback;
import com.fintechviet.android.sdk.user.User;
import com.sma.mobile.R;
import com.sma.mobile.activities.AbstractAppCompatActivity;
import com.sma.mobile.utils.firebasenotifications.Config;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by QLong on 10/4/2017.
 */

public class MyInfoActivity extends AbstractAppCompatActivity {

    @BindView(R.id.tv_user_name)
    TextView tv_user_name;

    @BindView(R.id.tv_spinner_province_hint)
    TextView tv_spinner_province_hint;

    @BindView(R.id.tv_spinner_birthday_hint)
    TextView tv_spinner_birthday_hint;

    @BindView(R.id.rad_sex)
    RadioGroup rad_sex;

    @Override
    public int getFragmentContainerViewId() {
        return 0;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_info_activity);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // add back arrow to toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        getSupportActionBar().setTitle("Thông tin của tôi");

        Resources r = getResources();
        TypedArray citiesCodes = r.obtainTypedArray(R.array.vietnamese_cities_and_provinces_with_vietnamese_characters);
        final Map<String, String> hashMap = new HashMap<>();
        int cpt = citiesCodes.length();
        String[] cityAndCode = r.getStringArray(R.array.vietnamese_cities_and_provinces_with_vietnamese_characters);
        String city = "NA";
        String code = "NA";
        for (int i = 0; i < cpt; ++i) {
            if (i % 2 == 0) {
                code = cityAndCode[i];
            } else {
                city = cityAndCode[i];
            }
            hashMap.put(code, city);
        }
//        SharedPreferences pref = getSharedPreferences(Config.SHARED_PREF, 0);
//        String registrationToken = pref.getString(Config.REGISTRATION_TOKENS, "ERROR");
        FintechvietSdk.getInstance().getUserInfo(getDeviceSerialNumber(), new JCallback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                User user = response.body();
                if (null != user) {
                    tv_user_name.setText(user.getEmail());
                    tv_spinner_province_hint.setText(hashMap.get(user.getLocation()));
                    tv_spinner_birthday_hint.setText(String.valueOf(user.getDob()));
                    if(!StringUtils.isBlank(user.getGender())){
                        if (user.getGender().equalsIgnoreCase("male")) {
                            ((RadioButton) rad_sex.getChildAt(0)).setChecked(true);
                        } else {
                            ((RadioButton) rad_sex.getChildAt(1)).setChecked(true);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
    }
}
