package com.fintechviet.android.sdk;

import android.support.annotation.CallSuper;

import com.fintechviet.android.sdk.ad.DecisionResponse;
import com.fintechviet.android.sdk.ad.Request;
import com.fintechviet.android.sdk.content.NewsResponse;
import com.fintechviet.android.sdk.model.ArticlesResponse;
import com.fintechviet.android.sdk.model.Favourite;
import com.fintechviet.android.sdk.user.Reward;
import com.fintechviet.android.sdk.user.User;


import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by tungn on 9/7/2017.
 */
public interface FintechvietService {

    @Headers("Content-Type: application/json")
    @POST("/ad/placement")
    Call<DecisionResponse> request(@Body Request request);

    @Headers("Content-Type: application/json")
    @POST("/content/news/userInterest/{deviceToken}/{cateIds}/{lastNewsIds}")
    Call<NewsResponse> getNewsByUserInterest(@Path("deviceToken") String deviceToken, @Path("cateIds") String cateIds, @Path("lastNewsIds") String lastNewsIds);

    @Headers("Content-Type: application/json")
    @POST("/user/{deviceToken}/{email}/{gender}/{dob}/{location}")
    Call<String> updateUserInfo(@Path("deviceToken") String deviceToken, @Path("email") String email, @Path("gender") String gender, @Path("dob") int dob,
                                @Path("location") String location);

    @Headers("Content-Type: application/json")
    @POST("/user/updateUserReward/{deviceToken}/{event}/{addedPoint}")
    Call<String> updateUserReward(@Path("deviceToken") String deviceToken, @Path("event") String event, @Path("addedPoint") long addedPoint);

    @Headers("Content-Type: application/json")
    @POST("/user/{deviceToken}")
    Call<User> getUserInfo(@Path("deviceToken") String deviceToken);

    @Headers("Content-Type: application/json")
    @POST("/user/reward/{deviceToken}")
    Call<List<Reward>> getUserRewardInfo(@Path("deviceToken") String deviceToken);

    @Headers("Content-Type: application/json")
    @GET("/content/categories")
    Call<List<Favourite>> getListFavourite();

    @Headers("Content-Type: application/json")
    @GET("/content/news_crawler/interest/{deviceToken}")
    Call<ArticlesResponse> getArticlesResponse(@Path("deviceToken") String deviceToken, @Query("page") int page);
}
