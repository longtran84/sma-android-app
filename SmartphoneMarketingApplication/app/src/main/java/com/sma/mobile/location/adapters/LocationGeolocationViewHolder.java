package com.sma.mobile.location.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.sma.mobile.R;
import com.sma.mobile.favourite.RecyclerViewOnItemClickListener;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by longtran on 17/01/2017.
 */

public class LocationGeolocationViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private RecyclerViewOnItemClickListener recyclerViewOnItemClickListener;

    private int mSelectedItem = -1;

    @BindView(R.id.radio_map_marker)
    public RadioButton radioButtonMapMarker;

    @BindView(R.id.text_view_title)
    public TextView textViewTitle;

    @BindView(R.id.text_view_snippet)
    public TextView textViewSnippet;

    @BindView(R.id.text_view_distance)
    public TextView textViewDistance;

    public LocationGeolocationViewHolder(View view,
                                         RecyclerViewOnItemClickListener recyclerViewOnItemClickListener) {
        super(view);
        ButterKnife.bind(this, view);
        this.recyclerViewOnItemClickListener = recyclerViewOnItemClickListener;
        view.setOnClickListener(this);
        radioButtonMapMarker.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        mSelectedItem = getAdapterPosition();
        recyclerViewOnItemClickListener.onClick(v, getAdapterPosition());
    }
}
