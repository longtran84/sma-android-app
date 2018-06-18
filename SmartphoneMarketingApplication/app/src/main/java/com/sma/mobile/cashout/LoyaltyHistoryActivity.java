package com.sma.mobile.cashout;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.fintechviet.android.sdk.FintechvietSdk;
import com.fintechviet.android.sdk.listener.JCallback;
import com.fintechviet.android.sdk.model.AbstractMessage;
import com.fintechviet.android.sdk.model.ErrorResponse;
import com.fintechviet.android.sdk.model.OrderLoyalty;
import com.fintechviet.android.sdk.model.PhoneCardResponse;
import com.fintechviet.android.sdk.model.VoucherResponse;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.sma.mobile.R;
import com.sma.mobile.activities.AbstractAppCompatActivity;
import com.sma.mobile.cashout.adapters.OrderStatus;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by longtran on 10/12/2017.
 */

public class LoyaltyHistoryActivity extends AbstractAppCompatActivity {

    @BindView(R.id.appCompat_text_view_left1)
    public AppCompatTextView appCompat_text_view_left1;

    @BindView(R.id.appCompat_text_view_right1)
    public AppCompatTextView appCompat_text_view_right1;

    @BindView(R.id.appCompat_text_view_left2)
    public AppCompatTextView appCompat_text_view_left2;

    @BindView(R.id.appCompat_text_view_right2)
    public AppCompatTextView appCompat_text_view_right2;

    @BindView(R.id.appCompat_text_view_left4)
    public AppCompatTextView appCompat_text_view_left4;

    @BindView(R.id.appCompat_text_view_right4)
    public AppCompatTextView appCompat_text_view_right4;

    @BindView(R.id.appCompat_text_view_left5)
    public AppCompatTextView appCompat_text_view_left5;

    @BindView(R.id.appCompat_text_view_right5)
    public AppCompatTextView appCompat_text_view_right5;

    @BindView(R.id.appCompat_text_view_left6)
    public AppCompatTextView appCompat_text_view_left6;

    @BindView(R.id.appCompat_text_view_right6)
    public AppCompatTextView appCompat_text_view_right6;

    @BindView(R.id.appCompat_text_view1)
    public AppCompatTextView appCompat_text_view1;

    @BindView(R.id.appCompat_text_view2)
    public AppCompatTextView appCompat_text_view2;

    @BindView(R.id.appCompat_text_view3)
    public AppCompatTextView appCompat_text_view3;

    @BindView(R.id.image_view_app_icon_id)
    public AppCompatImageView image_view_app_icon_id;

    @BindView(R.id.appCompat_text_view4)
    public AppCompatTextView appCompat_text_view4;

    @BindView(R.id.appCompat_text_view5)
    public AppCompatTextView appCompat_text_view5;

    @BindView(R.id.app_compat_button_cancel_order)
    public AppCompatButton app_compat_button_cancel_order;

    private OrderLoyalty orderLoyaltyResponse;

    @Override
    public int getFragmentContainerViewId() {
        return 0;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loyalty_history);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // add back arrow to toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        getSupportActionBar().setTitle("Lịch sử trả thưởng");
        final DecimalFormat formatter = new DecimalFormat("###,###,###.##");
        final OrderLoyalty orderLoyalty = (OrderLoyalty) getIntent().getExtras().getSerializable(OrderLoyalty.class.getName());
        FintechvietSdk.getInstance().getOrderLoyaltyHistoryInfo(orderLoyalty.getOrderId(), new JCallback<OrderLoyalty>() {
            @Override
            public void onResponse(Call<OrderLoyalty> call, Response<OrderLoyalty> response) {
                if (response.code() == 200) {
                    orderLoyaltyResponse = response.body();
                    appCompat_text_view_left1.setText(String.format("#%d", orderLoyaltyResponse.getOrderId()));
                    appCompat_text_view_right1.setText(new SimpleDateFormat("dd/MM/yyyy").format(new Date(orderLoyaltyResponse.getCreatedDate())));
                    appCompat_text_view_left2.setText("Tình trạng đơn hàng");
                    appCompat_text_view_right2.setText(OrderStatus.valueOfOrderStatus(orderLoyalty.getStatus()));
                    if (orderLoyalty.getStatus().equalsIgnoreCase(OrderStatus.CANCELLED.getKey())
                            || orderLoyalty.getStatus().equalsIgnoreCase(OrderStatus.CLOSE.getKey())) {
                        app_compat_button_cancel_order.setVisibility(View.INVISIBLE);
                    } else {
                        app_compat_button_cancel_order.setVisibility(View.VISIBLE);
                    }
                    appCompat_text_view_left4.setText("Tổng tiền");
                    appCompat_text_view_right4.setText(String.format("%s điểm", formatter.format(orderLoyalty.getTotal())));
                    appCompat_text_view_left5.setText("Phí vận chuyển");
                    appCompat_text_view_right5.setText("Miễn phí");
                    appCompat_text_view_left6.setText("Tổng điểm đổi");
                    appCompat_text_view_right6.setText(String.format("%s điểm", formatter.format(orderLoyalty.getTotalPoint())));
                    VoucherResponse voucherResponse = orderLoyaltyResponse.getVoucherResponse();
                    PhoneCardResponse phoneCardResponse = orderLoyaltyResponse.getPhoneCardResponse();
                    if (null != voucherResponse) {
                        appCompat_text_view1.setText(voucherResponse.getName());
                        appCompat_text_view2.setText(String.format("%s điểm", formatter.format(voucherResponse.getPrice())));
                        appCompat_text_view3.setText(String.format("Số lượng: %d", orderLoyaltyResponse.getQuantity()));
                        if (!StringUtils.isBlank(voucherResponse.getPicture())) {
                            Glide.with(getApplicationContext())
                                    .load(voucherResponse.getPicture())
                                    .into(image_view_app_icon_id);

                        }
                        appCompat_text_view4.setText("Địa chỉ nhận voucher");
                        appCompat_text_view5.setText(orderLoyaltyResponse.getAddress());
                    } else if (null != phoneCardResponse) {
                        appCompat_text_view1.setText(phoneCardResponse.getName());
                        appCompat_text_view2.setText(String.format("%s điểm", formatter.format(phoneCardResponse.getPrice())));
                        appCompat_text_view3.setText(String.format("Số lượng: %d", orderLoyaltyResponse.getQuantity()));
                        if (!StringUtils.isBlank(phoneCardResponse.getPicture())) {
                            Glide.with(getApplicationContext())
                                    .load(phoneCardResponse.getPicture())
                                    .into(image_view_app_icon_id);

                        }
                        appCompat_text_view4.setText("Địa chỉ nhận thẻ điện thoại");
                        appCompat_text_view5.setText(orderLoyaltyResponse.getAddress());
                    }
                }
            }

            @Override
            public void onFailure(Call<OrderLoyalty> call, Throwable t) {

            }
        });
        app_compat_button_cancel_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FintechvietSdk.getInstance().cancelOrder(orderLoyaltyResponse.getOrderId(), new JCallback<AbstractMessage>() {
                    @Override
                    public void onResponse(Call<AbstractMessage> call, Response<AbstractMessage> response) {
                        if (response.code() == 200) {
                            getMaterialDialogAlert(LoyaltyHistoryActivity.this, "Thông báo", response.body().getMessage(),
                                    new MaterialDialog.SingleButtonCallback() {
                                        @Override
                                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                            finish();
                                        }
                                    }).show();
                        } else {
                            Gson gson = new Gson();
                            TypeAdapter<ErrorResponse> adapter = gson.getAdapter(ErrorResponse.class);
                            try {
                                if (response.errorBody() != null) {
                                    ErrorResponse errorResponse = adapter.fromJson(response.errorBody().string());
                                    getMaterialDialogAlert(LoyaltyHistoryActivity.this, "Thông báo",
                                            errorResponse.getError() != null ? errorResponse.getError() : "Xảy ra một lỗi từ máy chủ").show();
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<AbstractMessage> call, Throwable t) {

                    }
                });
            }
        });
    }
}
