package com.sma.mobile.settings.reward.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.fintechviet.android.sdk.model.RewardInfoEntity;
import com.sma.mobile.R;
import com.sma.mobile.favourite.RecyclerViewOnItemClickListener;
import com.sma.mobile.utils.viewholder.ProgressViewHolder;

import org.apache.commons.lang3.StringUtils;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by longtran on 17/01/2017.
 */

public class RewardInfoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<RewardInfoEntity> itemList;
    private Context context;
    private static final int VIEW_TYPE_ITEM = 0;
    private static final int VIEW_TYPE_LOADING = 1;
    private RecyclerViewOnItemClickListener recyclerViewOnItemClickListener;

    /***
     * @param context
     * @param itemList
     */
    public RewardInfoAdapter(Context context, List<RewardInfoEntity> itemList,
                             RecyclerViewOnItemClickListener
                                        recyclerViewOnItemClickListener) {
        this.itemList = itemList;
        this.context = context;
        this.recyclerViewOnItemClickListener = recyclerViewOnItemClickListener;
    }

    @Override
    public int getItemViewType(int position) {
        return itemList.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_reward_info, parent, false);
            RewardInfoViewHolder viewHolder = new RewardInfoViewHolder(view, recyclerViewOnItemClickListener);
            return viewHolder;
        } else if (viewType == VIEW_TYPE_LOADING) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_progress_bar, parent, false);
            ProgressViewHolder progressViewHolder = new ProgressViewHolder(view);
            return progressViewHolder;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof RewardInfoViewHolder) {
            final RewardInfoViewHolder rewardInfoViewHolder
                    = (RewardInfoViewHolder) holder;
            final RewardInfoEntity rewardInfoEntity = itemList.get(position);
            if (null != rewardInfoEntity) {
                rewardInfoViewHolder.textViewRewardName.setText(rewardInfoEntity.getRewardName());
                //final DecimalFormat formatter = new DecimalFormat("###,###,###.##");
                rewardInfoViewHolder.textViewAmount.setText(String.format("%s điểm", rewardInfoEntity.getAmount()));
            }
        } else {

        }
    }

    @Override
    public int getItemCount() {
        return itemList == null ? 0 : itemList.size();
    }
}
