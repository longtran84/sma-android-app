package com.sma.mobile.cashout;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.fintechviet.android.sdk.FintechvietSdk;
import com.fintechviet.android.sdk.listener.JCallback;
import com.fintechviet.android.sdk.model.PhoneCardResponse;
import com.sma.mobile.R;
import com.sma.mobile.activities.AbstractAppCompatActivity;
import com.sma.mobile.cashout.adapters.PhoneCardAdapter;
import com.sma.mobile.favourite.RecyclerViewOnItemClickListener;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Response;

public class PhoneCardActivity extends AbstractAppCompatActivity {

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    private List<PhoneCardResponse> items;
    private PhoneCardAdapter phoneCardAdapter;

    @Override
    public int getFragmentContainerViewId() {
        return 0;
    }

    /***
     *
     */
    private void redirectPhoneCardDetailActivity(Activity activity, PhoneCardResponse phoneCardResponse) {
        Intent intent = new Intent();
        intent.setClassName(activity, PhoneCardDetailActivity.class.getName());
        Bundle mBundle = new Bundle();
        mBundle.putSerializable(PhoneCardResponse.class.getName(), phoneCardResponse);
        intent.putExtras(mBundle);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voucher);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (null != toolbar) {
            setSupportActionBar(toolbar);
            // add back arrow to toolbar
            if (getSupportActionBar() != null) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setDisplayShowHomeEnabled(true);
            }
        }
        getSupportActionBar().setTitle("Thẻ điện thoại");
        showProcessing();
        items = new ArrayList<>();
        phoneCardAdapter = new PhoneCardAdapter(this, items, new RecyclerViewOnItemClickListener() {
            @Override
            public void onClick(View v, int position) {
                redirectPhoneCardDetailActivity(PhoneCardActivity.this, items.get(position));
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
        FintechvietSdk.getInstance().getListPhoneCardResponse(new JCallback<List<PhoneCardResponse>>() {
            @Override
            public void onResponse(Call<List<PhoneCardResponse>> call, Response<List<PhoneCardResponse>> response) {
                if (response.code() == 200) {
                    List<PhoneCardResponse> listVoucherResponse = response.body();
                    for(PhoneCardResponse phoneCardResponse : listVoucherResponse){
                        items.add(phoneCardResponse);
                        phoneCardAdapter.notifyDataSetChanged();
                    }
                }
                hideProcessing();
            }

            @Override
            public void onFailure(Call<List<PhoneCardResponse>> call, Throwable t) {
                hideProcessing();
            }
        });
    }
}
