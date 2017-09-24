package com.fintechviet.android.sdk;

import com.fintechviet.android.sdk.content.NewsResponse;
import com.fintechviet.android.sdk.ad.DecisionResponse;
import com.fintechviet.android.sdk.ad.Request;
import com.fintechviet.android.sdk.listener.JCallback;
import com.fintechviet.android.sdk.model.ArticlesResponse;
import com.fintechviet.android.sdk.model.Favourite;
import com.fintechviet.android.sdk.user.Reward;
import com.fintechviet.android.sdk.user.User;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

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
    static final String FINTECHVIET_ENDPOINT = "http://222.252.16.132:9000";
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
                }
            }
        }).start();
    }

    /**
     * Send a request to the Native Ads API.
     * This is an asynchronous request, results will be returned to the given listener.
     *
     * @param request  ad Request specifying one or more Placements
     * @param listener Can be null, but caller will never get notifications.
     */
    public void requestPlacement(Request request, final DecisionListener listener) {
        getFintechvietService().request(request).enqueue(new Callback<DecisionResponse>() {
            @Override
            public void onResponse(Call<DecisionResponse> call, Response<DecisionResponse> response) {
                if (listener != null) {
                    listener.success(response.body());
                }
            }

            @Override
            public void onFailure(Call<DecisionResponse> call, Throwable t) {
                if (listener != null) {
                    //listener.error(new FintechvietError(error));
                }
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

    public void updateUserInfo(String deviceToken, String email, String gender, int dob, String location) {
        getFintechvietService().updateUserInfo(deviceToken, email, gender, dob, location).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }

    public void updateUserReward(String deviceToken, String event, long addedpoint) {
        getFintechvietService().updateUserReward(deviceToken, event, addedpoint).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }

    public User getUserInfo(String deviceToken) {
        getFintechvietService().getUserInfo(deviceToken).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {

            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

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
        try {
            callbackServer(new URL(CONTENT_IMPRESSION_ENDPOINT));
            return true;
        } catch (MalformedURLException e) {
            return false;
        }
    }

    public boolean saveContentClick() {
        try {
            callbackServer(new URL(CONTENT_CLICK_ENDPOINT));
            return true;
        } catch (MalformedURLException e) {
            return false;
        }
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
     * @param fromDate
     * @param toDate
     * @param jCallback
     */
    public void getArticlesResponse(String deviceToken, String fromDate, String toDate, final JCallback jCallback) {
        getFintechvietService().getArticlesResponse(deviceToken, fromDate, toDate).enqueue(new Callback<ArticlesResponse>() {
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
                    .readTimeout(120, TimeUnit.SECONDS)
                    .connectTimeout(120, TimeUnit.SECONDS)
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
