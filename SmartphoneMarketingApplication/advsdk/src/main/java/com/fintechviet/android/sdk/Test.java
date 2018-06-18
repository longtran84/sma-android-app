package com.fintechviet.android.sdk;

import com.fintechviet.android.sdk.content.NewsResponse;
import com.fintechviet.android.sdk.user.Reward;
import com.fintechviet.android.sdk.user.User;

import java.util.List;

/**
 * Created by tungn on 9/17/2017.
 */
public class Test {
    public static void main(String[] args) {
        //FintechvietSdk.getInstance().updateUserInfo("abcdef", "abcdef@abc.com", "MALE", 1982, "HN");
        //User user = FintechvietSdk.getInstance().getUserInfo("abcdef");
        //List<Reward> rewards = FintechvietSdk.getInstance().getUserRewardInfo("abcdef");

        FintechvietSdk.getInstance().getNewsByUserInterest("abcdef", "THT,VHO", "-1", new FintechvietSdk.NewsListener() {

            @Override
            public void success(NewsResponse response) {
                System.out.println("success");
            }

            @Override
            public void error(FintechvietSdk.FintechvietError error) {
                System.out.println(error.getReason());
            }
        });

        System.out.println("abcdef");
    }
}
