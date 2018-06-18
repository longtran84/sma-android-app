package com.sma.mobile.admob;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.sma.mobile.R;
import com.sma.mobile.favourite.RecyclerViewOnItemClickListener;

import org.sufficientlysecure.htmltextview.HtmlTextView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by longtran on 17/01/2017.
 */

public class AdMobRewardedViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private RecyclerViewOnItemClickListener recyclerViewOnItemClickListener;

    @BindView(R.id.image_view_app_icon_id)
    public ImageView imageViewAppIcon;

    @BindView(R.id.text_view_description)
    public TextView textViewDescription;

    public AdMobRewardedViewHolder(View itemView,
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
