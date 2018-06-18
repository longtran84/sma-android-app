package com.fintechviet.android.sdk;

import com.fintechviet.android.sdk.ad.AdMob;
import com.fintechviet.android.sdk.ad.DecisionResponse;
import com.fintechviet.android.sdk.content.NewsResponse;
import com.fintechviet.android.sdk.model.AbstractMessage;
import com.fintechviet.android.sdk.model.ArticlesGameItem;
import com.fintechviet.android.sdk.model.ArticlesItem;
import com.fintechviet.android.sdk.model.ArticlesResponse;
import com.fintechviet.android.sdk.model.Favourite;
import com.fintechviet.android.sdk.model.GameCardResponse;
import com.fintechviet.android.sdk.model.GiftCodeResponse;
import com.fintechviet.android.sdk.model.LatitudeLongitude;
import com.fintechviet.android.sdk.model.ListNewsByCategory;
import com.fintechviet.android.sdk.model.ListTopAds;
import com.fintechviet.android.sdk.model.Message;
import com.fintechviet.android.sdk.model.OrderLoyalty;
import com.fintechviet.android.sdk.model.PhoneCardResponse;
import com.fintechviet.android.sdk.model.PromotionMessage;
import com.fintechviet.android.sdk.model.RewardInfoEntity;
import com.fintechviet.android.sdk.model.VoucherImagesResponse;
import com.fintechviet.android.sdk.model.VoucherResponse;
import com.fintechviet.android.sdk.user.Reward;
import com.fintechviet.android.sdk.user.User;


import java.util.List;

import retrofit2.Call;
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
    @GET("/ad/placement")
    Call<DecisionResponse> placement(@Query("template") String template, @Query("deviceToken") String deviceToken, @Query("adTypeId") int adTypeId);

    @Headers("Content-Type: application/json")
    @POST("/content/news/userInterest/{deviceToken}/{cateIds}/{lastNewsIds}")
    Call<NewsResponse> getNewsByUserInterest(@Path("deviceToken") String deviceToken, @Path("cateIds") String cateIds, @Path("lastNewsIds") String lastNewsIds);

    @Headers("Content-Type: application/json")
    @GET("/user/updateUserInfo")
    Call<Void> updateUserInfo(@Query("deviceToken") String deviceToken, @Query("email") String email, @Query("gender") String gender, @Query("dob") String dob,
                              @Query("location") String location, @Query("inviteCode") String inviteCode);

    @Headers("Content-Type: application/json")
    @GET("/user/registerUser")
    Call<Void> registerUser(@Query("deviceToken") String deviceId);

    @Headers("Content-Type: application/json")
    @GET("/user/updateUserReward/{deviceToken}")
    Call<String> updateUserReward(@Path("deviceToken") String deviceToken, @Query("rewardCode") String event, @Query("addedPoint") int addedPoint);

    @Headers("Content-Type: application/json")
    @GET("/user/{deviceToken}")
    Call<User> getUserInfo(@Path("deviceToken") String deviceToken);

    @Headers("Content-Type: application/json")
    @POST("/user/reward/{deviceToken}")
    Call<List<Reward>> getUserRewardInfo(@Path("deviceToken") String deviceToken);

    @Headers("Content-Type: application/json")
    @GET("/content/categories")
    Call<List<Favourite>> getListFavourite();

    @Headers("Content-Type: application/json")
    @GET("/content/news_crawler/interest/{deviceToken}")
    Call<ArticlesResponse> getArticlesResponse(@Path("deviceToken") String deviceToken, @Query("page") int page, @Query("newsId") String newsId);

    @Headers("Content-Type: application/json")
    @GET("/content/news_crawler/interest")
    Call<ArticlesResponse> _getArticlesResponseByInterests(@Query("interests") String interests, @Query("page") int page);

    @Headers("Content-Type: application/json")
    @GET("/content/click")
    Call<Void> contentClick();

    @Headers("Content-Type: application/json")
    @GET("/content/impression")
    Call<Void> contentImpression();

    @Headers("Content-Type: application/json")
    @GET("/ad/appAds")
    Call<List<AdMob>> appAds();

    @Headers("Content-Type: application/json")
    @GET("/ad/view")
    Call<Void> adMobView(@Query("adId") int adId, @Query("deviceToken") String deviceToken);

    @Headers("Content-Type: application/json")
    @GET("/ad/click")
    Call<Void> adMobClick(@Query("adId") int adId, @Query("deviceToken") String deviceToken);

    @Headers("Content-Type: application/json")
    @GET("/user/messages/{deviceToken}")
    Call<List<Message>> getMessages(@Path("deviceToken") String deviceToken);

    @Headers("Content-Type: application/json")
    @GET("/user/newMessages/{deviceToken}")
    Call<List<PromotionMessage>> getPromotionMessage(@Path("deviceToken") String deviceToken,
                                                     @Query("type") String type);

    @Headers("Content-Type: application/json")
    @GET("user/updateMessage/{id}/READ")
    Call<Void> updateMessage(@Path("id") int id);

    @Headers("Content-Type: application/json")
    @GET("user/updateInviteCode/{deviceToken}")
    Call<String> updateInviteCode(@Path("deviceToken") String deviceToken, @Query("inviteCode") String inviteCode);

    @Headers("Content-Type: application/json")
    @GET("user/updateMessage/{id}/RECEIVE")
    Call<Void> updateMessagePromotion(@Path("id") int id);

    @Headers("Content-Type: application/json")
    @GET("/content/topNewsByCategory")
    Call<List<ArticlesItem>> _topNewsByCategory(@Query("categoryCode") String categoryCode);


    @Headers("Content-Type: application/json")
    @GET("/content/news_crawler/newsByCategory/{deviceToken}")
    Call<ListNewsByCategory> getNewsByCategory(@Path("deviceToken") String deviceToken, @Query("page") int page, @Query("categoryCode") String categoryCode);

    @Headers("Content-Type: application/json")
    @GET("/content/adnews")
    Call<List<ArticlesItem>> adNews(@Query("page") int page);

    @Headers("Content-Type: application/json")
    @GET("/content/games")
    Call<List<ArticlesGameItem>> games();

    @Headers("Content-Type: application/json")
    @GET("/user/reward/{deviceToken}")
    Call<List<RewardInfoEntity>> getRewardInfo(@Path("deviceToken") String deviceToken);

    @Headers("Content-Type: application/json")
    @GET("/user/updateUserReward/{deviceToken}")
    Call<Void> updateUserRewardPoint(@Path("deviceToken") String deviceToken, @Query("rewardCode") String rewardCode, @Query("addedPoint") int addedPoint);

    @Headers("Content-Type: application/json")
    @GET("content/click")
    Call<Void> contentClick(@Query("deviceToken") String deviceToken, @Query("newsId") String newsId, @Query("rewardPoint") int rewardPoint);

    @Headers("Content-Type: application/json")
    @GET("/user/interests/{favouriteList}/{deviceToken}")
    Call<Void> updateFavouriteCategoriesByDevice(@Path("favouriteList") String favouriteList, @Path("deviceToken") String deviceToken);

    @Headers("Content-Type: application/json")
    @GET("/ad/topadv")
    Call<List<ListTopAds>> getListTopAds(@Query("deviceToken") String deviceToken);

    @Headers("Content-Type: application/json")
    @GET("/loyalty/vouchers")
    Call<List<VoucherResponse>> getListVoucherResponse();

    @Headers("Content-Type: application/json")
    @GET("/loyalty/phonecards")
    Call<List<PhoneCardResponse>> getListPhoneCardResponse();

    @Headers("Content-Type: application/json")
    @GET("/loyalty/giftcodes")
    Call<List<GiftCodeResponse>> getListGiftCodeResponse();

    @Headers("Content-Type: application/json")
    @GET("/loyalty/gamecards")
    Call<List<GameCardResponse>> getListGameCardResponse();

    @Headers("Content-Type: application/json")
    @GET("/loyalty/voucher/{voucherId}")
    Call<VoucherResponse> getVoucherDetail(@Path("voucherId") long voucherId);

    @Headers("Content-Type: application/json")
    @GET("/loyalty/voucher/images/{voucherId}")
    Call<List<VoucherImagesResponse>> getVoucherImages(@Path("voucherId") long voucherId);

    @Headers("Content-Type: application/json")
    @GET("/loyalty/orders/{deviceToken}")
    Call<List<OrderLoyalty>> getOrderLoyalty(@Path("deviceToken") String deviceToken);

    @Headers("Content-Type: application/json")
    @GET("/loyalty/cart/addToCart/{deviceToken}")
    Call<AbstractMessage> addToCart(@Path("deviceToken") String deviceToken,
                                    @Query("itemId") long itemId,
                                    @Query("quantity") int quantity,
                                    @Query("price") String price,
                                    @Query("type") String type);


    @Headers("Content-Type: application/json")
    @GET("/loyalty/order/placeOrder/{deviceToken}")
    Call<AbstractMessage> placeOrder(@Path("deviceToken") String deviceToken,
                                     @Query("customerName") String customerName,
                                     @Query("address") String address,
                                     @Query("phone") String phone,
                                     @Query("email") String email);

    @Headers("Content-Type: application/json")
    @GET("/loyalty/order/{orderId}")
    Call<OrderLoyalty> getOrderLoyaltyHistoryInfo(@Path("orderId") long orderId);

    @Headers("Content-Type: application/json")
    @GET("/loyalty/order/cancelOrder/{orderId}")
    Call<AbstractMessage> cancelOrder(@Path("orderId") long orderId);

    @Headers("Content-Type: application/json")
    @GET("/loyalty/cart/deleteCart/{deviceToken}")
    Call<AbstractMessage> deleteCart(@Path("deviceToken") String deviceToken);

    @Headers("Content-Type: application/json")
    @GET("/loyalty/cart/{deviceToken}")
    Call<OrderLoyalty> getCartInfo(@Path("deviceToken") String deviceToken);

    @Headers("Content-Type: application/json")
    @GET("/location/searchNearBy")
    Call<List<LatitudeLongitude>> nearbySearch(@Query("type") String type, @Query("longitude") double longitude, @Query("latitude") double latitude);

    @Headers("Content-Type: application/json")
    @GET("/location/checkAdLocationsNearBy")
    Call<String> checkAdLocationsNearBy(@Query("deviceToken") String deviceToken, @Query("registrationToken") String registrationToken, @Query("longitude") double longitude,
                                        @Query("latitude") double latitude);


    @Headers("Content-Type: application/json")
    @GET("/user/redeemPoint/{deviceToken}")
    Call<String> redeemPoint(@Path("deviceToken") String deviceToken);
}
