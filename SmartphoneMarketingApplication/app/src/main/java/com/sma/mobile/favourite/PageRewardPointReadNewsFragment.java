package com.sma.mobile.favourite;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fintechviet.android.sdk.FintechvietSdk;
import com.fintechviet.android.sdk.listener.JCallback;
import com.fintechviet.android.sdk.user.User;
import com.sma.mobile.R;
import com.sma.mobile.activities.AbstractFragment;
import com.sma.mobile.home.HomeActivity;
import com.sma.mobile.utils.firebasenotifications.Config;

import java.text.DecimalFormat;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by longtran on 03/10/2017.
 */

public class PageRewardPointReadNewsFragment extends AbstractFragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_TEXT = "PARAM";
    private AppCompatTextView appCompatTextViewRewardPoint;

    @Nullable
    private String pageText;

    public PageRewardPointReadNewsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param pageText Parameter 1.
     * @return A new instance of fragment PageFragment.
     */
    public static PageRewardPointReadNewsFragment newInstance(@NonNull final String pageText) {
        PageRewardPointReadNewsFragment fragment = new PageRewardPointReadNewsFragment();

        Bundle args = new Bundle();
        args.putString(ARG_TEXT, pageText);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            pageText = getArguments().getString(ARG_TEXT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_page_rewards_points, container, false);
        AppCompatTextView appCompatTextViewRewardPointSubject = (AppCompatTextView) view.findViewById(R.id.app_compat_text_view_reward_point_subject_id);
        appCompatTextViewRewardPoint = (AppCompatTextView) view.findViewById(R.id.app_compat_text_view_reward_point_id);
        appCompatTextViewRewardPointSubject.setText("Điểm thưởng đọc tin");
        appCompatTextViewRewardPoint.setText("2.009 điểm");
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FintechvietSdk.getInstance().getUserInfo(getDeviceSerialNumber(), new JCallback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.code() == 200) {
                    User user = response.body();
                    if (null != user) {
                        //final DecimalFormat formatter = new DecimalFormat("###,###,###.##");
                        appCompatTextViewRewardPoint.setText(String.format("%s điểm", user.getEarning()));
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
