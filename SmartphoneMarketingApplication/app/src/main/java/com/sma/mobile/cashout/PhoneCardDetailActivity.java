package com.sma.mobile.cashout;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.RequestOptions;
import com.fintechviet.android.sdk.FintechvietSdk;
import com.fintechviet.android.sdk.listener.JCallback;
import com.fintechviet.android.sdk.model.AbstractMessage;
import com.fintechviet.android.sdk.model.ErrorResponse;
import com.fintechviet.android.sdk.model.PhoneCardResponse;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.sma.mobile.R;
import com.sma.mobile.activities.AbstractAppCompatActivity;
import com.sma.mobile.utils.firebasenotifications.Config;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import edu.counterview.CounterListner;
import edu.counterview.CounterView;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by QLong on 11/30/2017.
 */

public class PhoneCardDetailActivity extends AbstractAppCompatActivity implements View.OnClickListener {

    @BindView(R.id.app_compat_image_view_icon_id)
    AppCompatImageView appCompatImageView;

    @BindView(R.id.app_compat_text_view_title)
    AppCompatTextView app_compat_text_view_title;

    @BindView(R.id.app_compat_text_view_total_cost)
    AppCompatTextView app_compat_text_view_total_cost;

    @BindView(R.id.app_compat_text_view_cost_converter)
    AppCompatTextView app_compat_text_view_cost_converter;

    private int counterValue = 1;

    @BindView(R.id.app_compat_button_buy_now)
    AppCompatButton app_compat_button_buy_now;

    @BindView(R.id.linear_layout_button_buy_now)
    LinearLayout linear_layout_button_buy_now;

    @BindView(R.id.app_compat_image_view_button_buy_now)
    AppCompatImageView app_compat_image_view_button_buy_now;

    PhoneCardResponse phoneCardResponse;
    CounterView counterView;

    @Override
    public int getFragmentContainerViewId() {
        return 0;
    }

    public static String currencyFormat(BigDecimal n) {
        return NumberFormat.getCurrencyInstance().format(n);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_card_detail);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // add back arrow to toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        final DecimalFormat formatter = new DecimalFormat("###,###,###.##");
        phoneCardResponse = (PhoneCardResponse) getIntent().getExtras().getSerializable(PhoneCardResponse.class.getName());
        phoneCardResponse.setQuantityOrdered(counterValue);
        getSupportActionBar().setTitle(phoneCardResponse.getName());
        app_compat_text_view_title.setText(String.format("%s", phoneCardResponse.getName()));
        app_compat_text_view_total_cost.setText(
                String.format("Đơn giá: %s", formatter.format(phoneCardResponse.getPrice().multiply(new BigDecimal(counterValue))) + " đồng"
                ));
        app_compat_text_view_cost_converter.setText(
                String.format("Quy đổi: %s điểm", formatter.format(phoneCardResponse.getPointExchange().multiply(new BigDecimal(counterValue))))
        );
        counterView = (CounterView) findViewById(R.id.spinner_quality);
        counterView.setStartCounterValue("1");
        counterView.setCounterListener(new CounterListner() {
            @Override
            public void onIncClick(String s) {
                counterValue++;
                phoneCardResponse.setQuantityOrdered(counterValue);
//                app_compat_text_view_total_cost.setText(String.format("Đơn giá %s",
//                        formatter.format(phoneCardResponse.getPrice().multiply(new BigDecimal(counterValue))) + " đ"
//                ));
                app_compat_text_view_cost_converter.setText(
                        String.format("Quy đổi: %s điểm", formatter.format(phoneCardResponse.getPointExchange().multiply(new BigDecimal(counterValue))))
                );
            }

            @Override
            public void onDecClick(String s) {
                counterValue--;
                if (counterValue < 1) {
                    counterValue = 1;
                }
                phoneCardResponse.setQuantityOrdered(counterValue);
//                app_compat_text_view_total_cost.setText(String.format("Đơn giá %s",
//                        formatter.format(phoneCardResponse.getPrice().multiply(new BigDecimal(counterValue))) + " đ"
//                ));
                app_compat_text_view_cost_converter.setText(
                        String.format("Quy đổi: %s điểm", formatter.format(phoneCardResponse.getPointExchange().multiply(new BigDecimal(counterValue))))
                );
            }
        });

        if (!StringUtils.isBlank(phoneCardResponse.getPicture())) {
            Glide.with(getApplicationContext())
                    .load(phoneCardResponse.getPicture())
//                    .apply(RequestOptions.bitmapTransform(new MultiTransformation<>(new CenterCrop(),
//                            new RoundedCornersTransformation(0, 0, RoundedCornersTransformation.CornerType.ALL))))
                    .into(appCompatImageView);

        }
        app_compat_button_buy_now.setOnClickListener(this);
        linear_layout_button_buy_now.setOnClickListener(this);
        app_compat_image_view_button_buy_now.setOnClickListener(this);
//        if (phoneCardResponse.getQuantity() <= 0) {
//            linear_layout_button_buy_now.setEnabled(false);
//            app_compat_button_buy_now.setEnabled(false);
//            app_compat_image_view_button_buy_now.setEnabled(false);
//            counterView.setEnabled(false);
//        } else {
//            linear_layout_button_buy_now.setEnabled(true);
//            app_compat_button_buy_now.setEnabled(true);
//            app_compat_image_view_button_buy_now.setEnabled(true);
//            counterView.setEnabled(true);
//        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1984) {
            if (resultCode == Activity.RESULT_OK) {
                finish();
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
//        SharedPreferences pref = getSharedPreferences(Config.SHARED_PREF, 0);
//        final String registrationToken = pref.getString(Config.REGISTRATION_TOKENS, "ERROR");
        FintechvietSdk.getInstance().deleteCart(getDeviceSerialNumber(), new JCallback<AbstractMessage>() {
            @Override
            public void onResponse(Call<AbstractMessage> call, Response<AbstractMessage> response) {

            }

            @Override
            public void onFailure(Call<AbstractMessage> call, Throwable t) {

            }
        });
    }

    @Override
    public void onClick(View v) {
//        SharedPreferences pref = getSharedPreferences(Config.SHARED_PREF, 0);
//        final String registrationToken = pref.getString(Config.REGISTRATION_TOKENS, "ERROR");
        FintechvietSdk.getInstance().addToCart(getDeviceSerialNumber(), phoneCardResponse.getVoucherId(),
                phoneCardResponse.getQuantityOrdered(), phoneCardResponse.getPrice().toPlainString(),
                        /*phoneCardResponse.getType()*/"PHONE_CARD", new JCallback<AbstractMessage>() {
                    @Override
                    public void onResponse(Call<AbstractMessage> call, Response<AbstractMessage> response) {
                        if (response.code() == 200) {
                            Intent intent = new Intent();
                            intent.setClassName(PhoneCardDetailActivity.this, OrderActivity.class.getName());
                            Bundle mBundle = new Bundle();
                            mBundle.putSerializable(PhoneCardResponse.class.getName(), phoneCardResponse);
                            intent.putExtras(mBundle);
                            startActivityForResult(intent, 1984);
                        } else {
                            Gson gson = new Gson();
                            TypeAdapter<ErrorResponse> adapter = gson.getAdapter(ErrorResponse.class);
                            try {
                                if (response.errorBody() != null) {
                                    ErrorResponse errorResponse = adapter.fromJson(response.errorBody().string());
                                    getMaterialDialogAlert(PhoneCardDetailActivity.this, "Thông báo",
                                            errorResponse.getError() != null ? errorResponse.getError() : "Xảy ra một lỗi từ máy chủ", new MaterialDialog.SingleButtonCallback() {
                                                @Override
                                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                                    finish();
                                                }
                                            }).show();
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<AbstractMessage> call, Throwable t) {
                        getMaterialDialogAlert(PhoneCardDetailActivity.this, "Thông báo", "Xảy ra một lỗi từ máy chủ").show();
                    }
                });
    }
}
