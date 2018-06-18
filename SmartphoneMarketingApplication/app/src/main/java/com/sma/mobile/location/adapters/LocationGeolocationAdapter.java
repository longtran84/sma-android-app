package com.sma.mobile.location.adapters;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sma.mobile.R;
import com.sma.mobile.favourite.RecyclerViewOnItemClickListener;
import com.sma.mobile.location.beans.LatLngBean;
import com.sma.mobile.utils.viewholder.ProgressViewHolder;

import java.util.List;

/**
 * Created by longtran on 17/01/2017.
 */

public class LocationGeolocationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<LatLngBean> itemList;
    private Context context;
    private static final int VIEW_TYPE_ITEM = 0;
    private static final int VIEW_TYPE_LOADING = 1;
    private int lastCheckedPosition = -1;
    private RecyclerViewOnItemClickListener recyclerViewOnItemClickListener;

    /***
     * @param context
     * @param itemList
     */
    public LocationGeolocationAdapter(Context context, List<LatLngBean> itemList,
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
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_location_geolocation, parent, false);
            LocationGeolocationViewHolder viewHolder = new LocationGeolocationViewHolder(view, new RecyclerViewOnItemClickListener() {
                @Override
                public void onClick(View v, int position) {
                    lastCheckedPosition = position;
                    notifyItemRangeChanged(0, itemList.size());
                    recyclerViewOnItemClickListener.onClick(v, position);
                }
            });
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
        if (holder instanceof LocationGeolocationViewHolder) {
            final LocationGeolocationViewHolder viewHolder
                    = (LocationGeolocationViewHolder) holder;
            final LatLngBean latLngBean = itemList.get(position);
            if (null != latLngBean) {
                viewHolder.textViewTitle.setText(latLngBean.getTitle());
                viewHolder.textViewSnippet.setText(latLngBean.getSnippet());
                viewHolder.textViewDistance.setText(latLngBean.getDistance());
                switch (latLngBean.getType()) {
                    case 1:
                        viewHolder.radioButtonMapMarker.setBackground(ResourcesCompat.getDrawable(context.getResources(), R.drawable.custom_radio_button_map_marker_bank, null));
                        break;
                    case 2:
                        viewHolder.radioButtonMapMarker.setBackground(ResourcesCompat.getDrawable(context.getResources(), R.drawable.custom_radio_button_map_marker_atm, null));
                        break;
                    case 3:
                        viewHolder.radioButtonMapMarker.setBackground(ResourcesCompat.getDrawable(context.getResources(), R.drawable.custom_radio_button_map_marker_gif, null));
                        break;
                    default:
                        viewHolder.radioButtonMapMarker.setBackground(ResourcesCompat.getDrawable(context.getResources(), R.drawable.custom_radio_button_map_marker, null));
                        break;
                }
//                if (!StringUtils.isBlank(latLngBean.getIcon())) {
//                    Glide.with(context)
//                            .load(latLngBean.getIcon())
//                            .apply(RequestOptions.bitmapTransform(new MultiTransformation<>(new CenterCrop(),
//                                    new RoundedCornersTransformation(1, 0, RoundedCornersTransformation.CornerType.ALL))))
//                            .into(conversationViewHolder.imageViewAppIcon);
//
//                }
                if (latLngBean.getPosition() == lastCheckedPosition) {
                    ((LocationGeolocationViewHolder) holder).radioButtonMapMarker.setChecked(true);
                } else {
                    ((LocationGeolocationViewHolder) holder).radioButtonMapMarker.setChecked(false);
                }
                ((LocationGeolocationViewHolder) holder).radioButtonMapMarker.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        lastCheckedPosition = latLngBean.getPosition();
                        notifyItemRangeChanged(0, itemList.size());
                        recyclerViewOnItemClickListener.onClick(v, position);
                    }
                });
            }
        } else {

        }
    }

    /**
     * @param position
     */
    public void setCheckedPosition(int position) {
        lastCheckedPosition = position;
        notifyItemRangeChanged(0, itemList.size());
    }

    @Override
    public int getItemCount() {
        return itemList == null ? 0 : itemList.size();
    }
}
