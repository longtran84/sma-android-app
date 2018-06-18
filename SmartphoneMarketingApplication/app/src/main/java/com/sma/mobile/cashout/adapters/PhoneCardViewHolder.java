package com.sma.mobile.cashout.adapters;

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

public class PhoneCardViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private RecyclerViewOnItemClickListener recyclerViewOnItemClickListener;

    @BindView(R.id.image_view_app_icon_id)
    public ImageView imageViewAppIcon;

    @BindView(R.id.text_view_detail)
    public TextView textViewDetail;

    @BindView(R.id.text_view_point_exchange_text)
    public TextView pointExchangeText;

    public PhoneCardViewHolder(View itemView,
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
