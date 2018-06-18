package com.sma.mobile.game;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.fintechviet.android.sdk.FintechvietSdk;
import com.fintechviet.android.sdk.listener.JCallback;
import com.fintechviet.android.sdk.user.User;
import com.sma.mobile.R;
import com.sma.mobile.activities.AbstractAppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Response;
import rubikstudio.library.LuckyWheelView;
import rubikstudio.library.model.LuckyItem;

public class LuckyWheelBingo extends AbstractAppCompatActivity {

    private List<LuckyItem> data = new ArrayList<>();
    private TextView petCoin;

    @Override
    public int getFragmentContainerViewId() {
        return 0;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lucky_wheel_bingo);
        final LuckyWheelView luckyWheelView = (LuckyWheelView) findViewById(R.id.luckyWheel);
        petCoin = (TextView) findViewById(R.id.icon_pet_coin_id);
        //////////////////
        LuckyItem luckyItem4 = new LuckyItem();
        luckyItem4.text = "400";
        luckyItem4.reward = 400;
        luckyItem4.icon = R.drawable.sys_avatar_3;
        luckyItem4.color = 0xffFFF3E0;
        data.add(luckyItem4);

        LuckyItem luckyItem5 = new LuckyItem();
        luckyItem5.text = "500";
        luckyItem5.reward = 500;
        luckyItem5.icon = R.drawable.sys_avatar_4;
        luckyItem5.color = 0xffFFE0B2;
        data.add(luckyItem5);

        LuckyItem luckyItem6 = new LuckyItem();
        luckyItem6.text = "-6000";
        luckyItem6.reward = -6000;
        luckyItem6.icon = R.drawable.sys_avatar_5;
        luckyItem6.color = 0xffFFCC80;
        data.add(luckyItem6);
        //////////////////

        //////////////////////
        LuckyItem luckyItem7 = new LuckyItem();
        luckyItem7.text = "700";
        luckyItem7.reward = 700;
        luckyItem7.icon = R.drawable.sys_avatar_6;
        luckyItem7.color = 0xffFFF3E0;
        data.add(luckyItem7);

        LuckyItem luckyItem8 = new LuckyItem();
        luckyItem8.text = "-800";
        luckyItem8.reward = -800;
        luckyItem8.icon = R.drawable.sys_avatar_7;
        luckyItem8.color = 0xffFFE0B2;
        data.add(luckyItem8);


        LuckyItem luckyItem9 = new LuckyItem();
        luckyItem9.text = "900";
        luckyItem9.reward = 900;
        luckyItem9.icon = R.drawable.sys_avatar_8;
        luckyItem9.color = 0xffFFCC80;
        data.add(luckyItem9);
        ////////////////////////

        LuckyItem luckyItem10 = new LuckyItem();
        luckyItem10.text = "-1000";
        luckyItem10.reward = -1000;
        luckyItem10.icon = R.drawable.sys_avatar_9;
        luckyItem10.color = 0xffFFE0B2;
        data.add(luckyItem10);

        LuckyItem luckyItem11 = new LuckyItem();
        luckyItem11.text = "-3000";
        luckyItem11.reward = -3000;
        luckyItem11.icon = R.drawable.sys_avatar_10;
        luckyItem11.color = 0xffFFE0B2;
        data.add(luckyItem11);

        /////////////////////

        luckyWheelView.setData(data);
        luckyWheelView.setRound(getRandomRound());

        /*luckyWheelView.setLuckyWheelBackgrouldColor(0xff0000ff);
        luckyWheelView.setLuckyWheelTextColor(0xffcc0000);
        luckyWheelView.setLuckyWheelCenterImage(getResources().getDrawable(R.drawable.icon));
        luckyWheelView.setLuckyWheelCursorImage(R.drawable.ic_cursor);*/


        findViewById(R.id.play).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int index = getRandomIndex();
                luckyWheelView.startLuckyWheelWithTargetIndex(index);
            }
        });

        luckyWheelView.setLuckyRoundItemSelectedListener(new LuckyWheelView.LuckyRoundItemSelectedListener() {
            @Override
            public void luckyRoundItemSelected(int index) {
                if (data.get(Math.max(index - 1, 0)).reward < 0) {
                    Toast.makeText(getApplicationContext(), String.format("Bạn bị trừ %d điểm", data.get(Math.max(index - 1, 0)).reward), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), String.format("Bạn được cộng %d điểm", data.get(Math.max(index - 1, 0)).reward), Toast.LENGTH_SHORT).show();
                }
                FintechvietSdk.getInstance().updateUserReward(getDeviceSerialNumber(), "GAME", data.get(Math.max(index - 1, 0)).reward, new JCallback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        getUserInfo();
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {

                    }
                });
            }
        });
        getUserInfo();
    }

    /**
     *
     */
    private void getUserInfo() {
        FintechvietSdk.getInstance().getUserInfo(getDeviceSerialNumber(), new JCallback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.code() == 200) {
                    User user = response.body();
                    if (null != user) {
                        petCoin.setText(String.format("%s đ", user.getEarning()));
                    }
                }
                hideProcessing();
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                hideProcessing();
                t.printStackTrace();
            }
        });
    }

    private int getRandomIndex() {
        Random rand = new Random();
        return rand.nextInt(data.size() - 1) + 0;
    }

    private int getRandomRound() {
        Random rand = new Random();
        return rand.nextInt(10) + 15;
    }
}
