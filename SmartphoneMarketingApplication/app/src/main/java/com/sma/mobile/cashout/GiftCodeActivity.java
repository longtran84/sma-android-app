package com.sma.mobile.cashout;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.fintechviet.android.sdk.FintechvietSdk;
import com.fintechviet.android.sdk.listener.JCallback;
import com.fintechviet.android.sdk.model.GiftCodeResponse;
import com.fintechviet.android.sdk.model.PhoneCardResponse;
import com.sma.mobile.R;
import com.sma.mobile.activities.AbstractAppCompatActivity;
import com.sma.mobile.cashout.adapters.GiftCardAdapter;
import com.sma.mobile.cashout.adapters.PhoneCardAdapter;
import com.sma.mobile.favourite.RecyclerViewOnItemClickListener;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Response;

public class GiftCodeActivity extends AbstractAppCompatActivity {

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    private List<GiftCodeResponse> items;
    private GiftCardAdapter phoneCardAdapter;

    @Override
    public int getFragmentContainerViewId() {
        return 0;
    }

    /***
     *
     */
    private void redirectVoucherDetailActivity(Activity activity) {
        Intent intent = new Intent();
        intent.setClassName(activity, VoucherDetailActivity.class.getName());
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voucher);
        ButterKnife.bind(this);
        items = new ArrayList<>();
        phoneCardAdapter = new GiftCardAdapter(this, items, new RecyclerViewOnItemClickListener() {
            @Override
            public void onClick(View v, int position) {
                redirectVoucherDetailActivity(GiftCodeActivity.this);
            }
        });
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setHasFixedSize(true);
        Paint paint = new Paint();
        paint.setStrokeWidth(1);
        paint.setColor(Color.TRANSPARENT);
        paint.setAntiAlias(true);
        paint.setPathEffect(new DashPathEffect(new float[]{25.0f, 25.0f}, 0));
        mRecyclerView.addItemDecoration(
                new HorizontalDividerItemDecoration.Builder(getApplicationContext()).paint(paint).build());
        mRecyclerView.setAdapter(phoneCardAdapter);
        showProcessing();
        FintechvietSdk.getInstance().getListGiftCodeResponse(new JCallback<List<GiftCodeResponse>>() {
            @Override
            public void onResponse(Call<List<GiftCodeResponse>> call, Response<List<GiftCodeResponse>> response) {
                if (response.code() == 200) {
                    List<GiftCodeResponse> listVoucherResponse = response.body();
                    for(GiftCodeResponse giftCodeResponse : listVoucherResponse){
                        items.add(giftCodeResponse);
                        phoneCardAdapter.notifyDataSetChanged();
                    }
                }
                hideProcessing();
            }

            @Override
            public void onFailure(Call<List<GiftCodeResponse>> call, Throwable t) {
                hideProcessing();
            }
        });
    }
}
