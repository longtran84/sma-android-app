package com.sma.mobile.cashout.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.RequestOptions;
import com.fintechviet.android.sdk.model.OrderLoyalty;
import com.fintechviet.android.sdk.model.PhoneCardResponse;
import com.fintechviet.android.sdk.model.VoucherResponse;
import com.sma.mobile.R;
import com.sma.mobile.cashout.models.CashOut;
import com.sma.mobile.favourite.RecyclerViewOnItemClickListener;
import com.sma.mobile.utils.viewholder.ProgressViewHolder;

import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * Created by longtran on 17/01/2017.
 */

public class OrderLoyaltyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<OrderLoyalty> itemList;
    private Context context;
    private static final int VIEW_TYPE_ITEM = 0;
    private static final int VIEW_TYPE_LOADING = 1;
    private RecyclerViewOnItemClickListener recyclerViewOnItemClickListener;

    /***
     * @param context
     * @param itemList
     */
    public OrderLoyaltyAdapter(Context context, List<OrderLoyalty> itemList,
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
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_order_loyalty, parent, false);
            OrderLoyaltyHolder viewHolder = new OrderLoyaltyHolder(view, recyclerViewOnItemClickListener);
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
        if (holder instanceof OrderLoyaltyHolder) {
            final OrderLoyaltyHolder conversationViewHolder
                    = (OrderLoyaltyHolder) holder;
            final OrderLoyalty orderLoyalty = itemList.get(position);
            if (null != orderLoyalty) {
                final DecimalFormat formatter = new DecimalFormat("###,###,###.##");
                conversationViewHolder.appCompat_text_view_left.setText(String.format("%d", orderLoyalty.getOrderId()));
                conversationViewHolder.appCompat_text_view_right.setText(
                        new SimpleDateFormat("dd/MM/yyyy").format(new Date(orderLoyalty.getCreatedDate())));
                conversationViewHolder.appCompat_text_view2.setText(String.format("%sđ", formatter.format(orderLoyalty.getTotal())));
                conversationViewHolder.appCompat_text_view4.setText(String.format("%sđ", formatter.format(orderLoyalty.getTotalPoint())));
                conversationViewHolder.appCompat_text_view5.setText(OrderStatus.valueOfOrderStatus(orderLoyalty.getStatus()));
                VoucherResponse voucherResponse = orderLoyalty.getVoucherResponse();
                PhoneCardResponse phoneCardResponse = orderLoyalty.getPhoneCardResponse();
                if (null != voucherResponse) {
                    if (!StringUtils.isBlank(voucherResponse.getPicture())) {
                        Glide.with(context)
                                .load(voucherResponse.getPicture())
//                            .apply(RequestOptions.bitmapTransform(new MultiTransformation<>(new CenterCrop(),
//                                    new RoundedCornersTransformation(1, 0, RoundedCornersTransformation.CornerType.ALL))))
                                .into(conversationViewHolder.imageViewAppIcon);

                    }
                } else if (null != phoneCardResponse) {
                    if (!StringUtils.isBlank(phoneCardResponse.getPicture())) {
                        Glide.with(context)
                                .load(phoneCardResponse.getPicture())
//                            .apply(RequestOptions.bitmapTransform(new MultiTransformation<>(new CenterCrop(),
//                                    new RoundedCornersTransformation(1, 0, RoundedCornersTransformation.CornerType.ALL))))
                                .into(conversationViewHolder.imageViewAppIcon);

                    }
                }
            }
        } else {

        }
    }

    @Override
    public int getItemCount() {
        return itemList == null ? 0 : itemList.size();
    }
}
