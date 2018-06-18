package com.fintechviet.android.sdk;

import com.fintechviet.android.sdk.ad.AdMob;
import com.fintechviet.android.sdk.content.NewsResponse;
import com.fintechviet.android.sdk.ad.DecisionResponse;
import com.fintechviet.android.sdk.ad.Request;
import com.fintechviet.android.sdk.listener.JCallback;
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
import com.fintechviet.android.sdk.model.TopAds;
import com.fintechviet.android.sdk.model.VoucherImagesResponse;
import com.fintechviet.android.sdk.model.VoucherResponse;
import com.fintechviet.android.sdk.user.Reward;
import com.fintechviet.android.sdk.user.User;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Query;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by tungn on 9/7/2017.
 */
public class FintechvietSdk {
    static final String TAG = FintechvietSdk.class.getSimpleName();
    static final String FINTECHVIET_ENDPOINT = "http://222.252.16.132:9000";//222.252.16.132
    //static final String FINTECHVIET_ENDPOINT = "http://localhost:9000";
    static final String CONTENT_IMPRESSION_ENDPOINT = FINTECHVIET_ENDPOINT + "/content/impression";
    static final String CONTENT_CLICK_ENDPOINT = FINTECHVIET_ENDPOINT + "/content/click";

    static FintechvietSdk instance;

    FintechvietService service;

    public static class FintechvietError {
        int statusCode;
        String reason;

        public FintechvietError(int statusCode, String reason, Exception exception) {
            this.statusCode = statusCode;
            this.reason = reason;
        }

        public int getStatusCode() {
            return statusCode;
        }

        public String getReason() {
            return reason;
        }
    }

    /**
     * Listener for the DecisionResponse to an ad placement Request
     */
    public interface DecisionListener {
        public void success(DecisionResponse response);

        public void error(FintechvietError error);
    }

    /**
     * Listener for the NewsResponse to user
     */
    public interface NewsListener {
        public void success(NewsResponse response);

        public void error(FintechvietError error);
    }

    /**
     * Listener for the Response to user
     */
    public interface CommonListener {
        public void success(Response response);

        public void error(FintechvietError error);
    }

    /**
     * Returns the SDK instance for making Fintechviet API calls.
     *
     * @return sdk instance
     */
    public static FintechvietSdk getInstance() {
        if (instance == null) {
            instance = new FintechvietSdk();
        }

        return instance;
    }

    protected void callbackServer(final URL url) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    url.openConnection().getContent();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * Send a request to the Native Ads API.
     * This is an asynchronous request, results will be returned to the given listener.
     *
     * @param template  ad Request specifying one or more Placements
     * @param jCallback Can be null, but caller will never get notifications.
     */
    public void requestPlacement(String template, String deviceToken, final JCallback jCallback) {
        getFintechvietService().placement(template, deviceToken, 2).enqueue(new Callback<DecisionResponse>() {
            @Override
            public void onResponse(Call<DecisionResponse> call, Response<DecisionResponse> response) {
                jCallback.onResponse(call, response);
            }

            @Override
            public void onFailure(Call<DecisionResponse> call, Throwable t) {
                jCallback.onFailure(call, t);
            }
        });
    }

    public void getNewsByUserInterest(String deviceToken, String cateIds, String lastNewsIds, final NewsListener listener) {
        getFintechvietService().getNewsByUserInterest(deviceToken, cateIds, lastNewsIds).enqueue(new Callback<NewsResponse>() {
            @Override
            public void onResponse(Call<NewsResponse> call, Response<NewsResponse> response) {
                if (listener != null) {
                    listener.success(response.body());
                }
            }

            @Override
            public void onFailure(Call<NewsResponse> call, Throwable t) {
                if (listener != null) {
                    //listener.error(new FintechvietError(error));
                }
            }
        });
    }

    public void updateUserInfo(String deviceToken, String email, String gender, String dob, String location, String inviteCode, final JCallback jCallback) {
        getFintechvietService().updateUserInfo(deviceToken, email, gender, dob, location, inviteCode).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                jCallback.onResponse(call, response);
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                jCallback.onFailure(call, t);
            }
        });
    }

    public void registerUser(String deviceId, final JCallback jCallback) {
        getFintechvietService().registerUser(deviceId).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                jCallback.onResponse(call, response);
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                jCallback.onFailure(call, t);
            }
        });
    }

    public void updateUserReward(String deviceToken, String event, int addedpoint, final JCallback<String> jCallback) {
        getFintechvietService().updateUserReward(deviceToken, event, addedpoint).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                jCallback.onResponse(call, response);
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                jCallback.onFailure(call, t);
            }
        });
    }

    public void redeemPoint(String deviceToken, final JCallback<String> jCallback) {
        getFintechvietService().redeemPoint(deviceToken).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                jCallback.onResponse(call, response);
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                jCallback.onFailure(call, t);
            }
        });
    }

    public User getUserInfo(String deviceToken, final JCallback jCallback) {
        getFintechvietService().getUserInfo(deviceToken).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                jCallback.onResponse(call, response);
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                jCallback.onFailure(call, t);
            }
        });
        return null;
    }

    public List<Reward> getUserRewardInfo(String deviceToken) {
        getFintechvietService().getUserRewardInfo(deviceToken).enqueue(new Callback<List<Reward>>() {
            @Override
            public void onResponse(Call<List<Reward>> call, Response<List<Reward>> response) {

            }

            @Override
            public void onFailure(Call<List<Reward>> call, Throwable t) {

            }
        });
        return null;
    }

    /**
     * Converts the given String to an impression URL.
     *
     * @param urlString
     * @return - false if it is malformed
     */
    public boolean saveAdActivity(final String urlString) {
        try {
            callbackServer(new URL(urlString));
            return true;
        } catch (MalformedURLException e) {
            return false;
        }
    }

    public boolean saveContentImpression() {
        getFintechvietService().contentImpression().enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
        return true;
    }

    public boolean saveContentClick() {
        getFintechvietService().contentClick().enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
        return true;
    }

    /***
     *
     * @param deviceToken
     * @param jCallback
     */
    public void getListFavourite(String deviceToken, final JCallback jCallback) {

        getFintechvietService().getListFavourite().enqueue(new Callback<List<Favourite>>() {
            @Override
            public void onResponse(Call<List<Favourite>> call, Response<List<Favourite>> response) {
                jCallback.onResponse(call, response);
            }

            @Override
            public void onFailure(Call<List<Favourite>> call, Throwable t) {
                jCallback.onFailure(call, t);
            }
        });
    }

    /***
     *
     * @param deviceToken
     * @param page
     * @param jCallback
     */
    public void getArticlesResponse(String deviceToken, int page, String newsId, final JCallback jCallback) {
        getFintechvietService().getArticlesResponse(deviceToken, page, newsId).enqueue(new Callback<ArticlesResponse>() {
            @Override
            public void onResponse(Call<ArticlesResponse> call, Response<ArticlesResponse> response) {
                jCallback.onResponse(call, response);
            }

            @Override
            public void onFailure(Call<ArticlesResponse> call, Throwable t) {
                jCallback.onFailure(call, t);
            }
        });
    }

    /***
     *
     * @param deviceToken
     * @param page
     * @param jCallback
     */
    public void _getArticlesResponseByInterests(String deviceToken, int page, final JCallback jCallback) {
//        getFintechvietService().getArticlesResponseByInterests(deviceToken, page).enqueue(new Callback<ArticlesResponse>() {
//            @Override
//            public void onResponse(Call<ArticlesResponse> call, Response<ArticlesResponse> response) {
//                jCallback.onResponse(call, response);
//            }
//
//            @Override
//            public void onFailure(Call<ArticlesResponse> call, Throwable t) {
//                jCallback.onFailure(call, t);
//            }
//        });
    }

    /***
     *
     * @param jCallback
     */
    public void contentClick(final JCallback jCallback) {
        getFintechvietService().contentClick().enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                jCallback.onResponse(call, response);
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                jCallback.onFailure(call, t);
            }
        });
    }

    /***
     *
     * @param jCallback
     */
    public void contentImpression(final JCallback jCallback) {
        getFintechvietService().contentImpression().enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                jCallback.onResponse(call, response);
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                jCallback.onFailure(call, t);
            }
        });
    }

    /***
     *
     * @param jCallback
     */
    public void appAds(final JCallback jCallback) {
        getFintechvietService().appAds().enqueue(new Callback<List<AdMob>>() {
            @Override
            public void onResponse(Call<List<AdMob>> call, Response<List<AdMob>> response) {
                jCallback.onResponse(call, response);
            }

            @Override
            public void onFailure(Call<List<AdMob>> call, Throwable t) {
                jCallback.onFailure(call, t);
            }
        });
    }

    /***
     *
     * @param adId
     * @param deviceToken
     * @param jCallback
     */
    public void adMobView(int adId, String deviceToken, final JCallback jCallback) {
        getFintechvietService().adMobView(adId, deviceToken).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                jCallback.onResponse(call, response);
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                jCallback.onFailure(call, t);
            }
        });
    }

    /***
     *
     * @param adId
     * @param deviceToken
     * @param jCallback
     */
    public void _adMobClick(int adId, String deviceToken, final JCallback jCallback) {
        getFintechvietService().adMobClick(adId, deviceToken).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                jCallback.onResponse(call, response);
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                jCallback.onFailure(call, t);
            }
        });
    }

    /***
     *
     * @param deviceToken
     * @param jCallback
     */
    public void getMessages(String deviceToken, final JCallback jCallback) {
        getFintechvietService().getMessages(deviceToken).enqueue(new Callback<List<Message>>() {
            @Override
            public void onResponse(Call<List<Message>> call, Response<List<Message>> response) {
                jCallback.onResponse(call, response);
            }

            @Override
            public void onFailure(Call<List<Message>> call, Throwable t) {
                jCallback.onFailure(call, t);
            }
        });
    }

    /**
     * @param deviceToken
     * @param inviteCode
     * @param jCallback
     */
    public void updateInviteCode(String deviceToken, String inviteCode, final JCallback<String> jCallback) {
        getFintechvietService().updateInviteCode(deviceToken, inviteCode).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                jCallback.onResponse(call, response);
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                jCallback.onFailure(call, t);
            }
        });
    }

    /***
     *
     * @param deviceToken
     * @param jCallback
     */
    public void getPromotionMessage(String deviceToken, String type, final JCallback jCallback) {
        getFintechvietService().getPromotionMessage(deviceToken, type).enqueue(new Callback<List<PromotionMessage>>() {
            @Override
            public void onResponse(Call<List<PromotionMessage>> call, Response<List<PromotionMessage>> response) {
                jCallback.onResponse(call, response);
            }

            @Override
            public void onFailure(Call<List<PromotionMessage>> call, Throwable t) {
                jCallback.onFailure(call, t);
            }
        });
    }

    /***
     *
     * @param id
     * @param jCallback
     */
    public void updateMessage(int id, final JCallback jCallback) {
        getFintechvietService().updateMessage(id).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                jCallback.onResponse(call, response);
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                jCallback.onFailure(call, t);
            }
        });
    }

    /***
     *
     * @param id
     * @param jCallback
     */
    public void updateMessagePromotion(int id, final JCallback jCallback) {
        getFintechvietService().updateMessagePromotion(id).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                jCallback.onResponse(call, response);
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                jCallback.onFailure(call, t);
            }
        });
    }

    /***
     *
     * @param jCallback
     */
    public void getNewsByCategory(String deviceToken, int page, String categoryCode, final JCallback<ListNewsByCategory> jCallback) {
        getFintechvietService().getNewsByCategory(deviceToken, page, categoryCode).enqueue(new Callback<ListNewsByCategory>() {
            @Override
            public void onResponse(Call<ListNewsByCategory> call, Response<ListNewsByCategory> response) {
                jCallback.onResponse(call, response);
            }

            @Override
            public void onFailure(Call<ListNewsByCategory> call, Throwable t) {
                jCallback.onFailure(call, t);
            }
        });
    }

    /***
     *
     * @param page
     * @param jCallback
     */
    public void adNews(int page, final JCallback jCallback) {
        getFintechvietService().adNews(page).enqueue(new Callback<List<ArticlesItem>>() {
            @Override
            public void onResponse(Call<List<ArticlesItem>> call, Response<List<ArticlesItem>> response) {
                jCallback.onResponse(call, response);
            }

            @Override
            public void onFailure(Call<List<ArticlesItem>> call, Throwable t) {
                jCallback.onFailure(call, t);
            }
        });
    }

    /***
     *
     * @param jCallback
     */
    public void games(final JCallback jCallback) {
        getFintechvietService().games().enqueue(new Callback<List<ArticlesGameItem>>() {
            @Override
            public void onResponse(Call<List<ArticlesGameItem>> call, Response<List<ArticlesGameItem>> response) {
                jCallback.onResponse(call, response);
            }

            @Override
            public void onFailure(Call<List<ArticlesGameItem>> call, Throwable t) {
                jCallback.onFailure(call, t);
            }
        });
    }

    /***
     *
     * @param deviceToken
     * @param jCallback
     */
    public void getRewardInfo(String deviceToken, final JCallback jCallback) {
        getFintechvietService().getRewardInfo(deviceToken).enqueue(new Callback<List<RewardInfoEntity>>() {
            @Override
            public void onResponse(Call<List<RewardInfoEntity>> call, Response<List<RewardInfoEntity>> response) {
                jCallback.onResponse(call, response);
            }

            @Override
            public void onFailure(Call<List<RewardInfoEntity>> call, Throwable t) {
                jCallback.onFailure(call, t);
            }
        });
    }

    /***
     *
     * @param deviceToken
     * @param rewardCode
     * @param addedPoint
     * @param jCallback
     */
    public void updateUserRewardPoint(String deviceToken, String rewardCode, int addedPoint, final JCallback jCallback) {
        getFintechvietService().updateUserRewardPoint(deviceToken, rewardCode, addedPoint).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                jCallback.onResponse(call, response);
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                jCallback.onFailure(call, t);
            }
        });
    }

    public void contentClick(String deviceToken, String newsId, int rewardPoint, final JCallback jCallback) {
        getFintechvietService().contentClick(deviceToken, newsId, rewardPoint).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                jCallback.onResponse(call, response);
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                jCallback.onFailure(call, t);
            }
        });
    }

    public List<ListTopAds> getListTopAds(String deviceToken, final JCallback jCallback) {
        getFintechvietService().getListTopAds(deviceToken).enqueue(new Callback<List<ListTopAds>>() {
            @Override
            public void onResponse(Call<List<ListTopAds>> call, Response<List<ListTopAds>> response) {
                jCallback.onResponse(call, response);
            }

            @Override
            public void onFailure(Call<List<ListTopAds>> call, Throwable t) {
                jCallback.onFailure(call, t);
            }
        });
        return null;
    }

    /***
     *
     * @param favouriteList
     * @param deviceToken
     * @param jCallback
     */
    public void updateFavouriteCategoriesByDevice(String favouriteList, String deviceToken, final JCallback jCallback) {
        getFintechvietService().updateFavouriteCategoriesByDevice(favouriteList, deviceToken).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                jCallback.onResponse(call, response);
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                jCallback.onFailure(call, t);
            }
        });
    }

    /***
     *
     * @param jCallback
     */
    public void getListVoucherResponse(final JCallback jCallback) {
        getFintechvietService().getListVoucherResponse().enqueue(new Callback<List<VoucherResponse>>() {
            @Override
            public void onResponse(Call<List<VoucherResponse>> call, Response<List<VoucherResponse>> response) {
                jCallback.onResponse(call, response);
            }

            @Override
            public void onFailure(Call<List<VoucherResponse>> call, Throwable t) {
                jCallback.onFailure(call, t);
            }
        });
    }

    /***
     *
     * @param voucherId
     * @param jCallback
     */
    public void getListVoucherResponse(long voucherId, final JCallback jCallback) {
        getFintechvietService().getVoucherDetail(voucherId).enqueue(new Callback<VoucherResponse>() {
            @Override
            public void onResponse(Call<VoucherResponse> call, Response<VoucherResponse> response) {
                jCallback.onResponse(call, response);
            }

            @Override
            public void onFailure(Call<VoucherResponse> call, Throwable t) {
                jCallback.onFailure(call, t);
            }
        });
    }

    /***
     *
     * @param voucherId
     * @param jCallback
     */
    public void getVoucherImages(long voucherId, final JCallback jCallback) {
        getFintechvietService().getVoucherImages(voucherId).enqueue(new Callback<List<VoucherImagesResponse>>() {
            @Override
            public void onResponse(Call<List<VoucherImagesResponse>> call, Response<List<VoucherImagesResponse>> response) {
                jCallback.onResponse(call, response);
            }

            @Override
            public void onFailure(Call<List<VoucherImagesResponse>> call, Throwable t) {
                jCallback.onFailure(call, t);
            }
        });
    }

    /****
     *
     * @param deviceToken
     * @param jCallback
     */
    public void getOrderLoyalty(String deviceToken, final JCallback jCallback) {
        getFintechvietService().getOrderLoyalty(deviceToken).enqueue(new Callback<List<OrderLoyalty>>() {
            @Override
            public void onResponse(Call<List<OrderLoyalty>> call, Response<List<OrderLoyalty>> response) {
                jCallback.onResponse(call, response);
            }

            @Override
            public void onFailure(Call<List<OrderLoyalty>> call, Throwable t) {
                jCallback.onFailure(call, t);
            }
        });
    }

    /***
     *
     * @param jCallback
     */
    public void getListPhoneCardResponse(final JCallback jCallback) {
        getFintechvietService().getListPhoneCardResponse().enqueue(new Callback<List<PhoneCardResponse>>() {
            @Override
            public void onResponse(Call<List<PhoneCardResponse>> call, Response<List<PhoneCardResponse>> response) {
                jCallback.onResponse(call, response);
            }

            @Override
            public void onFailure(Call<List<PhoneCardResponse>> call, Throwable t) {
                jCallback.onFailure(call, t);
            }
        });
    }

    /***
     *
     * @param jCallback
     */
    public void getListGiftCodeResponse(final JCallback jCallback) {
        getFintechvietService().getListGiftCodeResponse().enqueue(new Callback<List<GiftCodeResponse>>() {
            @Override
            public void onResponse(Call<List<GiftCodeResponse>> call, Response<List<GiftCodeResponse>> response) {
                jCallback.onResponse(call, response);
            }

            @Override
            public void onFailure(Call<List<GiftCodeResponse>> call, Throwable t) {
                jCallback.onFailure(call, t);
            }
        });
    }

    /***
     *
     * @param jCallback
     */
    public void getListGameCardResponse(final JCallback jCallback) {
        getFintechvietService().getListGameCardResponse().enqueue(new Callback<List<GameCardResponse>>() {
            @Override
            public void onResponse(Call<List<GameCardResponse>> call, Response<List<GameCardResponse>> response) {
                jCallback.onResponse(call, response);
            }

            @Override
            public void onFailure(Call<List<GameCardResponse>> call, Throwable t) {
                jCallback.onFailure(call, t);
            }
        });
    }

    /***
     *
     * @param deviceToken
     * @param itemId
     * @param quantity
     * @param price
     * @param type
     * @param jCallback
     */
    public void addToCart(String deviceToken,
                          long itemId,
                          int quantity,
                          String price,
                          String type,
                          final JCallback jCallback) {
        getFintechvietService().addToCart(deviceToken, itemId, quantity, price, type).enqueue(new Callback<AbstractMessage>() {
            @Override
            public void onResponse(Call<AbstractMessage> call, Response<AbstractMessage> response) {
                jCallback.onResponse(call, response);
            }

            @Override
            public void onFailure(Call<AbstractMessage> call, Throwable t) {
                jCallback.onFailure(call, t);
            }
        });
    }

    /***
     *
     * @param deviceToken
     * @param customerName
     * @param address
     * @param phone
     * @param email
     * @param jCallback
     */
    public void placeOrder(String deviceToken,
                           String customerName,
                           String address,
                           String phone,
                           String email,
                           final JCallback jCallback) {
        getFintechvietService().placeOrder(deviceToken, customerName, address, phone, email).enqueue(new Callback<AbstractMessage>() {
            @Override
            public void onResponse(Call<AbstractMessage> call, Response<AbstractMessage> response) {
                jCallback.onResponse(call, response);
            }

            @Override
            public void onFailure(Call<AbstractMessage> call, Throwable t) {
                jCallback.onFailure(call, t);
            }
        });
    }

    /***
     *
     * @param jCallback
     */
    public void getOrderLoyaltyHistoryInfo(long orderId, final JCallback jCallback) {
        getFintechvietService().getOrderLoyaltyHistoryInfo(orderId).enqueue(new Callback<OrderLoyalty>() {
            @Override
            public void onResponse(Call<OrderLoyalty> call, Response<OrderLoyalty> response) {
                jCallback.onResponse(call, response);
            }

            @Override
            public void onFailure(Call<OrderLoyalty> call, Throwable t) {
                jCallback.onFailure(call, t);
            }
        });
    }

    /***
     *
     * @param jCallback
     */
    public void cancelOrder(long orderId, final JCallback jCallback) {
        getFintechvietService().cancelOrder(orderId).enqueue(new Callback<AbstractMessage>() {
            @Override
            public void onResponse(Call<AbstractMessage> call, Response<AbstractMessage> response) {
                jCallback.onResponse(call, response);
            }

            @Override
            public void onFailure(Call<AbstractMessage> call, Throwable t) {
                jCallback.onFailure(call, t);
            }
        });
    }

    /***
     *
     * @param jCallback
     */
    public void deleteCart(String deviceToken, final JCallback jCallback) {
        getFintechvietService().deleteCart(deviceToken).enqueue(new Callback<AbstractMessage>() {
            @Override
            public void onResponse(Call<AbstractMessage> call, Response<AbstractMessage> response) {
                jCallback.onResponse(call, response);
            }

            @Override
            public void onFailure(Call<AbstractMessage> call, Throwable t) {
                jCallback.onFailure(call, t);
            }
        });
    }

    /***
     *
     * @param jCallback
     */
    public void getCartInfo(String deviceToken, final JCallback jCallback) {
        getFintechvietService().getCartInfo(deviceToken).enqueue(new Callback<OrderLoyalty>() {
            @Override
            public void onResponse(Call<OrderLoyalty> call, Response<OrderLoyalty> response) {
                jCallback.onResponse(call, response);
            }

            @Override
            public void onFailure(Call<OrderLoyalty> call, Throwable t) {
                jCallback.onFailure(call, t);
            }
        });
    }

    /**
     * @param type
     * @param longitude
     * @param latitude
     * @param deviceToken
     * @param jCallback
     */
    public void nearbySearch(String type, double longitude, double latitude, String deviceToken, final JCallback<List<LatitudeLongitude>> jCallback) {
        getFintechvietService().nearbySearch(type, longitude, latitude).enqueue(new Callback<List<LatitudeLongitude>>() {
            @Override
            public void onResponse(Call<List<LatitudeLongitude>> call, Response<List<LatitudeLongitude>> response) {
                jCallback.onResponse(call, response);
            }

            @Override
            public void onFailure(Call<List<LatitudeLongitude>> call, Throwable t) {
                jCallback.onFailure(call, t);
            }
        });
    }

    /**
     * @param deviceToken
     * @param registrationToken
     * @param longitude
     * @param latitude
     * @param jCallback
     */
    public void checkAdLocationsNearBy(String deviceToken, String registrationToken, double longitude, double latitude, final JCallback<String> jCallback) {
        getFintechvietService().checkAdLocationsNearBy(deviceToken, registrationToken, longitude, latitude).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                jCallback.onResponse(call, response);
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                jCallback.onFailure(call, t);
            }
        });
    }

    // Create service for the Fintechviet REST endpoint
    private FintechvietService getFintechvietService() {
        if (service == null) {
//            RestAdapter.Builder builder = new RestAdapter.Builder()
//                    .setEndpoint(FINTECHVIET_ENDPOINT)
//                    .setLogLevel(RestAdapter.LogLevel.NONE);

            // Show log json response
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();

            OkHttpClient client = clientBuilder
                    .readTimeout(30, TimeUnit.SECONDS)
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .addInterceptor(interceptor)
                    //.addInterceptor(new HeaderInterceptor())
                    .build();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(FINTECHVIET_ENDPOINT)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            service = retrofit.create(FintechvietService.class);
        }
        return service;
    }

}
