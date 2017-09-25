package com.sma.mobile.utils.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.sma.mobile.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by longtran on 17/01/2017.
 */

public class ProgressViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.progress_bar_load_more)
    public ProgressBar progressBarLoadMore;

    /***
     *
     * @param itemView
     */
    public ProgressViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
