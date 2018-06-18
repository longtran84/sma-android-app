package com.sma.mobile.cashout;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.LinearLayout;

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
import com.fintechviet.android.sdk.model.GameCardResponse;
import com.fintechviet.android.sdk.model.OrderLoyalty;
import com.fintechviet.android.sdk.model.PhoneCardResponse;
import com.fintechviet.android.sdk.model.VoucherResponse;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.sma.mobile.R;
import com.sma.mobile.activities.AbstractAppCompatActivity;
import com.sma.mobile.utils.firebasenotifications.Config;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.Annotation;
import java.text.DecimalFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Converter;
import retrofit2.Response;

/**
 * Created by longtran on 08/12/2017.
 */

public class OrderActivity extends AbstractAppCompatActivity implements View.OnClickListener {

    @BindView(R.id.app_compat_image_view_icon_id)
    AppCompatImageView app_compat_image_view_icon_id;

    @BindView(R.id.app_compat_text_view_title)
    AppCompatTextView app_compat_text_view_title;

    @BindView(R.id.app_compat_text_view_quality_voucher)
    AppCompatTextView app_compat_text_view_quality_voucher;

    @BindView(R.id.app_compat_text_view_total_payment)
    AppCompatTextView app_compat_text_view_total_payment;

    @BindView(R.id.app_compat_text_view_total_point)
    AppCompatTextView app_compat_text_view_total_point;

    @BindView(R.id.app_compat_text_view_transportation_cost)
    AppCompatTextView app_compat_text_view_transportation_cost;

    @BindView(R.id.app_compat_text_view_shipping_addresses_name)
    AppCompatEditText app_compat_text_view_shipping_addresses_name;

    @BindView(R.id.app_compat_text_view_shipping_addresses_addresses)
    AppCompatEditText app_compat_text_view_shipping_addresses_addresses;

    @BindView(R.id.app_compat_text_view_shipping_addresses_phone)
    AppCompatEditText app_compat_text_view_shipping_addresses_phone;

    @BindView(R.id.app_compat_text_view_shipping_addresses_email)
    AppCompatEditText app_compat_text_view_shipping_addresses_email;

    @BindView(R.id.app_compat_button_buy_now)
    AppCompatButton app_compat_button_buy_now;

    @BindView(R.id.linear_layout_button_buy_now)
    LinearLayout linear_layout_button_buy_now;

    @BindView(R.id.app_compat_image_view_button_buy_now)
    AppCompatImageView app_compat_image_view_button_buy_now;

    private VoucherResponse voucherResponse;
    private PhoneCardResponse phoneCardResponse;
    private GameCardResponse gameCardResponse;

    @Override
    public int getFragmentContainerViewId() {
        return 0;
    }

    /**
     * This method converts dp unit to equivalent pixels, depending on device density.
     *
     * @param dp      A value in dp (density independent pixels) unit. Which we need to convert into pixels
     * @param context Context to get resources and device specific display metrics
     * @return A float value to represent px equivalent to dp depending on device density
     */
    public float convertDpToPixel(float dp, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * ((float) metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return px;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // add back arrow to toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        getSupportActionBar().setTitle("Đặt hàng");
        //https://stackoverflow.com/questions/4605527/converting-pixels-to-dp
        //https://www.bountysource.com/issues/45564012-how-to-resize-image-and-set-scale-type-to-fitxy
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.override((int) convertDpToPixel(128, getApplicationContext()), (int) convertDpToPixel(128, getApplicationContext()));

        final DecimalFormat formatter = new DecimalFormat("###,###,###.##");
        Serializable serializableVoucherResponse = getIntent().getExtras().getSerializable(VoucherResponse.class.getName());
        if (null != serializableVoucherResponse) {
            voucherResponse = (VoucherResponse) serializableVoucherResponse;
            if (!StringUtils.isBlank(voucherResponse.getPicture())) {
                Glide.with(getApplicationContext())
                        .load(voucherResponse.getPicture())
                        .apply(requestOptions)
//                        .apply(RequestOptions.bitmapTransform(new MultiTransformation<>(new CenterCrop(),
//                                new RoundedCornersTransformation(0, 0, RoundedCornersTransformation.CornerType.ALL))))
                        .into(app_compat_image_view_icon_id);
            }
            app_compat_text_view_title.setText(voucherResponse.getName());
            app_compat_text_view_quality_voucher.setText(String.format("Số lượng: %d", voucherResponse.getQuantityOrdered()));
            app_compat_text_view_total_payment.setText(String.format("%s điểm",
                    formatter.format(voucherResponse.getPrice().multiply(new BigDecimal(voucherResponse.getQuantityOrdered())))));
            app_compat_text_view_total_point.setText(String.format("%s điểm",
                    formatter.format(voucherResponse.getPointExchange().multiply(new BigDecimal(voucherResponse.getQuantityOrdered())))));
            app_compat_text_view_transportation_cost.setText("Miễn phí");
        }
        Serializable serializablePhoneCardResponse = getIntent().getExtras().getSerializable(PhoneCardResponse.class.getName());
        if (null != serializablePhoneCardResponse) {
            phoneCardResponse = (PhoneCardResponse) serializablePhoneCardResponse;
            if (!StringUtils.isBlank(phoneCardResponse.getPicture())) {
                Glide.with(getApplicationContext())
                        .load(phoneCardResponse.getPicture())
                        .apply(requestOptions)
//                        .apply(RequestOptions.bitmapTransform(new MultiTransformation<>(new CenterCrop(),
//                                new RoundedCornersTransformation(0, 0, RoundedCornersTransformation.CornerType.ALL))))
                        .into(app_compat_image_view_icon_id);
            }
            app_compat_text_view_title.setText(phoneCardResponse.getName());
            app_compat_text_view_quality_voucher.setText(String.format("Số lượng: %d", phoneCardResponse.getQuantityOrdered()));
            app_compat_text_view_total_payment.setText(String.format("%s điểm",
                    formatter.format(phoneCardResponse.getPrice().multiply(new BigDecimal(phoneCardResponse.getQuantityOrdered())))));
            app_compat_text_view_total_point.setText(String.format("%s điểm",
                    formatter.format(phoneCardResponse.getPointExchange().multiply(new BigDecimal(phoneCardResponse.getQuantityOrdered())))));
            app_compat_text_view_transportation_cost.setText("Miễn phí");
        }
        Serializable serializableGameCardResponse = getIntent().getExtras().getSerializable(GameCardResponse.class.getName());
        if (null != serializableGameCardResponse) {
            gameCardResponse = (GameCardResponse) serializableGameCardResponse;
            if (!StringUtils.isBlank(gameCardResponse.getPicture())) {
                Glide.with(getApplicationContext())
                        .load(gameCardResponse.getPicture())
                        .apply(requestOptions)
//                        .apply(RequestOptions.bitmapTransform(new MultiTransformation<>(new CenterCrop(),
//                                new RoundedCornersTransformation(0, 0, RoundedCornersTransformation.CornerType.ALL))))
                        .into(app_compat_image_view_icon_id);
            }
            app_compat_text_view_title.setText(gameCardResponse.getName());
            app_compat_text_view_quality_voucher.setText(String.format("Số lượng: %d", gameCardResponse.getQuantityOrdered()));
            app_compat_text_view_total_payment.setText(String.format("%s điểm",
                    formatter.format(gameCardResponse.getPrice().multiply(new BigDecimal(gameCardResponse.getQuantityOrdered())))));
            app_compat_text_view_total_point.setText(String.format("%s điểm",
                    formatter.format(gameCardResponse.getPointExchange().multiply(new BigDecimal(gameCardResponse.getQuantityOrdered())))));
            app_compat_text_view_transportation_cost.setText("Miễn phí");
        }
        app_compat_button_buy_now.setOnClickListener(this);
        linear_layout_button_buy_now.setOnClickListener(this);
        app_compat_image_view_button_buy_now.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        showProcessing();
        final String customerName = app_compat_text_view_shipping_addresses_name.getText().toString();
        final String address = app_compat_text_view_shipping_addresses_addresses.getText().toString();
        final String phone = app_compat_text_view_shipping_addresses_phone.getText().toString();
        final String email = app_compat_text_view_shipping_addresses_email.getText().toString();
        if (StringUtils.isBlank(customerName)
                || StringUtils.isBlank(address)
                || StringUtils.isBlank(phone)
                || StringUtils.isBlank(email)) {
            hideProcessing();
            getMaterialDialogAlert(OrderActivity.this, "Thông báo", "Vui lòng nhập đủ thông tin").show();
        } else {
//            SharedPreferences pref = getSharedPreferences(Config.SHARED_PREF, 0);
//            final String registrationToken = pref.getString(Config.REGISTRATION_TOKENS, "ERROR");
//            long itemId = 0;
//            int quantity = 0;
//            String price = "";
//            String type = "";
//            //Add item to cart. Type = VOUCHER, GAME_CARD, PHONE_CARD, GIFT_CODE
//            if (null != voucherResponse) {
//                type = "VOUCHER";
//                itemId = voucherResponse.getVoucherId();
//                quantity = voucherResponse.getQuantityOrdered();
//                price = voucherResponse.getPrice().toPlainString();
//            }
//            if (null != phoneCardResponse) {
//                type = "PHONE_CARD";
//                itemId = phoneCardResponse.getVoucherId();
//                quantity = phoneCardResponse.getQuantityOrdered();
//                price = phoneCardResponse.getPrice().toPlainString();
//            }
//            if (null != gameCardResponse) {
//                type = "GAME_CARD";
//                itemId = gameCardResponse.getVoucherId();
//                quantity = gameCardResponse.getQuantityOrdered();
//                price = gameCardResponse.getPrice().toPlainString();
//            }
            FintechvietSdk.getInstance().getCartInfo(getDeviceSerialNumber(), new JCallback<OrderLoyalty>() {
                @Override
                public void onResponse(Call<OrderLoyalty> call, Response<OrderLoyalty> response) {
                    if (response.code() == 200) {
                        FintechvietSdk.getInstance().placeOrder(getDeviceSerialNumber(), customerName, address, phone, email, new JCallback<AbstractMessage>() {
                            @Override
                            public void onResponse(Call<AbstractMessage> call, Response<AbstractMessage> response) {
                                hideProcessing();
                                if (response.code() == 200) {
                                    getMaterialDialogAlert(OrderActivity.this, "Thông báo", response.body().getMessage(), new MaterialDialog.SingleButtonCallback() {
                                        @Override
                                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                            Intent returnIntent = new Intent();
                                            setResult(Activity.RESULT_OK, returnIntent);
                                            finish();
                                        }
                                    }).show();
                                } else {
                                    Gson gson = new Gson();
                                    TypeAdapter<ErrorResponse> adapter = gson.getAdapter(ErrorResponse.class);
                                    try {
                                        if (response.errorBody() != null) {
                                            ErrorResponse errorResponse = adapter.fromJson(response.errorBody().string());
                                            getMaterialDialogAlert(OrderActivity.this, "Thông báo",
                                                    errorResponse.getError() != null ? errorResponse.getError() : "Xảy ra một lỗi từ máy chủ").show();
                                        }
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<AbstractMessage> call, Throwable t) {
                                hideProcessing();
                                getMaterialDialogAlert(OrderActivity.this, "Thông báo", "Xảy ra một lỗi từ máy chủ").show();
                            }
                        });
                    } else {
                        hideProcessing();
                        Gson gson = new Gson();
                        TypeAdapter<ErrorResponse> adapter = gson.getAdapter(ErrorResponse.class);
                        try {
                            if (response.errorBody() != null) {
                                ErrorResponse errorResponse = adapter.fromJson(response.errorBody().string());
                                getMaterialDialogAlert(OrderActivity.this, "Thông báo",
                                        errorResponse.getError() != null ? errorResponse.getError() : "Xảy ra một lỗi từ máy chủ").show();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(Call<OrderLoyalty> call, Throwable t) {
                    hideProcessing();
                    getMaterialDialogAlert(OrderActivity.this, "Thông báo", "Xảy ra một lỗi từ máy chủ").show();
                }
            });
        }
    }
}
