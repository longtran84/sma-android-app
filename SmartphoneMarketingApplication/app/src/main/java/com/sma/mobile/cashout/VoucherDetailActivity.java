package com.sma.mobile.cashout;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;

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
import com.fintechviet.android.sdk.model.VoucherImagesResponse;
import com.fintechviet.android.sdk.model.VoucherResponse;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.hkm.ezwebview.webviewleakfix.NonLeakingWebView;
import com.sma.mobile.R;
import com.sma.mobile.activities.AbstractAppCompatActivity;
import com.sma.mobile.cashout.adapters.RecyclerViewAdapter;
import com.sma.mobile.utils.firebasenotifications.Config;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import android.view.GestureDetector;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import edu.counterview.CounterListner;
import edu.counterview.CounterView;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;
import retrofit2.Call;
import retrofit2.Response;

public class VoucherDetailActivity extends AbstractAppCompatActivity implements View.OnClickListener {

    @BindView(R.id.imgImage1)
    ImageView imgImage1;

    @BindView(R.id.webviewDes)
    NonLeakingWebView webView;

    @BindView(R.id.recycler_view_slideshow_images)
    RecyclerView recyclerView;

    @BindView(R.id.text_view_title_id)
    AppCompatTextView text_view_title_id;

    @BindView(R.id.text_view_price_id)
    AppCompatTextView textViewPrice;

    @BindView(R.id.textViewMarketPrice)
    AppCompatTextView textViewMarketPrice;

    @BindView(R.id.tvSalePercent)
    AppCompatTextView textViewDiscount;

    @BindView(R.id.voucher_quantity_id)
    AppCompatTextView voucher_quantity_id;

    @BindView(R.id.point_exchange_id)
    AppCompatTextView point_exchange_id;

    @BindView(R.id.app_compat_button_buy_now)
    AppCompatButton app_compat_button_buy_now;

    @BindView(R.id.linear_layout_button_buy_now)
    LinearLayout linear_layout_button_buy_now;

    @BindView(R.id.app_compat_image_view_button_buy_now)
    AppCompatImageView app_compat_image_view_button_buy_now;

    CounterView counterView;

    private int counterValue = 1;

    private ArrayList<String> arrayListImageSlideShow;
    private RecyclerView.LayoutManager recyclerViewLayoutManager;
    private RecyclerViewAdapter recyclerViewHorizontalAdapter;
    private LinearLayoutManager horizontalLayout;
    private View childView;
    private int recyclerViewItemPosition;
    private VoucherResponse voucherResponse;

    @Override
    public int getFragmentContainerViewId() {
        return 0;
    }

    /**
     * This method converts dp unit to equivalent pixels, depending on device density.
     *
     * @param dp A value in dp (density independent pixels) unit. Which we need to convert into pixels
     * @param context Context to get resources and device specific display metrics
     * @return A float value to represent px equivalent to dp depending on device density
     */
    public static float convertDpToPixel(float dp, Context context){
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * ((float)metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return px;
    }

    /**
     * This method converts device specific pixels to density independent pixels.
     *
     * @param px A value in px (pixels) unit. Which we need to convert into db
     * @param context Context to get resources and device specific display metrics
     * @return A float value to represent dp equivalent to px value
     */
    public static float convertPixelsToDp(float px, Context context){
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float dp = px / ((float)metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return dp;
    }

    public static int convertDpToSp(float dp, Context context) {
        int sp = (int) (convertDpToPixel(dp, context) / (float) convertDpToPixel(dp, context));
        return sp;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.voucher_detail_activity);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // add back arrow to toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        showProcessing();
        voucherResponse = (VoucherResponse) getIntent().getExtras().getSerializable(VoucherResponse.class.getName());
        voucherResponse.setQuantityOrdered(counterValue);
        WebSettings settings = webView.getSettings();
        settings.setMinimumFontSize(35);
        settings.setLoadWithOverviewMode(true);
        settings.setUseWideViewPort(true);
        settings.setBuiltInZoomControls(true);
        settings.setDisplayZoomControls(false);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        webView.clearCache(true);
        webView.clearHistory();
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setHorizontalScrollBarEnabled(false);
        final DecimalFormat formatter = new DecimalFormat("###,###,###.##");
        counterView = (CounterView) findViewById(R.id.spinner_quality);
        counterView.setStartCounterValue("1");
        counterView.setCounterListener(new CounterListner() {
            @Override
            public void onIncClick(String s) {
                counterValue++;
                textViewPrice.setText(String.format("%s điểm", formatter.format(voucherResponse.getPrice().multiply(new BigDecimal(counterValue)))));
                point_exchange_id.setText(String.format("Quy đổi: %s điểm",
                        formatter.format(voucherResponse.getPointExchange().multiply(new BigDecimal(counterValue))))
                );
                voucherResponse.setQuantityOrdered(counterValue);
            }

            @Override
            public void onDecClick(String s) {
                counterValue--;
                if (counterValue < 1) {
                    counterValue = 1;
                }
                textViewPrice.setText(String.format("%s điểm", formatter.format(voucherResponse.getPrice().multiply(new BigDecimal(counterValue)))));
                point_exchange_id.setText(String.format("Quy đổi: %s điểm",
                        formatter.format(voucherResponse.getPointExchange().multiply(new BigDecimal(counterValue))))
                );
                voucherResponse.setQuantityOrdered(counterValue);
            }
        });
        app_compat_button_buy_now.setOnClickListener(this);
        linear_layout_button_buy_now.setOnClickListener(this);
        app_compat_image_view_button_buy_now.setOnClickListener(this);

        recyclerViewLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(recyclerViewLayoutManager);
        // Adding items to RecyclerView.
        arrayListImageSlideShow = new ArrayList<>();
        recyclerViewHorizontalAdapter = new RecyclerViewAdapter(this, arrayListImageSlideShow);
        horizontalLayout = new LinearLayoutManager(VoucherDetailActivity.this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(horizontalLayout);
        recyclerView.setAdapter(recyclerViewHorizontalAdapter);

        // Adding on item click listener to RecyclerView.
        recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            GestureDetector gestureDetector = new GestureDetector(VoucherDetailActivity.this, new GestureDetector.SimpleOnGestureListener() {

                @Override
                public boolean onSingleTapUp(MotionEvent motionEvent) {
                    return true;
                }

            });

            @Override
            public boolean onInterceptTouchEvent(RecyclerView Recyclerview, MotionEvent motionEvent) {
                childView = Recyclerview.findChildViewUnder(motionEvent.getX(), motionEvent.getY());
                if (childView != null && gestureDetector.onTouchEvent(motionEvent)) {
                    //Getting clicked value.
                    recyclerViewItemPosition = Recyclerview.getChildAdapterPosition(childView);
                    // Showing clicked item value on screen using toast message.
                    //Toast.makeText(VoucherDetailActivity.this, arrayListImageSlideShow.get(recyclerViewItemPosition), Toast.LENGTH_LONG).show();
                    if (!StringUtils.isBlank(arrayListImageSlideShow.get(recyclerViewItemPosition))) {
                        Glide.with(getApplicationContext())
                                .load(arrayListImageSlideShow.get(recyclerViewItemPosition))
                                .apply(RequestOptions.bitmapTransform(new MultiTransformation<>(new CenterCrop(),
                                        new RoundedCornersTransformation(0, 0, RoundedCornersTransformation.CornerType.ALL))))
                                .into(imgImage1);
                    }
                }
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView Recyclerview, MotionEvent motionEvent) {
            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
            }
        });

        FintechvietSdk.getInstance().getListVoucherResponse(voucherResponse.getVoucherId(), new JCallback<VoucherResponse>() {
            @Override
            public void onResponse(Call<VoucherResponse> call, Response<VoucherResponse> response) {
                if (response.code() == 200) {
                    VoucherResponse voucherResponseResponse = response.body();
                    if (!StringUtils.isBlank(voucherResponseResponse.getPicture())) {
                        Glide.with(getApplicationContext())
                                .load(voucherResponseResponse.getPicture())
                                .apply(RequestOptions.bitmapTransform(new MultiTransformation<>(new CenterCrop(),
                                        new RoundedCornersTransformation(0, 0, RoundedCornersTransformation.CornerType.ALL))))
                                .into(imgImage1);
                    }
                    final DecimalFormat formatter = new DecimalFormat("###,###,###.##");
                    webView.loadData(voucherResponseResponse.getDescription(), "text/html; charset=utf-8", "utf-8");
                    webView.requestLayout();
                    text_view_title_id.setText(voucherResponseResponse.getName());
                    getSupportActionBar().setTitle(voucherResponseResponse.getName());
                    textViewPrice.setText(String.format("%s điểm", formatter.format(voucherResponseResponse.getPrice())));
                    textViewMarketPrice.setPaintFlags(textViewMarketPrice.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);
                    textViewMarketPrice.setText(
                            String.format(
                                    "Giá gốc: %s điểm", formatter.format(voucherResponseResponse.getMarketPrice())
                            )
                    );
                    BigDecimal discountTempSubtract = voucherResponseResponse.getMarketPrice().subtract(voucherResponseResponse.getPrice());
                    BigDecimal discountTempDivide = discountTempSubtract.divide(voucherResponseResponse.getMarketPrice(), 2, RoundingMode.FLOOR);
                    BigDecimal discountTempMultiply = discountTempDivide.multiply(new BigDecimal(100)).setScale(0, RoundingMode.FLOOR);;
                    textViewDiscount.setText(discountTempMultiply.toPlainString() + "%");
                    voucher_quantity_id.setText(String.format("%d voucher", voucherResponseResponse.getQuantity()));
                    if(voucherResponseResponse.getQuantity() <= 0){
                        linear_layout_button_buy_now.setEnabled(false);
                        app_compat_button_buy_now.setEnabled(false);
                        app_compat_image_view_button_buy_now.setEnabled(false);
                        counterView.setEnabled(false);
                    }else{
                        linear_layout_button_buy_now.setEnabled(true);
                        app_compat_button_buy_now.setEnabled(true);
                        app_compat_image_view_button_buy_now.setEnabled(true);
                        counterView.setEnabled(true);
                    }
                    point_exchange_id.setText(String.format("%s điểm", formatter.format(voucherResponseResponse.getPointExchange())));
                }
                hideProcessing();
            }

            @Override
            public void onFailure(Call<VoucherResponse> call, Throwable t) {
                hideProcessing();
            }
        });

        FintechvietSdk.getInstance().getVoucherImages(voucherResponse.getVoucherId(), new JCallback<List<VoucherImagesResponse>>() {
            @Override
            public void onResponse(Call<List<VoucherImagesResponse>> call, Response<List<VoucherImagesResponse>> response) {
                if (response.code() == 200) {
                    List<VoucherImagesResponse> listVoucherImagesResponse = response.body();
                    for (VoucherImagesResponse voucherImagesResponse : listVoucherImagesResponse) {
                        arrayListImageSlideShow.add(voucherImagesResponse.getImage());
                        recyclerViewHorizontalAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<VoucherImagesResponse>> call, Throwable t) {

            }
        });
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
        //todo
//        final String registrationToken = pref.getString(Config.REGISTRATION_TOKENS, "ERROR");
        FintechvietSdk.getInstance().addToCart(getDeviceSerialNumber(), voucherResponse.getVoucherId(),
                voucherResponse.getQuantityOrdered(), voucherResponse.getPrice().toPlainString(),
                        /*voucherResponse.getType()*/"VOUCHER", new JCallback<AbstractMessage>() {
                    @Override
                    public void onResponse(Call<AbstractMessage> call, Response<AbstractMessage> response) {
                        if (response.code() == 200) {
                            Intent intent = new Intent();
                            intent.setClassName(VoucherDetailActivity.this, OrderActivity.class.getName());
                            Bundle mBundle = new Bundle();
                            mBundle.putSerializable(VoucherResponse.class.getName(), voucherResponse);
                            intent.putExtras(mBundle);
                            startActivityForResult(intent, 1984);
                        } else {
                            Gson gson = new Gson();
                            TypeAdapter<ErrorResponse> adapter = gson.getAdapter(ErrorResponse.class);
                            try {
                                if (response.errorBody() != null) {
                                    ErrorResponse errorResponse = adapter.fromJson(response.errorBody().string());
                                    getMaterialDialogAlert(VoucherDetailActivity.this, "Thông báo",
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
                        getMaterialDialogAlert(VoucherDetailActivity.this, "Thông báo", "Xảy ra một lỗi từ máy chủ").show();
                    }
                });
    }
}
