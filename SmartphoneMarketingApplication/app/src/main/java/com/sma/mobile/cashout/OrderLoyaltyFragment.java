package com.sma.mobile.cashout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fintechviet.android.sdk.FintechvietSdk;
import com.fintechviet.android.sdk.listener.JCallback;
import com.fintechviet.android.sdk.model.OrderLoyalty;
import com.fintechviet.android.sdk.model.PhoneCardResponse;
import com.sma.mobile.R;
import com.sma.mobile.activities.AbstractFragment;
import com.sma.mobile.cashout.adapters.OrderLoyaltyAdapter;
import com.sma.mobile.favourite.RecyclerViewOnItemClickListener;
import com.sma.mobile.utils.firebasenotifications.Config;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Response;

public class OrderLoyaltyFragment extends AbstractFragment {

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    private final boolean GRID_LAYOUT = false;
    private OrderLoyaltyAdapter orderLoyaltyAdapter;
    private List<OrderLoyalty> itemList;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_order_loyalty, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        //showProcessing();
        if (GRID_LAYOUT) {
            mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        } else {
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        }
        mRecyclerView.setHasFixedSize(true);
        Paint paint = new Paint();
        paint.setStrokeWidth(1);
        paint.setColor(Color.TRANSPARENT);
        paint.setAntiAlias(true);
        paint.setPathEffect(new DashPathEffect(new float[]{25.0f, 25.0f}, 0));
        mRecyclerView.addItemDecoration(
                new HorizontalDividerItemDecoration.Builder(getContext()).paint(paint).build());
        itemList = new ArrayList<>();
        orderLoyaltyAdapter = new OrderLoyaltyAdapter(getContext(), itemList, new RecyclerViewOnItemClickListener() {
            @Override
            public void onClick(View v, int position) {
                try {
                    Intent intent = new Intent();
                    intent.setClassName(getActivity(), LoyaltyHistoryActivity.class.getName());
                    Bundle mBundle = new Bundle();
                    mBundle.putSerializable(OrderLoyalty.class.getName(), itemList.get(position));
                    intent.putExtras(mBundle);
                    startActivity(intent);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }

            }
        });
        mRecyclerView.setAdapter(orderLoyaltyAdapter);
    }

    public static OrderLoyaltyFragment newInstance() {
        return new OrderLoyaltyFragment();
    }

    @Override
    public void onResume() {
        super.onResume();
//        SharedPreferences pref = getActivity().getSharedPreferences(Config.SHARED_PREF, 0);
//        String registrationToken = pref.getString(Config.REGISTRATION_TOKENS, "ERROR");
        //todo
        FintechvietSdk.getInstance().getOrderLoyalty(getDeviceSerialNumber(), new JCallback<List<OrderLoyalty>>() {
            @Override
            public void onResponse(Call<List<OrderLoyalty>> call, Response<List<OrderLoyalty>> response) {
                if (response.code() == 200) {
                    itemList.clear();
                    List<OrderLoyalty> listOrderLoyalty = response.body();
                    for (OrderLoyalty orderLoyalty : listOrderLoyalty) {
                        itemList.add(orderLoyalty);
                        orderLoyaltyAdapter.notifyDataSetChanged();
                    }
                }
                //hideProcessing();
            }

            @Override
            public void onFailure(Call<List<OrderLoyalty>> call, Throwable t) {
                //hideProcessing();
            }
        });
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
    }
}