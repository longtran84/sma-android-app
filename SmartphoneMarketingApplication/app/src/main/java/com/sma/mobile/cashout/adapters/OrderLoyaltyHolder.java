package com.sma.mobile.cashout.adapters;

import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.sma.mobile.R;
import com.sma.mobile.favourite.RecyclerViewOnItemClickListener;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by longtran on 17/01/2017.
 */

public class OrderLoyaltyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private RecyclerViewOnItemClickListener recyclerViewOnItemClickListener;

    @BindView(R.id.appCompat_text_view_left)
    public AppCompatTextView appCompat_text_view_left;

    @BindView(R.id.appCompat_text_view_right)
    public AppCompatTextView appCompat_text_view_right;

    @BindView(R.id.image_view_app_icon_id)
    public AppCompatImageView imageViewAppIcon;

    @BindView(R.id.appCompat_text_view1)
    public AppCompatTextView appCompat_text_view1;

    @BindView(R.id.appCompat_text_view2)
    public AppCompatTextView appCompat_text_view2;

    @BindView(R.id.appCompat_text_view3)
    public AppCompatTextView appCompat_text_view3;

    @BindView(R.id.appCompat_text_view4)
    public AppCompatTextView appCompat_text_view4;

    @BindView(R.id.appCompat_text_view5)
    public AppCompatTextView appCompat_text_view5;

    public OrderLoyaltyHolder(View itemView,
                              RecyclerViewOnItemClickListener recyclerViewOnItemClickListener) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.recyclerViewOnItemClickListener = recyclerViewOnItemClickListener;
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        recyclerViewOnItemClickListener.onClick(v, getAdapterPosition());
    }
}
