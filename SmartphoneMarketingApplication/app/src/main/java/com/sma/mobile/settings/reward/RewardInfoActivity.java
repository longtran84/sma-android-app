package com.sma.mobile.settings.reward;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.fintechviet.android.sdk.FintechvietSdk;
import com.fintechviet.android.sdk.listener.JCallback;
import com.fintechviet.android.sdk.model.RewardInfoEntity;
import com.fintechviet.android.sdk.user.User;
import com.sma.mobile.R;
import com.sma.mobile.activities.AbstractAppCompatActivity;
import com.sma.mobile.cashout.CashOutMainActivity;
import com.sma.mobile.favourite.RecyclerViewOnItemClickListener;
import com.sma.mobile.favourite.adapters.ViewPagerRewardsPointsAdapter;
import com.sma.mobile.settings.SettingFragment;
import com.sma.mobile.settings.reward.adapters.RewardInfoAdapter;
import com.sma.mobile.utils.firebasenotifications.Config;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by longtran on 05/10/2017.
 */

public class RewardInfoActivity extends AbstractAppCompatActivity {

    private static final boolean GRID_LAYOUT = false;
    private List<RewardInfoEntity> items;
    private RewardInfoAdapter rewardInfoAdapter;

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    @BindView(R.id.view_pager_rewards_points_id)
    ViewPager viewPager;

    @BindView(R.id.app_compat_button_point_converter)
    AppCompatButton appCompatButtonPointConverter;

    @Override
    public int getFragmentContainerViewId() {
        return 0;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reward_info);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // add back arrow to toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        getSupportActionBar().setTitle("Chi tiết điểm thưởng");
        showProcessing();
        viewPager.setAdapter(new ViewPagerRewardsPointsAdapter(getSupportFragmentManager()));
        items = new ArrayList<RewardInfoEntity>();
//        SharedPreferences pref = getSharedPreferences(Config.SHARED_PREF, 0);
//        String registrationToken = pref.getString(Config.REGISTRATION_TOKENS, "ERROR");
        FintechvietSdk.getInstance().getRewardInfo(getDeviceSerialNumber(), new JCallback<List<RewardInfoEntity>>() {
            @Override
            public void onResponse(Call<List<RewardInfoEntity>> call, Response<List<RewardInfoEntity>> response) {
                if (response.code() == 200) {
                    List<RewardInfoEntity> listAdMobReponse = response.body();
                    for (RewardInfoEntity rewardInfoEntity : listAdMobReponse) {
                        items.add(rewardInfoEntity);
                        rewardInfoAdapter.notifyDataSetChanged();
                    }
                }
                FintechvietSdk.getInstance().redeemPoint(getDeviceSerialNumber(), new JCallback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if (response.code() == 200) {
                            RewardInfoEntity rewardInfoEntity = new RewardInfoEntity();
                            rewardInfoEntity.setAmount(response.body());
                            rewardInfoEntity.setRewardName("Điểm đã sử dụng");
                            items.add(rewardInfoEntity);
                            rewardInfoAdapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onFailure(Call<String>  call, Throwable t) {

                    }
                });
                hideProcessing();
            }

            @Override
            public void onFailure(Call<List<RewardInfoEntity>> call, Throwable t) {
                hideProcessing();
            }
        });
//        FintechvietSdk.getInstance().getUserInfo(getDeviceSerialNumber(), new JCallback<User>() {
//            @Override
//            public void onResponse(Call<User> call, Response<User> response) {
//                if (response.code() == 200) {
//                    User user = response.body();
//                    if (null != user) {
//                        //final DecimalFormat formatter = new DecimalFormat("###,###,###.##");
//                        //textViewRewardPoint.setText(String.format("%s đ", user.getEarning()));
//                    }
//                }
//                hideProcessing();
//            }
//
//            @Override
//            public void onFailure(Call<User> call, Throwable t) {
//                hideProcessing();
//            }
//        });

//        if (GRID_LAYOUT) {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
//        } else {
//            mRecyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
//        }
        mRecyclerView.setHasFixedSize(true);
        Paint paint = new Paint();
        paint.setStrokeWidth(1);
        paint.setColor(0xFFCCCCCC);
        paint.setAntiAlias(true);
        paint.setPathEffect(new DashPathEffect(new float[]{25.0f, 25.0f}, 0));
        mRecyclerView.addItemDecoration(
                new HorizontalDividerItemDecoration.Builder(getApplicationContext()).paint(paint).build());
        rewardInfoAdapter = new RewardInfoAdapter(getApplicationContext(), items, new RecyclerViewOnItemClickListener() {
            @Override
            public void onClick(View v, int position) {

            }
        });
        mRecyclerView.setAdapter(rewardInfoAdapter);
        appCompatButtonPointConverter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClassName(getApplicationContext(), CashOutMainActivity.class.getName());
                startActivity(intent);
            }
        });
    }
}
