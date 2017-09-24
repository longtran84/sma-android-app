package com.sma.mobile;

import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Application class for the contact explorer app.
 *
 * @since Android 4.4
 * Project name : SMA
 * Package name : com.smartsystemspro.sspschedule
 * File name    : SMAApplication.java
 * Author       : longtran
 * Create by    : longtran
 * Create date  : Oct 07, 2016 10:38:03 AM
 */

public class SMAApplication extends MultiDexApplication {

    private final String TAG = SMAApplication.class.getName();
//    private Retrofit retrofit;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        // Initialize Realm. Should only be done once when the application starts.
        Realm.init(this);
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder()
                .name("sma")
                .schemaVersion(2)
                .deleteRealmIfMigrationNeeded()
                .build();
        // Delete Realm between app restarts.
        //Realm.deleteRealm(realmConfiguration);
        Realm.setDefaultConfiguration(realmConfiguration);
        // Show log json response
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
        // Auto refresh token
        //clientBuilder.authenticator(new TokenAuthenticator());
        // Add interceptor
//        OkHttpClient client = clientBuilder
//                .readTimeout(120, TimeUnit.SECONDS)
//                .connectTimeout(120, TimeUnit.SECONDS)
//                .addInterceptor(interceptor)
//                .addInterceptor(new HeaderInterceptor())
//                .build();

//        retrofit = new Retrofit.Builder()
//                .baseUrl(Endpoint.ENDPOINT)
//                .client(client)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
    }

    /***
     * @return
     */
//    public Retrofit getRetrofit() {
//        return retrofit;
//    }

    /**
     * Recall WebTokens authentication
     */
//    private class TokenAuthenticator implements Authenticator {
//        @Override
//        public Request authenticate(Route routeAuthenticator, Response responseAuthenticator) throws IOException {
//            System.err.println(responseAuthenticator + "------------------");
//            SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
//            String registrationToken = pref.getString(Config.REGISTRATION_TOKENS, "Firebase Reg Id is not received yet!");
//            DevicesToken devicesToken = new DevicesToken();
//            devicesToken.setDeviceToken(registrationToken);
//            RealmResults<AuthenticatorRequest> realmResultsAuthenticatorBean = Realm.getDefaultInstance().where(AuthenticatorRequest.class).findAll();
//            if (null != realmResultsAuthenticatorBean && !realmResultsAuthenticatorBean.isEmpty()) {
//                String basicAuthentication = realmResultsAuthenticatorBean.first().getClientID()
//                        + ":" + realmResultsAuthenticatorBean.first().getClientSecret()
//                        + ":" + realmResultsAuthenticatorBean.first().getRestaurantCode();
//                basicAuthentication = Endpoint.TOKEN_TYPE + new String(Base64.encode(basicAuthentication.getBytes(), Base64.NO_WRAP));
//                LogHelper.i(TAG, "Refresh your access_token using a synchronous api request");
////                Call<SignInResponse> callAuthenticationService =
////                        getRetrofit().create(AuthenticationService.class).getAuthorization("application/json",
////                                basicAuthentication, new Gson().toJson(devicesToken));
////                callAuthenticationService.enqueue(new Callback<SignInResponse>() {
////                    @Override
////                    public void onResponse(Call<SignInResponse> call, retrofit2.Response<SignInResponse> _response) {
////                        final Headers headers = _response.headers();
////                        if (null != headers && !StringUtils.isEmpty(headers.get("Token"))) {
////                            final SignInResponse signInResponse = _response.body();
////                            final AuthenticatorResponse authenticatorResponse = new AuthenticatorResponse();
////                            authenticatorResponse.setUserId(signInResponse.getUserId());
////                            authenticatorResponse.setUserName(signInResponse.getUserName());
////                            authenticatorResponse.setAccessToken(headers.get("Token"));
////                            Realm realm = null;
////                            try {
////                                realm = Realm.getDefaultInstance();
////                                realm.executeTransaction(new Realm.Transaction() {
////                                    @Override
////                                    public void execute(Realm realm) {
////                                        realm.insertOrUpdate(authenticatorResponse);
////                                    }
////                                });
////                            } finally {
////                                if (realm != null) {
////                                    realm.close();
////                                }
////                            }
////                        }
////                    }
////
////                    @Override
////                    public void onFailure(Call<SignInResponse> call, Throwable t) {
////                        LogHelper.e(TAG, t.toString());
////                    }
////                });
//                return responseAuthenticator.request().newBuilder()
//                        .addHeader("Content-Type", "application/json")
//                        .addHeader("Authorization", realmResultsAuthenticatorBean.first().getTokenType() + " " + basicAuthentication)
//                        .build();
//            } else {
//                return null;
//            }
//        }
//    }

    /**
     * auto add header to each request
     */
//    private class HeaderInterceptor implements Interceptor {
//
//        private Request request;
//
//        @Override
//        public Response intercept(final Chain chain)
//                throws IOException {
//            request = chain.request();
//            SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
//            String headerToken = pref.getString(Config.HEADER_TOKEN, null);
//            if (!StringUtils.isBlank(headerToken)) {
//                LogHelper.i(TAG, headerToken);
//                request = request.newBuilder()
//                        .addHeader("Content-Type", "application/json")
//                        .addHeader("Token", headerToken)
//                        //.addHeader("Authorization", authenticatorResponse.getTokenType() + " " + authenticatorResponse.getAccessToken())
//                        .build();
//            }
////            Realm realm = null;
////            try {
////                realm = Realm.getDefaultInstance();
////                realm.executeTransaction(new Realm.Transaction() {
////                    @Override
////                    public void execute(Realm realm) {
////                        RealmResults<AuthenticatorResponse> realmResultsAuthenticatorResponse =
////                                realm.where(AuthenticatorResponse.class).findAll();
////                        if (realmResultsAuthenticatorResponse.size() > 0) {
////                            AuthenticatorResponse authenticatorResponse = realmResultsAuthenticatorResponse.first();
////
////                        }
////                    }
////                });
////            } catch (Exception exception) {
////                exception.printStackTrace();
////            } finally {
////                if (realm != null) {
////                    realm.close();
////                }
////            }
//            Response response = chain.proceed(request);
//            //GetCountMessageByReadFlag?readFlag=0
//            LogHelper.d(TAG, "Code : " + response.code());
//            if (response.code() == 401 && ((request.url().toString().indexOf("GetCountMessageByReadFlag") < 0)
//                    && request.url().toString().indexOf("GetMessageChat") < 0)) {
//                // Magic is here ( Handle the error as your way )
//                sendBroadcastUnauthorized();
//                return response;
//            }
//            return response;
//        }
//    }
}
