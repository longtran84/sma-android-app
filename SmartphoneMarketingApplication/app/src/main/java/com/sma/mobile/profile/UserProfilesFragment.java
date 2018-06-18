package com.sma.mobile.profile;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fintechviet.android.sdk.FintechvietSdk;
import com.fintechviet.android.sdk.listener.JCallback;
import com.fintechviet.android.sdk.user.User;
import com.flaviofaria.kenburnsview.KenBurnsView;
import com.sma.mobile.R;
import com.sma.mobile.favourite.News;
import com.sma.mobile.utils.firebasenotifications.Config;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by tqlong.net@gmail.com on 24/04/15.
 */
public class UserProfilesFragment extends Fragment {

    @BindView(R.id.image_view_header_id)
    KenBurnsView kenBurnsView;

    @BindView(R.id.text_view_email_id)
    TextView textViewEmail;

    @BindView(R.id.text_view_gender_id)
    TextView textViewGender;

    @BindView(R.id.avatar)
    CircleImageView circleImageViewAvatar;

    @BindView(R.id.text_view_earning_id)
    TextView textViewEarning;

    @BindView(R.id.text_view_invite_code_id)
    TextView textViewInviteCode;

    @BindView(R.id.text_view_location_id)
    TextView textViewLocation;

    @BindView(R.id.text_view_dob_id)
    TextView textViewDatesOfBirth;

    public static UserProfilesFragment newInstance() {
        return new UserProfilesFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user_profiles, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        SharedPreferences pref = getActivity().getSharedPreferences(Config.SHARED_PREF, 0);
        String registrationToken = pref.getString(Config.REGISTRATION_TOKENS, "ERROR");
        FintechvietSdk.getInstance().getUserInfo(registrationToken, new JCallback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                User user = response.body();
                if (null != user) {
                    textViewEmail.setText(user.getEmail());
                    textViewGender.setText(user.getGender());
                    textViewEarning.setText(String.valueOf(user.getEarning()));
                    textViewInviteCode.setText(user.getInviteCode());
                    textViewLocation.setText(String.format("Địa chỉ : %s", user.getLocation()));
                    textViewDatesOfBirth.setText(String.format("Ngày sinh : %s", String.valueOf(user.getDob())));
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
    }
}
