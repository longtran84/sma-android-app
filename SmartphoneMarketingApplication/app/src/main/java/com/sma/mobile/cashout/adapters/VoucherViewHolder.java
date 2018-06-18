package com.sma.mobile.cashout.adapters;

import android.graphics.Paint;
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

public class VoucherViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private RecyclerViewOnItemClickListener recyclerViewOnItemClickListener;

    @BindView(R.id.image_view_app_icon_id)
    public ImageView imageViewAppIcon;

    @BindView(R.id.text_view_price)
    public TextView textViewPrice;

    @BindView(R.id.text_view_market_price)
    public TextView textViewMarketPrice;

    @BindView(R.id.text_view_description)
    public TextView textViewDescription;

    @BindView(R.id.text_view_discount)
    public TextView textViewDiscount;

    @BindView(R.id.text_view_pay_now_oversell)
    public TextView text_view_oversell;

    @BindView(R.id.text_view_point_exchange_text)
    public TextView pointExchangeText;

    public VoucherViewHolder(View itemView,
                             RecyclerViewOnItemClickListener recyclerViewOnItemClickListener) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.recyclerViewOnItemClickListener = recyclerViewOnItemClickListener;
        itemView.setOnClickListener(this);
        // Set TextView text strike through
        textViewMarketPrice.setPaintFlags(textViewMarketPrice.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);
    }

    @Override
    public void onClick(View v) {
        recyclerViewOnItemClickListener.onClick(v, getAdapterPosition());
    }
}
