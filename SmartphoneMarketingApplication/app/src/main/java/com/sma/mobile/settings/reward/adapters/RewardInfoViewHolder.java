package com.sma.mobile.settings.reward.adapters;

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

public class RewardInfoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private RecyclerViewOnItemClickListener recyclerViewOnItemClickListener;

    @BindView(R.id.text_view_reward_name_id)
    public AppCompatTextView textViewRewardName;

    @BindView(R.id.text_view_amount_id)
    public AppCompatTextView textViewAmount;

    public RewardInfoViewHolder(View itemView,
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
